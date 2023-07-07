package lk.sliit.foodies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private long id;
    private UserDTO user;
    private String title;
    private String description;
    private List<String> images;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private Date date;
    private int likes;
    private int comments;
}
