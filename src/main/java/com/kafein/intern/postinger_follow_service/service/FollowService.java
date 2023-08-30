package com.kafein.intern.postinger_follow_service.service;

import com.kafein.intern.postinger_follow_service.model.Follow;
import com.kafein.intern.postinger_follow_service.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final IdentityServiceClient identityServiceClient;

    public String getToken(HttpServletRequest request){
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }return jwt;
    }
    public void followUser(Long followerId, Long followedId) {
        Follow relationship = new Follow();
        relationship.setFollowerId(followerId);
        relationship.setFollowedId(followedId);

        followRepository.save(relationship);
    }

    public boolean isFollowing(Long followerId, Long followedId) {
        return followRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }

    public void unfollowUser(Long followerId,Long followedId){
        if(isFollowing(followerId, followedId)) {
            followRepository.delete(followRepository.findByFollowerIdAndFollowedId(followerId, followedId));
        }else throw new RuntimeException("User with followerId is not following this user");
    }
    public List<Long> getFollowedIds(Long followerId) {
        return followRepository.findFollowedIdsByFollowerId(followerId);
    }
    public List<Long> getFollowerIds(Long followedId) {
        return followRepository.findFollowerIdsByFollowedId(followedId);
    }
    public Long getFollowerCount(Long followedId) { //takipçiler
        return followRepository.countByFollowedId(followedId);
    }
    public Long getFollowedCount(Long followerId) { //takip edilenler
        return followRepository.countByFollowerId(followerId);
    }
    public void incrementFollower(Long followedId){
        identityServiceClient.incrementFollowerCount(followedId);
    }
    public void decrementFollower(Long followedId){
        identityServiceClient.decrementFollowerCount(followedId);
    }
    public void incrementFollowed(Long followerId){
        identityServiceClient.incrementFollowedCount(followerId);
    }
    public void decrementFollowed(Long followerId){
        identityServiceClient.incrementFollowedCount(followerId);
    }
}
