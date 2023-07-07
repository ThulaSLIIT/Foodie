package lk.sliit.foodies.repository;

import lk.sliit.foodies.entity.CommentEntity;
import lk.sliit.foodies.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hp
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT COUNT(c) FROM CommentEntity c WHERE c.postEntity = :post")
    int getCommentCountByPostId(@Param("post") PostEntity postEntity);

    @Query("SELECT c FROM CommentEntity c WHERE c.postEntity = :post ORDER BY c.date DESC")
    List<CommentEntity> getCommentByPostDateOrder(@Param("post")PostEntity postEntity);

}
