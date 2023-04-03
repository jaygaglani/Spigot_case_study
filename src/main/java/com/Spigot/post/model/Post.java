package com.Spigot.post.model;


import com.Spigot.post.dto.PostOutput;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

public class Post implements Serializable {

    //Table name
    public final static String TABLE = "posts";

    //Column names for the database
    public final static String ID = "id";
    public final static String SLUG = "SLUG";
    public final static String URL = "URL";
    public final static String IMAGE = "Image";

    private int id;
    private String slug;
    private String url;
    private Blob image;

    public Post(int id, String slug, String URL, Blob image) {
        this.id = id;
        this.slug = slug;
        this.url = URL;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getUrl() {
        return url;
    }

    public Blob getImage() {
        return image;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void setSlug(String newSlug){
        this.slug = newSlug;
    }

    public void setUrl(String newURL){
        this.url = newURL;
    }

    public void setImage(Blob newImage){
        this.image = newImage;
    }

    public PostOutput toPostOutput() {
        final PostOutput postOutput = new PostOutput();
        postOutput.setSlug(this.slug);
        postOutput.setUrl(this.url);
        try {
            postOutput.setImage(Base64.encodeBase64(image.getBinaryStream().readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };
        return postOutput;
    }
}
