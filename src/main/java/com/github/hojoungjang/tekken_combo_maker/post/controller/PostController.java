package com.github.hojoungjang.tekken_combo_maker.post.controller;

import com.github.hojoungjang.tekken_combo_maker.post.dto.PostCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @GetMapping("/{id}")
    public PostResponse getById(@PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @GetMapping
    public Page<PostResponse> getAll(Pageable pageable) {
        return postService.findAll(pageable);
    }

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequest request) {
        return postService.create(request);
    }
}
