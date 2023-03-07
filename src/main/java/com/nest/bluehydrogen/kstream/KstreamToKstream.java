/*
 * Copyright (c) NeST Digital Pvt Ltd, 2023.
 * All Rights Reserved. Confidential.
 */
package com.nest.bluehydrogen.kstream;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





/**

 * File: KstreamToKstream.java

 * This class is responsible for processing data streams using Kafka Streams API. It reads data from an input topic and
 * writes to an output topic. It uses Spring's @Value annotation to inject properties from application.properties file.

 @author Abhinand Manohar OP
 @date January 25, 2023

 */

@Component
@Getter
@Setter
public class KstreamToKstream {

    @Value("${kafka.streams.application.id}")
    private String applicationId;

    @Value("${kafka.streams.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${kafka.streams.default.key.serde}")
    private String keySerde;

    @Value("${kafka.streams.default.value.serde}")
    private String valueSerde;

    @Value("${kafka.streams.input.topic}")
    private String inputTopic;

    @Value("${kafka.streams.output.topic}")
    private String outputTopic;

    private static final Logger logger = LoggerFactory.getLogger(KstreamToKstream .class);



    /**
     * Function: processing()
     * <p>
     * This method is responsible for configuring and starting the Kafka streams processing.
     * It sets up the properties for the streams, including the application ID, bootstrap servers, and default key and value serdes.
     * It then creates a StreamsBuilder and sets up the stream to read from the input topic and write to the output topic.
     * Finally, it creates a KafkaStreams object and starts the processing.
     */


    public void process() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(inputTopic).to(outputTopic);


        boolean keepRunning = true;
        while (keepRunning) {
            try (KafkaStreams streams = new KafkaStreams(builder.build(), config)) {
                streams.start();
                Thread.sleep(10000); // Wait for 1 minute before starting the next iteration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Re-interrupt the thread
            } catch (Exception e) {
                logger.error("An error occurred", e);
            }


            keepRunning = false;

        }



    }
}




