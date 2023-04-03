package com.Spigot.post.controller;

import com.Spigot.post.dto.PostMetadataInput;
import com.Spigot.post.model.PostMetadata;
import com.Spigot.post.persistence.PostMetadataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class PostMetaDataController {

    @Autowired
    private PostMetadataDao postMetadataDao;

    @GetMapping("/getPostMetadata")
    public List<PostMetadata> getPostMetaData(int id) {
        return postMetadataDao.getPostMetadataById(id);
    }

    @PutMapping("/addPostMetadata")
    public boolean addPostMetadata(@RequestBody PostMetadata postMetadata) {
        return postMetadataDao.addPostMetadata(postMetadata);
    }

    @PatchMapping("/updatePostMetadata")
    public boolean updatePostMetadata(@RequestBody PostMetadataInput postMetadataInput) {
        return postMetadataDao.updatePostMetaData(postMetadataInput);
    }

    @DeleteMapping("/deletePostMetadata")
    public boolean deletePostMetadataById(@RequestParam int id,
                                          @RequestParam(required = false) String packageName,
                                          @RequestParam(required = false) String position){
        if(Objects.isNull(packageName) && Objects.isNull(position)) return postMetadataDao.deletePostMetadata(id);
        else if (Objects.isNull(position)) return postMetadataDao.deletePostMetadata(id, packageName);
        else return postMetadataDao.deletePostMetadata(id,packageName,Integer.parseInt(position));
    }
}
