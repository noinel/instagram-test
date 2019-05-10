package com.white.isntagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.white.isntagram.model.Tags;

public interface TagRepository extends JpaRepository<Tags, Integer> {

}
