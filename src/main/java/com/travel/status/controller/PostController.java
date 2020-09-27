package com.travel.status.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.travel.status.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.travel.status.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.travel.status.exception.RecordNotFoundException;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class PostController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	@Autowired
	PostService postService;

	@RequestMapping("/home")
	public String getAllPublicPost(Model model) {
		List<Post> posts = postService.getAllPublicPost();
		LOGGER.info("public status - {}", posts);
		model.addAttribute("posts", posts);
		return "home";
	}

	@RequestMapping("/posts")
	public String getAllPosts(Model model) {
		List<Post> posts = postService.getAllPost();
		model.addAttribute("posts", posts);
		return "logged-home";
	}

	@RequestMapping(path = {"/edit", "/edit/{postId}"})
	public String editPostById(Model model, @PathVariable("postId") Optional<Integer> postId)
            throws RecordNotFoundException {
		if (postId.isPresent()) {
			Post post = postService.getPostByPostId(postId.get());
			model.addAttribute("post", post);
		} else {
			model.addAttribute("post", new Post());
		}
		return "add-edit-post";
	}

    @RequestMapping(path = "/user")
    public String getUserPosts(Model model, HttpServletRequest request){
		Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        Set<Post> posts = postService.getAllPostsOfSpecificUser(email);
        model.addAttribute("posts", posts);
        return "user-posts";
    }

	@RequestMapping(path = "/createPost", method = RequestMethod.POST)
	public String createOrUpdatePost(Post post, HttpServletRequest req) {
		Principal principal = req.getUserPrincipal();
		String email = principal.getName();
		postService.addPost(post, email);
		return "redirect:/posts";
	}
}
