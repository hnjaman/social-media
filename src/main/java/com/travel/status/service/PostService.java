package com.travel.status.service;


import com.travel.status.model.Post;

import java.util.List;
import java.util.Set;

public interface PostService {
    Post addPost(Post post, String email);
    List<Post> getAllPost();
    List<Post> getAllPublicPost();
    Set<Post> getAllPostsOfSpecificUser(String userId);
    Post getPostByPostId(Integer postId);
}
