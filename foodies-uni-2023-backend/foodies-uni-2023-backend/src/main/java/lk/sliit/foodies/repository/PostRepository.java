package lk.sliit.foodies.repository;

import lk.sliit.foodies.entity.PostEntity;
import lk.sliit.foodies.entity.UserEntity;
import lk.sliit.foodies.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE p.postStatus=:status ORDER BY p.date DESC")
    List<PostEntity> getAllDateOrder(@Param("status")PostStatus postStatus);

    @Query("SELECT p FROM PostEntity p WHERE p.userEntity = :user AND p.postStatus=:status ORDER BY p.date DESC")
    List<PostEntity> getAllByUserDateOrder(@Param("user")UserEntity userEntity, @Param("status")PostStatus postStatus);
}
