package lk.sliit.foodies.service.impl;

import lk.sliit.foodies.entity.LikeEntity;
import lk.sliit.foodies.entity.PostEntity;
import lk.sliit.foodies.entity.UserEntity;
import lk.sliit.foodies.exception.PostException;
import lk.sliit.foodies.exception.UserException;
import lk.sliit.foodies.repository.LikeRepository;
import lk.sliit.foodies.repository.PostRepository;
import lk.sliit.foodies.repository.UserRepository;
import lk.sliit.foodies.service.LikeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static lk.sliit.foodies.constant.MsgConstant.RESOURCE_NOT_FOUND;

@Service
@Log4j2
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int manageLike(long userId, long postId) {
        try {

            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if (!userEntity.isPresent()) throw new UserException(RESOURCE_NOT_FOUND, "User not found");

            Optional<PostEntity> postEntity = postRepository.findById(postId);
            if(!postEntity.isPresent()) throw new PostException(RESOURCE_NOT_FOUND, "Post not found");

            Optional<LikeEntity> likeEntity = likeRepository.getLikeByPostIdAndUserId(postEntity.get(), userEntity.get());

            if(likeEntity.isPresent()) {
                likeRepository.delete(likeEntity.get());
            } else {
                LikeEntity like = likeRepository.save(new LikeEntity(postEntity.get(), userEntity.get(), new Date()));
            }
            return likeRepository.getLikeCountByPostId(postEntity.get());

        } catch (Exception e) {
            throw e;
        }
    }
}
