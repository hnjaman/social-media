package com.travel.status.service.impl;

import com.travel.status.model.Post;
import com.travel.status.repository.PostRepository;
import com.travel.status.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post addPost(Post post, String email) {
        if(post.getPostId() == null) {
            post.setUserId(email);
            post.setPostedAt(new Date());
            post = postRepository.save(post);
            LOGGER.info("New status - {}", post.getUserId(), post.getStatus());
            return post;
        } else {
            Optional<Post> postOptional = postRepository.findById(post.getPostId());
            if(postOptional.isPresent()) {
                Post presentPost = postOptional.get();
                presentPost.setStatus(post.getStatus());
                presentPost.setPrivacy(post.isPrivacy());
                presentPost.setCheckIn(post.getCheckIn());
                presentPost.setPinStatus(post.isPinStatus());

                presentPost = postRepository.save(presentPost);

                LOGGER.info("edit status - {}", presentPost.getUserId(), presentPost.getStatus());

                return presentPost;
            } else {
                post.setUserId(email);
                post.setPostedAt(new Date());
                post = postRepository.save(post);
                return post;
            }
        }
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllPublicPost() {
        List<Post> postList = postRepository.findAllByPrivacyFalse();
        return postList;
    }

    @Override
    public Set<Post> getAllPostsOfSpecificUser(String userId) {
        LinkedHashSet<Post> linkedSet = new LinkedHashSet<>();
        List<Post> posts = postRepository.findAllByUserId(userId);
        for(Post post: posts){
            if(post.isPinStatus()){
                linkedSet.add(post);
            }
        }
        linkedSet.addAll(posts);

        return linkedSet;
    }

    @Override
    public Post getPostByPostId(Integer postId) {
        return postRepository.findById(postId).get();
    }
}
