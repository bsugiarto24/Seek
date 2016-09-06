package com.aacfslo.tzli.seek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Bryan on 5/16/16.
 * This class represents a prayer from the database
 */
public class Post {
    public String Partner;
    public long date;

    public Post() {
    }

    public String getPartner() {
        return Partner;
    }

    @Override
    public String toString() {
        return "{" +
                "name:'" + Partner + '\'' +
                ", date:'" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post meetUp = (Post) o;

        if (Partner != null ? !Partner.equals(meetUp.Partner) : meetUp.Partner != null) return false;
        return true;
    }
}
