package com.kafein.intern.postinger_follow_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "postinger-identity-service", url = "http://localhost:8090")
public interface IdentityServiceClient {
    @PostMapping("/user/increment-follower-count")
    ResponseEntity<?> incrementFollowerCount(@RequestParam(name = "followedId") Long followedId);
    @PostMapping("/user/decrement-follower-count")
    ResponseEntity<?> decrementFollowerCount(@RequestParam(name = "followedId") Long followedId);
    @PostMapping("/user/increment-followed-count")
    ResponseEntity<?> incrementFollowedCount(@RequestParam(name = "followerId") Long followerId);
    @PostMapping("/user/decrement-followed-count")
    ResponseEntity<?> decrementFollowedCount(@RequestParam(name = "followerId") Long followerId);
}
