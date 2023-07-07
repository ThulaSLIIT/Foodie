package lk.sliit.foodies.controller;

import lk.sliit.foodies.dto.CommonResponseDTO;
import lk.sliit.foodies.dto.LoginDTO;
import lk.sliit.foodies.dto.UserDTO;
import lk.sliit.foodies.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("v1/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping(value = "/post/{postId}/user/{userId}")
    public ResponseEntity manageLike(@PathVariable long postId, @PathVariable long userId) {
        int result = likeService.manageLike(userId, postId);
        return new ResponseEntity(new CommonResponseDTO(true, "Like managed successfully", result), HttpStatus.OK);
    }
}
