package ru.larnerweb.wiki.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import ru.larnerweb.wiki.stream.model.WikiEvent;

import java.io.IOException;
import java.util.Properties;

public class StreamStarterApp {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "wiki-stream");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092,kafka02:9092,kafka03:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> events = builder.stream("wiki-src");
        KStream<String, String> eventsWithKeys = events.selectKey((k, v) -> extractKey(v));

        KStream<String, String>[] branches =
                eventsWithKeys.branch(
                    (k, v) -> v.contains("\"edit\""),
                    (k, v) -> v.contains("\"log\""),
                    (k, v) -> v.contains("\"categorize\""),
                    (k, v) -> v.contains("\"new\""),
                    (k, v) -> true);

        branches[0].to("wiki-edit");
        branches[1].to("wiki-log");
        branches[2].to("wiki-categorize");
        branches[3].to("wiki-new");
        branches[4].to("wiki-others");

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
        streams.localThreadsMetadata().forEach(System.out::println);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private static String extractKey(String value) {
        try {
            WikiEvent event = mapper.readValue(value, WikiEvent.class);
            return event.getMeta().getId();
        } catch (IOException e) {
            System.out.println(value);
            System.err.println(e.getLocalizedMessage());
        }
        return "unknown";
    }
}
