package ru.larnerweb.wiki.stream.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Revision {
    @JsonProperty("old")
    public int old_revision;
    @JsonProperty("new")
    public int new_revision;
}
