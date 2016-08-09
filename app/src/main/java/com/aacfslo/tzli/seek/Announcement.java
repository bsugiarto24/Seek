package com.aacfslo.tzli.seek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Bryan on 5/16/16.
 * This class represents a prayer from the database
 */
public class Announcement {
    public String author;
    public String date;
    public String announcement;

    public Announcement() {
    }



    @Override
    public String toString() {
        return "{" +
                "name:'" + author + '\'' +
                ", date:'" + date + '\'' +
                ", announcement:'" + announcement + '\'' +
                '}';
    }

}
