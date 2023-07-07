package lk.sliit.foodies.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.sliit.foodies.enums.PostStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column(length = 2000)
    private String description;
    @Column(length = 1000)
    private String image1;
    @Column(length = 1000)
    private String image2;
    @Column(length = 1000)
    private String image3;
    @Column(length = 1000)
    private String image4;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    public PostEntity(String title, String description, String image1, String image2, String image3, String image4, UserEntity userEntity, Date date, PostStatus postStatus) {
        this.title = title;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.userEntity = userEntity;
        this.date = date;
        this.postStatus = postStatus;
    }
}
