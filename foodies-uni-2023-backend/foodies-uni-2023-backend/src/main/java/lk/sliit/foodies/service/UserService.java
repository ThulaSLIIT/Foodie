package lk.sliit.foodies.service;

import lk.sliit.foodies.dto.LoginDTO;
import lk.sliit.foodies.dto.UserDTO;

/**
 * @author hp
 */
public interface UserService {
    public UserDTO login(LoginDTO loginDTO);
}
