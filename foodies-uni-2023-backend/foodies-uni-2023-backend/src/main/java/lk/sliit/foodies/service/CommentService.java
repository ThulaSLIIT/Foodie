package lk.sliit.foodies.service;

import lk.sliit.foodies.dto.CommentDTO;

import java.util.List;

/**
 * @author hp
 */
public interface CommentService {
    boolean addComment(long userId, long postId, String comment);
    List<CommentDTO> getCommentsByPostId(long postId);
}
