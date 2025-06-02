package ru.skypro.homework.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentsDto getCommentsByAdId(Long adId) {
        List<Comment> comments = commentRepository.findByAdPk(adId);
        return new CommentsDto(comments.size(), commentMapper.toDtoList(comments));
    }

    @Override
    public CommentDto createComment(Long adId, CreateOrUpdateCommentDto commentDto, String userEmail) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Comment comment = commentMapper.createOrUpdateCommentDtoToEntity(commentDto);
        comment.setAuthor(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(Math.toIntExact(commentId))
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(Long commentId, CreateOrUpdateCommentDto commentDto) {
        Comment comment = commentRepository.findById(Math.toIntExact(commentId))
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentMapper.updateCommentFromDto(commentDto, comment);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
}