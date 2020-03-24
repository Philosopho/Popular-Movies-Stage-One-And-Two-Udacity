package com.krinotech.popularmovies.model;

import android.net.Uri;

public class Trailer {
    private Uri link;
    private String name;

    public Trailer(Uri link, String name) {
        this.link = link;
        this.name = name;
    }

    public Uri getLink() {
        return link;
    }

    public void setLink(Uri link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
