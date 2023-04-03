package com.Spigot.post.mapper;

import com.Spigot.post.model.Post;
import com.Spigot.post.model.PostMetadata;
import com.Spigot.post.dto.PostDetails;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
//mapper class is to convert database object into java object
public class PostDetailsMapper implements RowMapper<PostDetails> {
    @Override
    public PostDetails mapRow(ResultSet resultSet, int i) throws SQLException {
        final int id = resultSet.getInt(PostMetadata.ID);
        final String packageName = resultSet.getString(PostMetadata.PACKAGE_NAME);
        final Date updated = resultSet.getDate(PostMetadata.UPDATED);
        final String url = resultSet.getString(Post.URL);
        final String slug = resultSet.getString(Post.SLUG);
        final Blob image = resultSet.getBlob(Post.IMAGE);
        final PostDetails postDetails = new PostDetails();
        final int position = resultSet.getInt(PostMetadata.POSITION);
        postDetails.setId(id);
        postDetails.setSlug(slug);
        postDetails.setUrl(url);
        postDetails.setUpdated(updated);
        postDetails.setPackageName(packageName);
        postDetails.setPosition(position);
        try {
            postDetails.setImage(Base64.encodeBase64(image.getBinaryStream().readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };
        return postDetails;
    }
}
