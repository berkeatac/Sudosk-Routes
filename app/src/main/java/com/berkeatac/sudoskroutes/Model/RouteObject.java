package com.berkeatac.sudoskroutes.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class RouteObject implements Parcelable {
    private String id;
    private String name;
    private String creator;
    private String date;
    private Integer grade;
    private String description;
    private String imageUrl;

    public RouteObject(String id, String name, String creator, String date, Integer grade, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.date = date;
        this.grade = grade;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    protected RouteObject(Parcel in) {
        id = in.readString();
        name = in.readString();
        creator = in.readString();
        date = in.readString();
        grade = in.readInt();
        description = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<RouteObject> CREATOR = new Creator<RouteObject>() {
        @Override
        public RouteObject createFromParcel(Parcel in) {
            return new RouteObject(in);
        }

        @Override
        public RouteObject[] newArray(int size) {
            return new RouteObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(creator);
        out.writeString(date);
        if(grade != null) {
            out.writeInt(grade);
        }
        out.writeString(description);
        out.writeString(imageUrl);
    }
}
