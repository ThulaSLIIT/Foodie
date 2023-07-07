package lk.sliit.foodies.service.impl;

import lk.sliit.foodies.dto.PostDTO;
import lk.sliit.foodies.dto.UserDTO;
import lk.sliit.foodies.entity.PostEntity;
import lk.sliit.foodies.entity.UserEntity;
import lk.sliit.foodies.enums.PostStatus;
import lk.sliit.foodies.exception.PostException;
import lk.sliit.foodies.exception.UserException;
import lk.sliit.foodies.repository.CommentRepository;
import lk.sliit.foodies.repository.LikeRepository;
import lk.sliit.foodies.repository.PostRepository;
import lk.sliit.foodies.repository.UserRepository;
import lk.sliit.foodies.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static lk.sliit.foodies.constant.MsgConstant.*;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private HttpServletRequest request;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public boolean createPost(long userId, String title, String description, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4) {
        try {

            log.info("Execute createPost: " + userId + ", " + title + ", " + description);

            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if (!userEntity.isPresent()) throw new UserException(RESOURCE_NOT_FOUND, "User not found");

            String path1 = null;
            String path2 = null;
            String path3 = null;
            String path4 = null;

            if (image1!=null) {
                path1 = saveImage(image1);
            }
            if (image2!=null) {
                path2 = saveImage(image2);
            }
            if (image3!=null) {
                path3 = saveImage(image3);
            }
            if (image4!=null) {
                path4 = saveImage(image4);
            }

            postRepository.save(new PostEntity(title, description, path1, path2, path3, path4, userEntity.get(), new Date(), PostStatus.ACTIVE));

            return true;

        } catch (Exception e) {
            log.info("error createPost: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean updatePost(long postId, String title, String description, MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4, List<Long> deleteImg) {
        log.info("Execute updatePost: img: " + image1 + ", " + image1 instanceof String);
        try {
            Optional<PostEntity> postEntity = postRepository.findById(postId);
            if (!postEntity.isPresent()) throw new PostException(RESOURCE_NOT_FOUND, "Post not found");

            PostEntity post = postEntity.get();

            String path1 = null;
            String path2 = null;
            String path3 = null;
            String path4 = null;

            if (title != null) {
                post.setTitle(title);
            }

            if (description != null) post.setDescription(description);

            if (image1!=null) {
                path1 = saveImage(image1);
                post.setImage1(path1);
            }

            if (image2!=null) {
                System.out.println("F**k3");
                path2 = saveImage(image2);
                post.setImage2(path2);
            }

            if (image3!=null) {
                System.out.println("F**k4");
                path3 = saveImage(image3);
                post.setImage3(path3);
            }

            if (image4!=null) {
                path4 = saveImage(image4);
                post.setImage4(path4);
            }

            for (long id : deleteImg) {
                if (id == 1) post.setImage1(null);
                if (id == 2) post.setImage2(null);
                if (id == 3) post.setImage3(null);
                if (id == 4) post.setImage4(null);
            }

            postRepository.save(post);

            return true;
        } catch (Exception e) {
            log.error("Execute updatePost: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deletePost(long postId) {
        try {

            Optional<PostEntity> postEntity = postRepository.findById(postId);
            if (!postEntity.isPresent()) throw new PostException(RESOURCE_NOT_FOUND, "Post not found");
            PostEntity post = postEntity.get();
            post.setPostStatus(PostStatus.DELETED);
            postRepository.save(post);
            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PostDTO> getPosts() {
        try {

            List<PostEntity> postEntities = postRepository.getAllDateOrder(PostStatus.ACTIVE);
            return preparePostDTOs(postEntities);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<PostDTO> getPostsByUserId(long userId) {
        try {

            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if (!userEntity.isPresent()) throw new UserException(RESOURCE_NOT_FOUND, "User not found");

            List<PostEntity> postEntities = postRepository.getAllByUserDateOrder(userEntity.get(), PostStatus.ACTIVE);
            return preparePostDTOs(postEntities);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PostDTO getPostByPostId(long postId) {

        List<String> images = new ArrayList<>();

        try {

            Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
            if (!postEntityOptional.isPresent()) throw new PostException(RESOURCE_NOT_FOUND, "Post not found");

            PostEntity postEntity = postEntityOptional.get();

            String img1 = postEntity.getImage1();
            String img2 = postEntity.getImage2();
            String img3 = postEntity.getImage3();
            String img4 = postEntity.getImage4();

            if (img1 != null) images.add(img1);
            if (img2 != null) images.add(img2);
            if (img3 != null) images.add(img3);
            if (img4 != null) images.add(img4);

            int likes = likeRepository.getLikeCountByPostId(postEntity);
            int comments = commentRepository.getCommentCountByPostId(postEntity);

            return new PostDTO(
                    postEntity.getId(),
                    new UserDTO(
                            postEntity.getUserEntity().getId(),
                            postEntity.getUserEntity().getEmail(),
                            postEntity.getUserEntity().getFirstName(),
                            postEntity.getUserEntity().getLastName(),
                            postEntity.getUserEntity().getDate()
                    ),
                    postEntity.getTitle(),
                    postEntity.getDescription(),
                    images,
                    img1,
                    img2,
                    img3,
                    img4,
                    postEntity.getDate(),
                    likes,
                    comments
            );

        } catch (Exception e) {
            throw e;
        }
    }

    private String saveImage(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadFolder = "E:\\TARGET-PROJECTS\\Foodies-UNI\\foodies-uni-2023-backend\\src\\main\\webapp\\"; // path to the folder where you want to save the file
                String fileName = file.getOriginalFilename();
                String filepath = uploadFolder + File.separator + fileName;
                Path path = Paths.get(filepath);

                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

                return "http://localhost:8080/" + fileName;

            } catch (IOException e) {
                log.info("error saveImage ioEx: " + e.getMessage());
                return null;
            } catch (Exception e) {
                log.info("error saveImage: " + e);
                throw e;
            }
        } else {
            log.info("error saveImage condition: ");
            return null;
        }
    }

    private List<PostDTO> preparePostDTOs(List<PostEntity> postEntities) {
        List<PostDTO> postDTOS = new ArrayList<>();
        List<String> images = new ArrayList<>();
        try {
            for (PostEntity postEntity : postEntities) {

                images = new ArrayList<>();

                String img1 = postEntity.getImage1();
                String img2 = postEntity.getImage2();
                String img3 = postEntity.getImage3();
                String img4 = postEntity.getImage4();

                if (img1 != null) images.add(img1);
                if (img2 != null) images.add(img2);
                if (img3 != null) images.add(img3);
                if (img4 != null) images.add(img4);

                int likes = likeRepository.getLikeCountByPostId(postEntity);
                int comments = commentRepository.getCommentCountByPostId(postEntity);

                postDTOS.add(new PostDTO(
                        postEntity.getId(),
                        new UserDTO(
                                postEntity.getUserEntity().getId(),
                                postEntity.getUserEntity().getEmail(),
                                postEntity.getUserEntity().getFirstName(),
                                postEntity.getUserEntity().getLastName(),
                                postEntity.getUserEntity().getDate()
                        ),
                        postEntity.getTitle(),
                        postEntity.getDescription(),
                        images,
                        img1,
                        img2,
                        img3,
                        img4,
                        postEntity.getDate(),
                        likes,
                        comments
                ));
            }

            return postDTOS;
        } catch (Exception e) {
            throw e;
        }
    }

}