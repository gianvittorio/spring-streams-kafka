package com.gianvittorio.kafka.examples.exactlyoncefanout.service;

import com.gianvittorio.kafka.examples.avroposgen.model.HadoopRecord;
import com.gianvittorio.kafka.examples.avroposgen.model.Notification;
import com.gianvittorio.kafka.examples.avroposgen.model.PosInvoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableBinding
public class PosListenerService {

    private final RecordBuilder recordBuilder;

    @StreamListener("pos-input-channel")
    public void process(KStream<String, PosInvoice> input) {


        KStream<String, HadoopRecord> hadoopRecordKStream = input.mapValues(recordBuilder::getMaskedInvoice)
                .flatMapValues(recordBuilder::getHadoopRecords);

        KStream<String, Notification> notificationKStream = input.filter((key, val) -> val.getCustomerType().equalsIgnoreCase("PRIME"))
                .mapValues(recordBuilder::getNotification);

        hadoopRecordKStream.foreach((key, val) -> log.info("Hadoop Record:-Key {}, Value: {}", key, val));

        notificationKStream.foreach((key, val) -> log.info("Notification Record:-Key {}, Value: {}", key, val));

        hadoopRecordKStream.to("hadoop-sink-topic");
        notificationKStream.to("loyalty-topic");
    }
}
