package com.kafein.intern.postinger_follow_service.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FOLLOW_TABLE", uniqueConstraints = {@UniqueConstraint(columnNames = {"follower_id", "followed_id"})})
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "followed_id")
    private Long followedId;
}
