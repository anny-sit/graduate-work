package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentsController {

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getComments(@RequestParam Long id) {

        return ResponseEntity.ok(new CommentsDto());

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();*/
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@RequestParam Long id) {

        return ResponseEntity.ok(new CommentDto());

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();*/
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestParam Long adId, @RequestParam Long commentId) {

        return ResponseEntity.status(HttpStatus.OK).build();

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();*/
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@RequestParam Long adId,
                                           @RequestParam Long commentId,
                                           @RequestBody CreateOrUpdateCommentDto createOrUpdateComment) {

        return ResponseEntity.ok(new CommentDto());

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();*/
    }
}
