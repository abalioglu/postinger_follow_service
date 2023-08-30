package com.kafein.intern.postinger_follow_service.repository;

import com.kafein.intern.postinger_follow_service.model.Follow;
import com.kafein.intern.postinger_follow_service.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class FollowRepositoryTest {
    @Autowired
    private FollowRepository followRepository;

    @Test
    void existsByFollowerIdAndFollowedId() {
        Follow follow = new Follow();
        follow.setFollowerId(1L);
        follow.setFollowedId(2L);
        followRepository.save(follow);
        boolean exists = followRepository.existsByFollowerIdAndFollowedId(1L, 2L);
        assertThat(exists).isTrue();
    }

    @Test
    void findByFollowerIdAndFollowedId() {
        Follow follow = new Follow();
        follow.setFollowerId(3L);
        follow.setFollowedId(4L);
        followRepository.save(follow);
        Follow foundFollow = followRepository.findByFollowerIdAndFollowedId(3L, 4L);
        assertThat(foundFollow).isNotNull();
        assertThat(foundFollow.getFollowerId()).isEqualTo(3L);
        assertThat(foundFollow.getFollowedId()).isEqualTo(4L);
    }

    @Test
    void findFollowedIdsByFollowerId() {
        Follow follow1 = new Follow();follow1.setFollowerId(1L);follow1.setFollowedId(2L);
        Follow follow2 = new Follow();follow2.setFollowerId(1L);follow2.setFollowedId(3L);
        followRepository.save(follow1);followRepository.save(follow2);
        List<Long> result = followRepository.findFollowedIdsByFollowerId(1L);
        assertThat(result).containsExactly(2L, 3L);
    }

    @Test
    void findFollowerIdsByFollowedId() {
        Follow follow1 = new Follow();follow1.setFollowerId(2L);follow1.setFollowedId(1L);
        Follow follow2 = new Follow();follow2.setFollowerId(3L);follow2.setFollowedId(1L);
        followRepository.save(follow1);followRepository.save(follow2);
        List<Long> result = followRepository.findFollowerIdsByFollowedId(1L);
        assertThat(result).containsExactly(2L, 3L);
    }

    @Test
    void countByFollowedId() { //takipçi
        Follow follow1 = new Follow();follow1.setFollowerId(2L);follow1.setFollowedId(1L);
        Follow follow2 = new Follow();follow2.setFollowerId(3L);follow2.setFollowedId(1L);
        followRepository.save(follow1);followRepository.save(follow2);
        Long result = followRepository.countByFollowedId(1L);
        assertNotNull(result);
        assertEquals(2, result);
    }

    @Test
    void countByFollowerId() {
        Follow follow1 = new Follow();follow1.setFollowerId(1L);follow1.setFollowedId(2L);
        Follow follow2 = new Follow();follow2.setFollowerId(1L);follow2.setFollowedId(3L);
        followRepository.save(follow1);followRepository.save(follow2);
        Long result = followRepository.countByFollowerId(1L);
        assertNotNull(result);
        assertEquals(2, result);
    }
}