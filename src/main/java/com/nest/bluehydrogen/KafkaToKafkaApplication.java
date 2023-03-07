/*
 * Copyright (c) NeST Digital Pvt Ltd, 2023.
 * All Rights Reserved. Confidential.
 */
package com.nest.bluehydrogen;

import com.nest.bluehydrogen.kstream.KstreamToKstream;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;




/**

 * File: KafkaToKafkaApplication.java

 * This is the main class of the Kafka To Kafka application.
 * It is responsible for initializing the Spring Boot application.
 * and starting the kstream processing.

 * @author Abhinand Manohar OP
 * @date January 25, 2023

 */


@Getter
@Setter
@SpringBootApplication
public class KafkaToKafkaApplication {

	@Autowired
	private KstreamToKstream kstream;

	@Value("${kafka.streams.bootstrap.servers}")
	private String bootstrapServers;

	@Value("${kafka.streams.input.topic}")
	private String inputTopic;

	@Value("${kafka.streams.output.topic}")
	private String outputTopic;

	private static final Logger logger = LoggerFactory.getLogger(KafkaToKafkaApplication .class);




	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(KafkaToKafkaApplication.class, args);
		KafkaToKafkaApplication kafkaToKafkaApplication = context.getBean(KafkaToKafkaApplication.class);
		kafkaToKafkaApplication.run();
	}

	public void run() {
		kstream.process();
	}
}


