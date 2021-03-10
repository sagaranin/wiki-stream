package ru.larnerweb.wiki.stream.model;

import lombok.Data;

import java.util.Date;

@Data
public class Meta {
    String uri;
    String request_id;
    String id;
    Date dt;
    String domain;
    String stream;
    String topic;
    Short partition;
    Long offset;
}
