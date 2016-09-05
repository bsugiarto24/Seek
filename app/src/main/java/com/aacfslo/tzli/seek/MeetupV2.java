package com.aacfslo.tzli.seek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by terrence on 5/16/16.
 */
public class MeetupV2 {
    private long date;
    public String Partner;

    public MeetupV2() {
    }




    public long getDate() {
        return date;
    }


    public void setDate(long date) {
        this.date = date;
    }


    @JsonIgnoreProperties({
            "picture"
    })

    @Override
    public String toString() {
        return "{" +
                "date:'" + date + '\'' +
                ", partner:'" + Partner + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetupV2 meetUp = (MeetupV2) o;

        if (Partner != null ? !Partner.equals(meetUp.Partner) : meetUp.Partner != null) return false;
        if (date != date) return false;
            return true;

    }
}
