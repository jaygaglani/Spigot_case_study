package com.Spigot.post.persistence;

import com.Spigot.post.dto.PostMetadataInput;
import com.Spigot.post.mapper.PostMetadataMapper;
import com.Spigot.post.model.PostMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class PostMetadataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //for debugging and printing statements
    private final static Logger logger = LoggerFactory.getLogger(PostDao.class);

    public List<PostMetadata> getPostMetadataById(final int id) {
        return jdbcTemplate.query(String.format("select * from %s where id = ?", PostMetadata.TABLE), new Object[]{id}, new PostMetadataMapper());
    }

    public PostMetadata getPostMetadataByIdPackageAndPos(final int id, final String packageName, final int position) {
        try {
            return jdbcTemplate.queryForObject(
                    String.format("select * from %s where %s = ? and %s = ? and %s = ?",
                            PostMetadata.TABLE, PostMetadata.ID, PostMetadata.PACKAGE_NAME, PostMetadata.POSITION),
                    new Object[]{id, packageName, position},
                    new PostMetadataMapper());
        } catch(EmptyResultDataAccessException ex) {
            logger.info("No entry found for id = {}, package = {}, position = {}",
                    id, packageName, position);
            return null;
        } catch(Exception ex) {
            logger.error("Failed to retrieve post record", ex);
            throw ex;
        }
    }

    public boolean addPostMetadata(final PostMetadata postMetadata)  {
        final SimpleJdbcInsert insertStmt = new SimpleJdbcInsert(jdbcTemplate).withTableName(PostMetadata.TABLE);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(PostMetadata.ID, postMetadata.getId());
        parameters.put(PostMetadata.PACKAGE_NAME, postMetadata.getPackageName());
        parameters.put(PostMetadata.ACTIVE, postMetadata.isActive());
        parameters.put(PostMetadata.POSITION, postMetadata.getPosition());
        parameters.put(PostMetadata.CREATED, new Date());
        parameters.put(PostMetadata.UPDATED, new Date());

        int key = 0;
        try {
            key = insertStmt.execute(parameters);
        }catch (DataIntegrityViolationException ex) {
            logger.error("Entry failed to meet SQL constraint in PostMetadata table for id = {}, package = {}, position = {}",
                    postMetadata.getId(), postMetadata.getPackageName(), postMetadata.getPosition());
            return false;
        }  catch (Exception ex) {
            logger.error("Exception occured while saving postMedata = {} to database", postMetadata, ex);
            throw ex;
        }
        return Objects.nonNull(key) && key > 0;
    }

    public boolean updatePostMetaData(final PostMetadataInput newVal) {
        final PostMetadata postMetadata = this.getPostMetadataByIdPackageAndPos(newVal.getCurrentId(),
                newVal.getCurrentPackageName(),
                newVal.getCurrentPosition());
        if (Objects.isNull(postMetadata)) {
            logger.info("Failed to update = {} record since value does not exist", newVal);
            return false;
        }
        if (!Objects.isNull(newVal.getNewId()) && postMetadata.getId() != newVal.getNewId()) {
            postMetadata.setId(newVal.getNewId());
        }
        if (!Objects.isNull(newVal.getNewPackageName())) {
            postMetadata.setPackageName(newVal.getNewPackageName());
        }
        if (!Objects.isNull(newVal.getNewPosition()) && postMetadata.getPosition()!= newVal.getNewPosition()) {
            postMetadata.setPosition(newVal.getNewPosition());
        }
        if (postMetadata.isActive() != newVal.isActive()) {
            postMetadata.setActive(newVal.isActive());
        }
        postMetadata.setUpdated(new Date());
        try {
            final int rows = jdbcTemplate.update(String.format("update %s set %s = ?, %s = ?, %s = ?," +
                                    "%s = ?, %s = ? where %s = ? and %s = ? and %s = ?",
                            PostMetadata.TABLE, PostMetadata.ID, PostMetadata.PACKAGE_NAME, PostMetadata.POSITION,
                            PostMetadata.ACTIVE, PostMetadata.UPDATED, PostMetadata.ID, PostMetadata.PACKAGE_NAME,
                            PostMetadata.POSITION
                    ),
                    new Object[]{postMetadata.getId(), postMetadata.getPackageName(), postMetadata.getPosition(),
                            postMetadata.isActive(), postMetadata.getUpdated(), newVal.getCurrentId(),
                            newVal.getCurrentPackageName(), newVal.getCurrentPosition()});
            return rows == 1;
        } catch(DataIntegrityViolationException ex) {
            logger.error("Update failed to meet data integrity {}", newVal, ex);
            return false;
        }
    }

    public boolean deletePostMetadata(final int id){
        final int rows = jdbcTemplate.update(String.format("delete from %s where id = ?",PostMetadata.TABLE), new Object[]{id});
        return rows>0;
    }

    public boolean deletePostMetadata(final int id, final String packageName){
        final int rows = jdbcTemplate.update(String.format("delete from %s where id = ? and packageName = ?",PostMetadata.TABLE), new Object[]{id,packageName});
        return rows>0;
    }

    public boolean deletePostMetadata(final int id, final String packageName, int position){
        final int rows = jdbcTemplate.update(String.format("delete from %s where id = ? and packageName = ? and position = ?",PostMetadata.TABLE), new Object[]{id,packageName,position});
        return rows>0;
    }

}

