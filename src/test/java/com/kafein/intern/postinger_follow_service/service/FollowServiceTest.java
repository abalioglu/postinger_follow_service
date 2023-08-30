package com.kafein.intern.postinger_follow_service.service;

import com.kafein.intern.postinger_follow_service.model.Follow;
import com.kafein.intern.postinger_follow_service.repository.FollowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FollowServiceTest {
    @Mock
    private FollowRepository followRepository;
    @Mock
    private IdentityServiceClient identityServiceClient;
    @Mock
    private HttpServletRequest request;

    private FollowService followService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        followService = new FollowService(followRepository, identityServiceClient);
    }
/*
    @Test
    public void testGetToken() {
        Cookie cookie = new Cookie("JWT_TOKEN", "deneme_jwt_token");
        when(request.getCookies()).thenReturn(new Cookie[] { cookie });
        String jwt = followService.getToken(request);
        assertEquals("deneme_jwt_token", jwt);
    }
 */

    @Test
    void followUser() {
        Long followerId = 1L;
        Long followedId = 2L;
        Follow relationship = new Follow();
        relationship.setFollowerId(followerId);
        relationship.setFollowedId(followedId);

        when(followRepository.save(relationship)).thenReturn(relationship);

        followService.followUser(followerId, followedId);
        //followrep mock'undaki save bir kere herhangi bir follow objesiyle çağırılıyor mu
        verify(followRepository, times(1)).save(relationship);
    }

    @Test
    void isFollowing() {
        Long followerId = 1L;
        Long followedId = 2L;
        when(followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)).thenReturn(true);
        boolean result = followService.isFollowing(followerId, followedId);
        assertTrue(result);
    }

    @Test
    void unfollowUser() {
        Long followerId = 1L;
        Long followedId = 2L;
        Follow relationship = new Follow();
        relationship.setFollowerId(followerId);
        relationship.setFollowedId(followedId);

        when(followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)).thenReturn(true);
        when(followRepository.findByFollowerIdAndFollowedId(followerId, followedId)).thenReturn(relationship);

        followService.unfollowUser(followerId, followedId);

        verify(followRepository, times(1)).delete(relationship);
    }

    @Test
    void getFollowedIds() {
        Long followerId = 1L;
        List<Long> followedIds = new ArrayList<>();
        followedIds.add(2L);
        followedIds.add(3L);

        when(followRepository.findFollowedIdsByFollowerId(followerId)).thenReturn(followedIds);
        List<Long> result = followService.getFollowedIds(followerId);
        assertEquals(followedIds, result);
    }

    @Test
    void getFollowerIds() {
        Long followedId = 2L;
        List<Long> followerIds = new ArrayList<>();
        followerIds.add(1L);
        followerIds.add(3L);
        //mock'un nasıl davranması gerektiğini göstremek için when().thenReturn()
        when(followRepository.findFollowerIdsByFollowedId(followedId)).thenReturn(followerIds);
        List<Long> result = followService.getFollowerIds(followedId);
        assertEquals(followerIds, result);
    }

    @Test
    void getFollowerCount(){
        Long followedId = 1L;
        Long expectedFollowerCount = 5L;
        when(followRepository.countByFollowedId(followedId)).thenReturn(expectedFollowerCount);
        Long actualFollowerCount = followService.getFollowerCount(followedId);
        verify(followRepository, times(1)).countByFollowedId(followedId);
        org.junit.jupiter.api.Assertions.assertEquals(expectedFollowerCount, actualFollowerCount);
    }
    @Test
    void getFollowedCount(){
        Long followerId = 1L;
        Long expectedFollowedCount = 10L;
        when(followRepository.countByFollowerId(followerId)).thenReturn(expectedFollowedCount);
        Long actualFollowedCount = followService.getFollowedCount(followerId);

        verify(followRepository, times(1)).countByFollowerId(followerId);
        org.junit.jupiter.api.Assertions.assertEquals(expectedFollowedCount, actualFollowedCount);
    }
}