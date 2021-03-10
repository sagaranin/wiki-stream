package ru.larnerweb.wiki.stream.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiEvent {
    @JsonProperty("$schema")
    public String schema;
    public Meta meta;
    public int id;
    public String type;
    public int namespace;
    public String title;
    public String comment;
    public int timestamp;
    public String user;
    public boolean bot;
    public int log_id;
    public String log_type;
    public String log_action;
    public String log_action_comment;
    public boolean minor;
    public boolean patrolled;
    public Length length;
    public Revision revision;
    public String server_url;
    public String server_name;
    public String server_script_path;
    public String wiki;
    public String parsedcomment;
}
