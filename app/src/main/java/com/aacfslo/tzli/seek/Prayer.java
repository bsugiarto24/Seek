package com.aacfslo.tzli.seek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Bryan on 5/16/16.
 * This class represents a prayer from the database
 */
public class Prayer {
    public String author;
    public long date;
    public String prayer;

    public Prayer() {
    }



    @Override
    public String toString() {
        return "{" +
                "name:'" + author + '\'' +
                ", date:'" + date + '\'' +
                ", uniqueId:'" + prayer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prayer meetUp = (Prayer) o;

        if (author != null ? !author.equals(meetUp.author) : meetUp.author != null) return false;
        if (prayer != null ? !author.equals(meetUp.prayer) : meetUp.prayer != null) return false;
        return (date != 0 ? date !=(meetUp.date) : meetUp.date != 0);
    }
}
