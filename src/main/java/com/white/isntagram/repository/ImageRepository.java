package com.white.isntagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.white.isntagram.model.Images;

public interface ImageRepository extends JpaRepository<Images, Integer>{

}
