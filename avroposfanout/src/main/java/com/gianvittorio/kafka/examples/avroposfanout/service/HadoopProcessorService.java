package com.gianvittorio.kafka.examples.avroposfanout.service;

import com.gianvittorio.kafka.examples.avroposfanout.binding.PosListenerBinding;
import com.gianvittorio.kafka.examples.avroposfanout.model.HadoopRecord;
import com.gianvittorio.kafka.examples.avroposgen.model.PosInvoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableBinding(PosListenerBinding.class)
@RequiredArgsConstructor
public class HadoopProcessorService {

    private final RecordBuilder recordBuilder;

    @StreamListener("hadoop-input-channel")
    @SendTo("hadoop-output-channel")
    public KStream<String, HadoopRecord> process(KStream<String, PosInvoice> input) {

        KStream<String, HadoopRecord> hadoopRecordKStream = input.mapValues(recordBuilder::getMaskedInvoice)
                .flatMapValues(recordBuilder::getHadoopRecords);

        hadoopRecordKStream.foreach(
                (key, value) -> log.info("Hadoop Record: -Key: {}, Value: {}", key, value)
        );

        return hadoopRecordKStream;
    }

}
