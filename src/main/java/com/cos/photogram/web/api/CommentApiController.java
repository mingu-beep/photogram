package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.service.CommentService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.comment.CommentSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> saveCommentApi(@Valid @RequestBody CommentSaveDto commentSaveDto,
                                            BindingResult bindingResult,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Comment comment = commentService.saveComment(commentSaveDto, principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 저장 성공", comment), HttpStatus.CREATED);

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> removeCommentApi(@PathVariable Integer commentId) {

        log.info(" @@@@@ 삭제 API 동작 {}", commentId);

        commentService.deleteComment(commentId);

        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 성공", null), HttpStatus.ACCEPTED);
    }
}
