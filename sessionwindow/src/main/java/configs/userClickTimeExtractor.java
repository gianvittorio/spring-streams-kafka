package configs;

import lombok.extern.slf4j.Slf4j;
import models.UserClick;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class userClickTimeExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        UserClick click = (UserClick) consumerRecord.value();
        log.info("Click Time: {}", click.getCreatedTime());
        return ((click.getCreatedTime() > 0) ? click.getCreatedTime() : prevTime);
    }

    @Bean(name = "userClickTimeExtractor")
    public TimestampExtractor userClickTimeExtractor() {
        return new userClickTimeExtractor();
    }
}