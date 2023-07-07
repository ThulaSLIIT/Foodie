package lk.sliit.foodies.controller;

import lk.sliit.foodies.dto.CommonResponseDTO;
import lk.sliit.foodies.dto.LoginDTO;
import lk.sliit.foodies.dto.PostDTO;
import lk.sliit.foodies.dto.UserDTO;
import lk.sliit.foodies.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPost(
            @RequestPart("userId") String userId,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart(name = "image1", required = false) MultipartFile image1,
            @RequestPart(name = "image2", required = false) MultipartFile image2,
            @RequestPart(name = "image3", required = false) MultipartFile image3,
            @RequestPart(name = "image4", required = false) MultipartFile image4
    ) {
        boolean result = postService.createPost(
                Long.parseLong(userId),
                title,
                description,
                image1,
                image2,
                image3,
                image4
        );
        return new ResponseEntity(new CommonResponseDTO(true, "Post create successfully", result), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePost(
            @RequestPart("postId") String postId,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart(name = "image1", required = false) MultipartFile image1,
            @RequestPart(name = "image2", required = false) MultipartFile image2,
            @RequestPart(name = "image3", required = false) MultipartFile image3,
            @RequestPart(name = "image4", required = false) MultipartFile image4,
            @RequestPart("deleteImg") String deleteImg
    ) {

        System.out.println("LLLLLLLLLLLLLLLLL: " + deleteImg);

        List<Long> deleteImgListLong = new ArrayList<>();
        if(!deleteImg.equals("null")) {
            System.out.println("LLLLLLLLLLLLLLLLL: " + deleteImg);
            if(deleteImg.replaceAll("\"", "")!="") {
                String[] str = deleteImg.split(",");
                for (String img : str) {
                    img = img.replaceAll("\"", "");
                    long r = Long.parseLong("" + img);
                    deleteImgListLong.add(r);
                }
            }
        }
        boolean result = postService.updatePost(Long.parseLong(postId), title, description, image1, image2, image3, image4, deleteImgListLong);
        return new ResponseEntity(new CommonResponseDTO(true, "Post updated successfully", result), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{postId}")
    public ResponseEntity login(@PathVariable long postId) {
        boolean result = postService.deletePost(postId);
        return new ResponseEntity(new CommonResponseDTO(true, "Post deleted successfully", result), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getPosts() {
        List<PostDTO> result = postService.getPosts();
        return new ResponseEntity(new CommonResponseDTO(true, "Posts found successfully", result), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity getPostByUserId(@PathVariable long userId) {
        List<PostDTO> result = postService.getPostsByUserId(userId);
        return new ResponseEntity(new CommonResponseDTO(true, "Posts found successfully", result), HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity getPostByPostId(@PathVariable long postId) {
        PostDTO result = postService.getPostByPostId(postId);
        return new ResponseEntity(new CommonResponseDTO(true, "Post found successfully", result), HttpStatus.OK);
    }

}
