package com.Instagram.chatApp.models;

import com.Instagram.chatApp.dto.UserDto;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String caption;
    private String image;
    private String location;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",column = @Column(name="user_id")),
            @AttributeOverride(name="email",column = @Column(name="user_email"))

    })
    private UserDto user;
    @OneToMany
    private List<Comment> comments = new ArrayList<Comment>();

    @Embedded
    @ElementCollection
    @JoinTable(name="likedByUsers", joinColumns = @JoinColumn(name="post_id",referencedColumnName = "id"))
    private Set<UserDto> likedByUser = new HashSet<UserDto>();

    public Post(Integer id, String caption, String image, String location, LocalDateTime createdAt, UserDto user, List<Comment> comments, Set<UserDto> likedByUser) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.location = location;
        this.createdAt = createdAt;
        this.user = user;
        this.comments = comments;
        this.likedByUser = likedByUser;
    }

    public Post(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<UserDto> getLikedBy() {
        return likedByUser;
    }

    public void setLikedBy(Set<UserDto> likedByUser) {
        this.likedByUser = likedByUser;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
