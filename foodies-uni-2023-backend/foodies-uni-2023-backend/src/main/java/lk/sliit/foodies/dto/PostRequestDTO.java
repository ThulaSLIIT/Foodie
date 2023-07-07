package lk.sliit.foodies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    private long userId;
    private String title;
    private String description;
    private File image1;
    private File image2;
    private File image3;
    private File image4;
}
