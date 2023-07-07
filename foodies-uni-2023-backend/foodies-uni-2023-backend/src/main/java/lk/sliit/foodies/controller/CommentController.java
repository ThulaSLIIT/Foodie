package lk.sliit.foodies.controller;

import lk.sliit.foodies.dto.CommentDTO;
import lk.sliit.foodies.dto.CommentRequestDTO;
import lk.sliit.foodies.dto.CommonResponseDTO;
import lk.sliit.foodies.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity addComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        boolean result = commentService.addComment(commentRequestDTO.getUserId(), commentRequestDTO.getPostId(), commentRequestDTO.getComment());
        return new ResponseEntity(new CommonResponseDTO(true, "Comment added successfully", result), HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}")
    public ResponseEntity getCommentsByPostId(@PathVariable long postId) {
        List<CommentDTO> result = commentService.getCommentsByPostId(postId);
        return new ResponseEntity(new CommonResponseDTO(true, "Comments found successfully", result), HttpStatus.OK);
    }

}
