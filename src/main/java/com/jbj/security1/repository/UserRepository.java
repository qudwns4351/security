package com.jbj.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbj.security1.model.User;

//@Repository 가 없어도 ioc가능. jpa리포지토리 상속해서 // 자동 빈 등록
public interface UserRepository extends JpaRepository<User, Integer> {	
	// findBy 규칙 -> Username문법
	// select * from user where username = 1?
	public User findByUsername(String username); //jpa Query methods
}
