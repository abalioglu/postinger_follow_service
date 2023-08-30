package com.kafein.intern.postinger_follow_service.repository;

import com.kafein.intern.postinger_follow_service.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);
    Follow findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    //takip edilenler
    @Query("SELECT f.followedId FROM Follow f WHERE f.followerId = :followerId")
    List<Long> findFollowedIdsByFollowerId(@Param("followerId") Long followerId);

    //takipçiler
    @Query("SELECT f.followerId FROM Follow f WHERE f.followedId = :followedId")
    List<Long> findFollowerIdsByFollowedId(@Param("followedId") Long followedId);
    Long countByFollowedId(Long followedId); //takipçiler
    Long countByFollowerId(Long followerId); //takip edilenler
}
