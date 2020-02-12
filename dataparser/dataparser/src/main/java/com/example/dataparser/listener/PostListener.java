package com.example.dataparser.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import twitter4j.*;

@Component
public class PostListener {
    @Autowired
    TwitterStreamFactory twitterStreamFactory;
    @Autowired
    KafkaTemplate kafkaTemplate;
    @Value("${kafka.topic.name}")
    private String topic;

    public void listen() {
        TwitterStream twitterStream = twitterStreamFactory.getInstance();
        twitterStream.addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                System.out.println(status.getText());
                kafkaTemplate.send(topic, status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {

            }
        });

        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.locations(new double[][]{new double[]{34.714737, 48.421910},
                new double[]{35.277786,48.572973
                }});
        twitterStream.filter(tweetFilterQuery);

    }
}
