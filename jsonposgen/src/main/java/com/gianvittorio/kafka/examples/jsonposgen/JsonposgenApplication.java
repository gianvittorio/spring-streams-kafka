package com.gianvittorio.kafka.examples.jsonposgen;

import com.gianvittorio.kafka.examples.jsonposgen.service.KafkaProducerService;
import com.gianvittorio.kafka.examples.jsonposgen.service.datagenerator.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootApplication
public class JsonposgenApplication implements ApplicationRunner {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private InvoiceGenerator invoiceGenerator;

    @Value("${application.configs.invoice.count}")
    private int INVOICE_COUNT;


    public static void main(String[] args) {
        SpringApplication.run(JsonposgenApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IntStream.range(0, INVOICE_COUNT)
                .forEach(i -> {
                    try {
                        kafkaProducerService.sendMessage(invoiceGenerator.getNextInvoice());

                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                });
    }
}
