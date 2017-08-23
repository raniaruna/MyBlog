package com.hasura.rania.myblog;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rania on 8/22/2017.
 */

public class Article  implements Parcelable{
    @SerializedName("id")
    Integer id;
    @SerializedName("title")
    String title;
    @SerializedName("heading")
    String heading;
    @SerializedName("date")
    String date;
    @SerializedName("content")
    String content;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHeading() {
        return heading;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.id);
        dest.writeString(this.heading);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.content);

    }
    public Article(){}
    public Article(Parcel in) {
        this.id = (Integer)in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.heading = in.readString();
        this.date = in.readString();
        this.content = in.readString();
    }
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>(){

        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", heading='" + heading + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
