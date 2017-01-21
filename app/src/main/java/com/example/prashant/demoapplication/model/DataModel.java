package com.example.prashant.demoapplication.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by prashant on 21/1/17.
 */
public class DataModel {

    @JsonProperty("id")
    public  int id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("body")
    public String body;

    public DataModel(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
