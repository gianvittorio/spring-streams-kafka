package com.gianvittorio.kafka.examples.streamingaggregates.service;

import com.gianvittorio.kafka.examples.streamingaggregates.binding.WordListenerBinding;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@EnableBinding(WordListenerBinding.class)
public class WordListenerService {

    @StreamListener("words-input-channel")
    public void process(KStream<String, String> input) {

        KStream<String, String> wordStream = input.flatMapValues(value -> List.of(value.toLowerCase().split(" ")));

        wordStream.groupBy((key, value) -> value)
                .count()
                .toStream()
                .peek((key, value) -> log.info("Word: {}; Count: {}", key, value));
    }
}
