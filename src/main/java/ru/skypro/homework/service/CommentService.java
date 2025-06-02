package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto getCommentsByAdId(Long adId);
    CommentDto createComment(Long adId, CreateOrUpdateCommentDto comment, String userEmail);
    void deleteComment(Long commentId);
    CommentDto updateComment(Long commentId, CreateOrUpdateCommentDto comment);
}