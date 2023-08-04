package com.Instagram.chatApp.Repository;

import com.Instagram.chatApp.models.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    @Query("select p from Post p where p.user.id=?1")
    public List<Post> findByUserId(Integer userId);

    @Query("select p from Post p where p.user.id IN :users ORDER BY p.createdAt desc")
    public List<Post> findAllPostsByUserIds(@Param("users") List<Integer>userIds);
}
