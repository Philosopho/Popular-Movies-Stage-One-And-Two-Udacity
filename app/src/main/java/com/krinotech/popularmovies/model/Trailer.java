package com.krinotech.popularmovies.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {
    private Uri link;
    private String name;

    public Trailer(Uri link, String name) {
        this.link = link;
        this.name = name;
    }

    protected Trailer(Parcel in) {
        link = in.readParcelable(Uri.class.getClassLoader());
        name = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public Uri getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(link, flags);
        dest.writeString(name);
    }
}
