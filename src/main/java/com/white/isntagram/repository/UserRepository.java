package com.white.isntagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.white.isntagram.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

}
