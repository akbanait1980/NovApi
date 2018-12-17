package com.learn.restapi.nov.NovApi.user;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.learn.restapi.nov.NovApi.bean.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
