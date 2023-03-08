package com.lr.blog.dao;

import com.lr.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

//使用spring boot里的JPA
public interface UserRepository extends JpaRepository<User,Long> {//操作的对象user,long类型

    User findByUsernameAndPassword(String username, String password); //定义方法，遵循这个命名规则，根据用户名和密码查询数据库，继承JPA直接有方法
}