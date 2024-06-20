package com.cos.photogram.service;

import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.comment.CommentRepository;
import com.cos.photogram.domain.handler.ex.CustomApiException;
import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.web.dto.comment.CommentSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment saveComment(CommentSaveDto commentSaveDto, int loginUserId) {

        Comment comment = new Comment();

        comment.setContent(commentSaveDto.getContent());

        User userEntity = userRepository.findById(loginUserId).orElseThrow(() -> {
            throw new CustomApiException("존재하지 않는 유저입니다.");
        });
        comment.setUser(userEntity);

        Image image = new Image();
        image.setId(commentSaveDto.getImageId());
        comment.setImage(image);


        return commentRepository.save(comment);

    }

    public void deleteComment() {

    }
}
