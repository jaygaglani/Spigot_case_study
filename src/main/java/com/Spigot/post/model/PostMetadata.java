package com.Spigot.post.model;

import java.util.*;

public class PostMetadata {

    private int id;
    private String packageName;
    private int position;
    private boolean active;
    private Date created;
    private Date updated;

    //Table name
    public final static String TABLE = "postmetadata";

    //Column names
    public final static String ID = "id";
    public final static String PACKAGE_NAME = "packageName";
    public final static String POSITION = "position";
    public final static String ACTIVE = "active";
    public final static String CREATED = "created";
    public final static String UPDATED = "updated";


    public PostMetadata(int id, String packageName, int position, boolean active, Date created, Date updated) {
        this.id = id;
        this.packageName = packageName;
        this.position = position;
        this.active = active;
        this.created = created;
        this.updated = updated;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
