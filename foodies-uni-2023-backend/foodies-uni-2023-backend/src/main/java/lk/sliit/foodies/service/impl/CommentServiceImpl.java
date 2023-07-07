package lk.sliit.foodies.service.impl;

import lk.sliit.foodies.dto.CommentDTO;
import lk.sliit.foodies.dto.UserDTO;
import lk.sliit.foodies.entity.CommentEntity;
import lk.sliit.foodies.entity.PostEntity;
import lk.sliit.foodies.entity.UserEntity;
import lk.sliit.foodies.exception.PostException;
import lk.sliit.foodies.exception.UserException;
import lk.sliit.foodies.repository.CommentRepository;
import lk.sliit.foodies.repository.PostRepository;
import lk.sliit.foodies.repository.UserRepository;
import lk.sliit.foodies.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static lk.sliit.foodies.constant.MsgConstant.RESOURCE_NOT_FOUND;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public boolean addComment(long userId, long postId, String comment) {
        try {
            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if (!userEntity.isPresent()) throw new UserException(RESOURCE_NOT_FOUND, "User not found");

            Optional<PostEntity> postEntity = postRepository.findById(postId);
            if(!postEntity.isPresent()) throw new PostException(RESOURCE_NOT_FOUND, "Post not found");

            commentRepository.save(new CommentEntity(postEntity.get(), userEntity.get(), comment, new Date()));
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        try {

            Optional<PostEntity> postEntity = postRepository.findById(postId);
            if(!postEntity.isPresent()) throw new PostException(RESOURCE_NOT_FOUND, "Post not found");

            List<CommentEntity> commentEntities = commentRepository.getCommentByPostDateOrder(postEntity.get());

            return prepareCommentDTOs(commentEntities);

        } catch (Exception e) {
            throw e;
        }
    }

    private List<CommentDTO> prepareCommentDTOs(List<CommentEntity> commentEntities) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        try {
            for (CommentEntity commentEntity : commentEntities) {
                commentDTOS.add(new CommentDTO(
                        commentEntity.getId(),
                        new UserDTO(
                                commentEntity.getUserEntity().getId(),
                                commentEntity.getUserEntity().getEmail(),
                                commentEntity.getUserEntity().getFirstName(),
                                commentEntity.getUserEntity().getLastName(),
                                commentEntity.getUserEntity().getDate()
                        ),
                        commentEntity.getComment(),
                        commentEntity.getDate()
                ));
            }

            return commentDTOS;
        } catch (Exception e) {
            throw e;
        }
    }
}
