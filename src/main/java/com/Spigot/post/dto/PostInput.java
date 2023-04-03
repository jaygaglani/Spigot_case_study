package com.Spigot.post.dto;
//for updating the posts

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class PostInput implements Serializable {//serializable is for blob to byte array

    private int id;
    private String slug;
    private String url;
    private MultipartFile image;//multipartFile is for receiving image

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public String getUrl() {
        return url;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setSlug(String newSlug){
        this.slug = newSlug;
    }

    public void setUrl(String newURL){
        this.url = newURL;
    }

    public void setImage(MultipartFile newImage){
        this.image = newImage;
    }

}

