package lk.sliit.foodies.controller;

import lk.sliit.foodies.dto.CommonResponseDTO;
import lk.sliit.foodies.dto.LoginDTO;
import lk.sliit.foodies.dto.UserDTO;
import lk.sliit.foodies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        UserDTO result = userService.login(loginDTO);
        return new ResponseEntity(new CommonResponseDTO(true, "User details found successfully", result), HttpStatus.OK);
    }

}
