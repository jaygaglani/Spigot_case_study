package com.Spigot.post.mapper;

import com.Spigot.post.model.PostMetadata;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMetadataMapper implements RowMapper<PostMetadata> {
    @Override

    public PostMetadata mapRow(ResultSet resultSet, int i) throws SQLException {
        final int id = resultSet.getInt(PostMetadata.ID);
        final String packageName = resultSet.getString(PostMetadata.PACKAGE_NAME);
        final int position = resultSet.getInt(PostMetadata.POSITION);
        final boolean active = resultSet.getBoolean(PostMetadata.ACTIVE);
        final Date created = resultSet.getDate(PostMetadata.CREATED);
        final Date updated = resultSet.getDate(PostMetadata.UPDATED);
        PostMetadata postMetadata = new PostMetadata(id, packageName, position, active,created,updated);
        return postMetadata;
    }
}