package com.nest.bluehydrogen;
import com.nest.bluehydrogen.kstream.KstreamToKstream;

import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;

////import org.apache.kafka.streams.kstream.KStream;
////import org.junit.Before;
//import org.mockito.Mockito;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ConfigurableApplicationContext;
////import org.junit.Test;
//import org.slf4j.Logger;
//import org.springframework.boot.SpringApplication;
import org.mockito.Mock;

//import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
//import org.junit.runner.RunWith;

import org.junit.jupiter.api.Test;


import org.mockito.junit.MockitoJUnitRunner;

//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.mockito.Mockito.*;
import java.util.Properties;


import org.mockito.InjectMocks;

//import static org.mockito.Mockito.*;
import org.apache.kafka.streams.StreamsBuilder;

import org.springframework.test.context.junit.jupiter.SpringExtension;

//import org.apache.kafka.streams.StreamsBuilder;


import org.apache.kafka.common.serialization.Serdes;

import org.apache.kafka.streams.StreamsConfig;

import org.springframework.context.ApplicationContext;



@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class KafkaToKafkaApplicationTests {

    @Autowired
    private KstreamToKstream kstreamToKstream;

    @Mock
    private KstreamToKstream kstream;

    @InjectMocks
    private KafkaToKafkaApplication kafkaToKafkaApplication;

    @Test
     void contextLoads() {
        kafkaToKafkaApplication.run();
        verify(kstream, times(1)).process();
    }

    @Test
    void testKstreamToKstream() {
        assertEquals("kafka-streams-demo", kstreamToKstream.getApplicationId());
        assertEquals("localhost:9092", kstreamToKstream.getBootstrapServers());
        assertEquals("org.apache.kafka.common.serialization.Serdes$StringSerde", kstreamToKstream.getKeySerde());
        assertEquals("org.apache.kafka.common.serialization.Serdes$StringSerde", kstreamToKstream.getValueSerde());
        assertEquals("input-topic", kstreamToKstream.getInputTopic());
        assertEquals("output-topic", kstreamToKstream.getOutputTopic());
    }


    @Test
     void testProcessing() {
        // Set up configuration properties
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-demo");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // Set up test input and output topics
        String inputTopic = "input-topic";
        String outputTopic = "output-topic";
        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(inputTopic).to(outputTopic);

        // Create a TopologyTestDriver instance and send input message to the test input topic
        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), config)) {
            TestInputTopic<String, String> input = testDriver.createInputTopic(inputTopic, new StringSerializer(), new StringSerializer());
            input.pipeInput("key", "value");

            // Read output message from the test output topic and verify result
            TestOutputTopic<String, String> output = testDriver.createOutputTopic(outputTopic, new StringDeserializer(), new StringDeserializer());
            KeyValue<String, String> outputRecord = output.readKeyValue();
            assertNotNull(outputRecord);
            assertEquals("key", outputRecord.key);
            assertEquals("value", outputRecord.value);
        }
    }




    @Test
     void testProcess() {



        String bootstrapServers = "localhost:9092";
        String inputTopic = "input-topic";
        String applicationId = "kafka-streams-demo";
        String outputTopic = "output-topic";
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        StreamsBuilder builder = new StreamsBuilder();
        KstreamToKstream kstreamToKstream = new KstreamToKstream();
        kstreamToKstream.setBootstrapServers("localhost:9092");
        kstreamToKstream.setKeySerde("org.apache.kafka.common.serialization.Serdes$StringSerde");
        kstreamToKstream.setValueSerde("org.apache.kafka.common.serialization.Serdes$StringSerde");
        kstreamToKstream.setApplicationId("kafka-streams-demo");
        kstreamToKstream.setInputTopic("input-topic");
        kstreamToKstream.setOutputTopic("output-topic");
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // when
        kstreamToKstream.process();

        assertEquals(applicationId, config.getProperty(StreamsConfig.APPLICATION_ID_CONFIG));
        assertEquals(bootstrapServers, config.getProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(Serdes.String().getClass().getName(), config.getProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG));
        assertEquals(Serdes.String().getClass().getName(), config.getProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG));
        assertNotNull(builder.stream(inputTopic));


    }
    @Test
    void testKafkaToKafkaApplication() {
        // Mock the ApplicationContext and KafkaToKafkaApplication classes
        ApplicationContext context = mock(ApplicationContext.class);
        KafkaToKafkaApplication kafkaToKafkaApplication = mock(KafkaToKafkaApplication.class);
        String[] args={"1"};
        kafkaToKafkaApplication.main(args);

//        // Call the run method on the mock KafkaToKafkaApplication instance
        kafkaToKafkaApplication.run();

//        // Verify that the run method was called once on the mock KafkaToKafkaApplication instance
        verify(kafkaToKafkaApplication, times(1)).run();

        KafkaToKafkaApplication kafkaToKafkaApplication1=new  KafkaToKafkaApplication();
        kafkaToKafkaApplication1.setInputTopic("input-topic");

        kafkaToKafkaApplication1.setOutputTopic("output-topic");
        kafkaToKafkaApplication1.setBootstrapServers("localhost:9092");

        assertEquals("input-topic",kafkaToKafkaApplication1.getInputTopic());
        assertEquals("output-topic",kafkaToKafkaApplication1.getOutputTopic());
        assertEquals("localhost:9092",kafkaToKafkaApplication1.getBootstrapServers());
    }

}

