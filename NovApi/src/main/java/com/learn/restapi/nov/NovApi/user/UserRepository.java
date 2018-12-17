package com.learn.restapi.nov.NovApi.user;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.learn.restapi.nov.NovApi.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
