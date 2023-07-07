package lk.sliit.foodies.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date date;
}
