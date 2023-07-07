package lk.sliit.foodies.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public LikeEntity(PostEntity postEntity, UserEntity userEntity, Date date) {
        this.postEntity = postEntity;
        this.userEntity = userEntity;
        this.date = date;
    }
}
