package lk.sliit.foodies.service;

import lk.sliit.foodies.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hp
 */
public interface PostService {
    boolean createPost(
            long userId,
            String title,
            String description,
            MultipartFile image1,
            MultipartFile image2,
            MultipartFile image3,
            MultipartFile image4
    );

    boolean updatePost(
            long postId,
            String title,
            String description,
            MultipartFile image1,
            MultipartFile image2,
            MultipartFile image3,
            MultipartFile image4,
            List<Long> deleteImg
    );

    boolean deletePost(long postId);

    List<PostDTO> getPosts();

    List<PostDTO> getPostsByUserId(long userId);

    PostDTO getPostByPostId(long postId);
}
