package com.Spigot.post.mapper;

import com.Spigot.post.model.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        final int id = resultSet.getInt(Post.ID);
        final String url = resultSet.getString(Post.URL);
        final String slug = resultSet.getString(Post.SLUG);
        final Blob image = resultSet.getBlob(Post.IMAGE);
        Post post = new Post(id, slug, url, image);
        return post;
    }
}
