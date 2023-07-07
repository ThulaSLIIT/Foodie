package lk.sliit.foodies.repository;

import lk.sliit.foodies.entity.LikeEntity;
import lk.sliit.foodies.entity.PostEntity;
import lk.sliit.foodies.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author hp
 */
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.postEntity = :post")
    int getLikeCountByPostId(@Param("post") PostEntity postEntity);

    @Query("SELECT l FROM LikeEntity l WHERE l.postEntity = :post AND l.userEntity = :user")
    Optional<LikeEntity> getLikeByPostIdAndUserId(@Param("post") PostEntity postEntity, @Param("user")UserEntity userEntity);

}
