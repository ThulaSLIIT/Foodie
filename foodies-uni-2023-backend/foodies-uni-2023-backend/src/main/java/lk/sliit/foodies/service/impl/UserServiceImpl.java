package lk.sliit.foodies.service.impl;

import lk.sliit.foodies.dto.LoginDTO;
import lk.sliit.foodies.dto.UserDTO;
import lk.sliit.foodies.entity.UserEntity;
import lk.sliit.foodies.exception.UserException;
import lk.sliit.foodies.repository.UserRepository;
import lk.sliit.foodies.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static lk.sliit.foodies.constant.MsgConstant.*;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) {
        try {

            Optional<UserEntity> userEntity = userRepository.findByEmail(loginDTO.getEmail());
            if (!userEntity.isPresent()) throw new UserException(RESOURCE_NOT_FOUND, "User not found");
            UserEntity user = userEntity.get();
            return new UserDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getDate());

        } catch (Exception e) {
            throw e;
        }
    }
}
