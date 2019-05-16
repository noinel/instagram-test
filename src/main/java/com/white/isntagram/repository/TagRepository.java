package com.white.isntagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.white.isntagram.model.Tags;

public interface TagRepository extends JpaRepository<Tags, Integer> {
	public List<Tags> findByNameContaining(String name);
}
