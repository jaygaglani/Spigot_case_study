package com.Spigot.post.dto;
//for updating the postmetadata
import java.util.Date;

public class PostMetadataInput {
    private int currentId;
    private String currentPackageName;
    private int currentPosition;
    private Integer newId;
    private String newPackageName;
    private Integer newPosition;
    private boolean active;




    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public String getCurrentPackageName() {
        return currentPackageName;
    }

    public void setCurrentPackageName(String currentPackageName) {
        this.currentPackageName = currentPackageName;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Integer getNewId() {
        return newId;
    }

    public void setNewId(Integer newId) {
        this.newId = newId;
    }

    public String getNewPackageName() {
        return newPackageName;
    }

    public void setNewPackageName(String newPackageName) {
        this.newPackageName = newPackageName;
    }

    public Integer getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Integer newPosition) {
        this.newPosition = newPosition;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
