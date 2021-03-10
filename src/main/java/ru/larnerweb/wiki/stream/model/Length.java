package ru.larnerweb.wiki.stream.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Length {
    @JsonProperty("old")
    public int old_length;
    @JsonProperty("new")
    public int new_length;
}
