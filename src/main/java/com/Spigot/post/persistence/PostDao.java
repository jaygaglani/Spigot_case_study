package com.Spigot.post.persistence;
//data access object, java uses these classes to access the database

import com.Spigot.post.dto.PostDetails;
import com.Spigot.post.dto.PostInput;
import com.Spigot.post.mapper.PostDetailsMapper;
import com.Spigot.post.mapper.PostMapper;
import com.Spigot.post.model.Post;
import com.Spigot.post.model.PostMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class PostDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static Logger logger = LoggerFactory.getLogger(PostDao.class);

    public List<Post> getAllPosts() {
        return jdbcTemplate.query(String.format("select * from %s where active = 1", Post.TABLE), new PostMapper());
    }

    public Post getPostById(final int id) {
        try {
            return jdbcTemplate.queryForObject(String.format("select * from %s where id = ?", Post.TABLE), new Object[]{id}, new PostMapper());
        } catch(EmptyResultDataAccessException ex) {
            logger.info("Post table is empty");
            return null;
        } catch(Exception ex) {
            logger.error("Failed to retrieve post record", ex);
            throw ex;
        }
    }

    public List<PostDetails> getPostByPackage(final String packageName) {//get posts sorted by position for a vertical
        final String sql = String.format("select p.*, pmd.packageName, pmd.position, pmd.updated from %s pmd " +
                "join %s p on pmd.id = p.id where pmd.packageName = ?\n" +
                " and pmd.active = 1 order by pmd.position, pmd.id;", PostMetadata.TABLE, Post.TABLE);
        return jdbcTemplate.query(sql, new Object[]{packageName}, new PostDetailsMapper());
    }

    public boolean addPost(final PostInput post)  {
        final SimpleJdbcInsert insertStmt = new SimpleJdbcInsert(jdbcTemplate).withTableName(Post.TABLE)
                .usingGeneratedKeyColumns(Post.ID);
        final Map<String, Object> parameters = new HashMap<>();
        Blob blob = null;
        try {
            byte[] bytes = post.getImage().getBytes();
            blob = new SerialBlob(bytes);
        } catch(Exception ex) {
            logger.error("Failed to create blob from multipart file", ex);
            throw new RuntimeException(ex);
        }
        parameters.put(Post.SLUG, post.getSlug());
        parameters.put(Post.URL, post.getUrl());
        parameters.put(Post.IMAGE, blob);
        Number primaryKey = null;
        try {
            primaryKey = insertStmt.executeAndReturnKey(parameters);
        } catch (Exception ex) {//for database failing to connect
            logger.error("Exception occurred while saving post = {} to database", post, ex);
            throw ex;
        }
        return Objects.nonNull(primaryKey) && primaryKey.intValue() > 0;//Pk should be + and non-null
    }

    public boolean updatePost(final PostInput postInput) {
        final Post post = this.getPostById(postInput.getId());
        if (Objects.isNull(post)) {
            logger.info("Failed to update. Post does not exist in DB = {} ", postInput);
            return false;
        }
        if (!Objects.isNull(postInput.getSlug())) {
            post.setSlug(postInput.getSlug());
        }
        if (!Objects.isNull(postInput.getUrl())) {
            post.setUrl(postInput.getUrl());
        }
        if (!Objects.isNull(postInput.getImage())) {
            Blob blob = null;
            try {
                byte[] bytes = postInput.getImage().getBytes();
                blob = new SerialBlob(bytes);
            } catch(Exception ex) {
                logger.error("Failed to create blob from multipart file", ex);
                throw new RuntimeException(ex);
            }
            post.setImage(blob);
        }
        final int rows = jdbcTemplate.update(String.format("update %s set %s = ?, %s = ?, %s = ? where %s = ?", Post.TABLE,
                        Post.SLUG, Post.URL, Post.IMAGE, Post.ID),
                        new Object[]{post.getSlug(), post.getUrl(), post.getImage(), post.getId()});
        return rows == 1;
    }

    public boolean deletePost(int id){
        final int rows = jdbcTemplate.update(String.format("delete from %s where id = ?", Post.TABLE), new Object[]{id});
        return rows == 1;
    }
}
