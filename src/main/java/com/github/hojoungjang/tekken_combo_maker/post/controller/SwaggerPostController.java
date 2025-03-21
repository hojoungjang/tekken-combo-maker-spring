package com.github.hojoungjang.tekken_combo_maker.post.controller;

import com.github.hojoungjang.tekken_combo_maker.common.dto.BaseErrorResponse;
import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.CommentResponse;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.post.dto.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Post", description = "게시물 API")
public interface SwaggerPostController {

    @Operation(summary = "ID 로 게시물 조회", description = "ID 를 이용해 게시물을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ID 로 게시물을 성공적으로 조회"),
            @ApiResponse(
                    responseCode = "404",
                    description = "ID 에 대한 게시물이 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = BaseErrorResponse.class))
            )
    })
    PostResponse getById(@Parameter(description = "게시물 ID") Long id);

    @Operation(summary = "게시물 목록 조회", description = "전체 게시물을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물을 성공적으로 조회")
    })
    Page<PostResponse> getAll(@ParameterObject Pageable pageable);

    @Operation(summary = "게시물 생성", description = "게시물을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물을 성공적으로 생성"),
            @ApiResponse(
                    responseCode = "404",
                    description = "ID 에 대한 사용자가 (멤버) 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = BaseErrorResponse.class))
            )
    })
    Long createPost(@RequestBody PostCreateRequest request);

    @Operation(summary = "게시물 댓글 목록 조회", description = "게시물의 전체 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물을 댓글을 성공적으로 조회")
    })
    Page<CommentResponse> getAllCommentsByPost(
            @Parameter(description = "게시물 ID") Long id,
            @ParameterObject Pageable pageable
    );

    @Operation(summary = "게시물 댓글 생성", description = "게시물에 댓글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 댓글을 성공적으로 생성"),
            @ApiResponse(
                    responseCode = "404",
                    description = "ID 에 대한 사용자 또는 게시물이 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = BaseErrorResponse.class))
            )
    })
    Long createComment(
            @Parameter(description = "게시물 ID") Long id,
            @RequestBody CommentCreateRequest request
    );
}
