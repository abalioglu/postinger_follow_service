package com.kafein.intern.postinger_follow_service.controller;

import com.kafein.intern.postinger_follow_service.jwt.JwtUtil;
import com.kafein.intern.postinger_follow_service.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;
    private final JwtUtil jwtUtil;

    @PostMapping("/follow-user")
    public ResponseEntity<?> followUser(@RequestParam(name = "followedId") Long followedId, HttpServletRequest request) {
        String jwt = followService.getToken(request);
        Long followerId = jwtUtil.extractIdClaim(jwt);
        if (followerId.equals(followedId)) {
            return ResponseEntity.badRequest().body("You cannot follow yourself.");
        }
        if (followService.isFollowing(followerId, followedId)) {
            return ResponseEntity.badRequest().body("You are already following this user.");
        }
        followService.followUser(followerId, followedId);
        followService.incrementFollowed(followerId);
        followService.incrementFollower(followedId);
        return ResponseEntity.ok("You are now following user with ID: " + followedId);
    }
    @DeleteMapping("/unfollow-user")
    public ResponseEntity<?> unfollowUser(@RequestParam(name = "followedId") Long followedId, HttpServletRequest request) {
        String jwt = followService.getToken(request);
        Long followerId = jwtUtil.extractIdClaim(jwt);

        followService.unfollowUser(followerId,followedId);
        followService.decrementFollowed(followerId);
        followService.decrementFollower(followedId);
        return ResponseEntity.badRequest().body("You have now unfollowed user with ID: " + followedId);
    }
    @GetMapping("/following-list")
    public ResponseEntity<?> getFollowedUsers(@RequestParam(name = "followerId") Long followerId, HttpServletRequest request) {
        List<Long> followingIds = followService.getFollowedIds(followerId);
        return ResponseEntity.ok(followingIds);
    }

    @GetMapping("/follower-list")
    public ResponseEntity<?> getFollowers(@RequestParam(name = "followedId") Long followedId,HttpServletRequest request) {
        List<Long> followerIds = followService.getFollowerIds(followedId);
        return ResponseEntity.ok(followerIds);
    }
    @GetMapping("/follower-count")
    public ResponseEntity<Long> getFollowerCount(@RequestParam(name = "followedId") Long followedId){
        return ResponseEntity.ok(followService.getFollowerCount(followedId));
    }
    @GetMapping("/followed-count")
    public ResponseEntity<Long> getFollowedCount(@RequestParam(name = "followerId") Long followerId){
        return ResponseEntity.ok(followService.getFollowedCount(followerId));
    }

}
