package com.gianvittorio.kafka.examples.xmlbranching.service;

import com.gianvittorio.kafka.examples.model.Order;
import com.gianvittorio.kafka.examples.xmlbranching.binding.OrderListenerBinding;
import com.gianvittorio.kafka.examples.xmlbranching.config.AppConstants;
import com.gianvittorio.kafka.examples.xmlbranching.config.AppSerdes;
import com.gianvittorio.kafka.examples.xmlbranching.model.OrderEnvelop;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Service
@Slf4j
@EnableBinding(OrderListenerBinding.class)
public class OrderListenerService {

    @Value("${application.configs.error.topic.name}")
    private String ERROR_TOPIC;

    @StreamListener("xml-input-channel")
    @SendTo({"india-orders-channel", "abroad-orders-channel"})
    public KStream<String, Order>[] process(KStream<String, String> input) {

        input.foreach((key, value) -> log.info("Received XML Order Key: {}, Value: {}", key, value));

        KStream<String, OrderEnvelop> orderEnvelopKStream = input.map((key, value) -> {

            OrderEnvelop orderEnvelop = OrderEnvelop.builder()
                    .xmlOrderKey(key)
                    .xmlOrderValue(value)
                    .build();

            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                orderEnvelop.setValidOrder((Order) jaxbUnmarshaller.unmarshal(new StringReader(value)));
                orderEnvelop.setOrderTag(AppConstants.VALID_ORDER);

                if (orderEnvelop.getValidOrder().getShipTo().getCity().isEmpty()) {
                    log.error("Missing destination city");
                    orderEnvelop.setOrderTag(AppConstants.ADDRESS_ERROR);
                }

            } catch (JAXBException e) {
                log.error("Failed to unmarshal the incoming XML");
                orderEnvelop.setOrderTag(AppConstants.PARSE_ERROR);
            }

            return KeyValue.pair(orderEnvelop.getOrderTag(), orderEnvelop);
        });

        orderEnvelopKStream.filter((key, value) -> !key.equalsIgnoreCase(AppConstants.VALID_ORDER))
                .to(ERROR_TOPIC, Produced.with(AppSerdes.String(), AppSerdes.OrderEnvelop()));

        KStream<String, Order> validOrders = orderEnvelopKStream
                .filter((key, value) -> key.equalsIgnoreCase(AppConstants.VALID_ORDER))
                .map((key, value) -> KeyValue.pair(value.getValidOrder().getOrderId(), value.getValidOrder()));

        validOrders.foreach((key, value) -> log.info("Valid Order with ID: {}", value.getOrderId()));

        Predicate<String, ? super Order> isIndiaOrder = (key, value) -> MatchersUtils.isDestinedTo(value, "India");
        Predicate<String, ? super Order> isAbroadOrder = (key, value) -> !isIndiaOrder.test(key, value);

        return validOrders.branch(isIndiaOrder, isAbroadOrder);
    }

    public static interface MatchersUtils {

        public static boolean isDestinedTo(final Order order, final String country) {
            return order.getShipTo().getCountry().equalsIgnoreCase(country);
        }
    }
}
