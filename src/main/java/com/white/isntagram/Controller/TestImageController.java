package com.white.isntagram.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.white.isntagram.model.Images;
import com.white.isntagram.model.Tags;
import com.white.isntagram.model.Users;
import com.white.isntagram.repository.ImageRepository;
import com.white.isntagram.repository.TagRepository;
import com.white.isntagram.repository.UserRepository;
import com.white.isntagram.util.MyUtils;

@RestController
public class TestImageController {
	@Autowired
	private ImageRepository imageRepository;
	final String context = "TestImageController";
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@PostMapping("/image/upload")
	public Images imageUpload(@RequestParam("file") MultipartFile file, String caption, String location, String tags) throws IOException {
		Path filePath = Paths.get(MyUtils.getResourcePath()+file.getOriginalFilename());
		System.out.println(context+filePath);
		Files.write(filePath, file.getBytes());
		Users user =MyUtils.getUser();
		userRepository.save(user);
		
		List<String> tagList = MyUtils.tagParser(tags);
		Images image = Images.builder()
						.caption(caption)
						.location(location)
						.user(user)//user객체가 db가 flush되지않으면 image를 save할 수 없음.
						.mimeType(file.getContentType())
						.fileName(file.getOriginalFilename())
						.filePath("/image/"+file.getOriginalFilename())
						.build();
		imageRepository.save(image);
		
		for(String t : tagList) {
			Tags tag = new Tags();
			tag.setName(t);
			tag.setImage(image);
			tag.setUser(user);
			tagRepository.save(tag);
			image.getTags().add(tag);
		}
		
		
		return image;
		
	}
	
	@PostMapping("/test/image/upload")
	public ResponseEntity<Resource> imageUpload(@RequestParam("file") MultipartFile file) throws IOException {

		Images image = new Images();
		//image.setFile(file.getBytes());
		
		imageRepository.save(image);
		
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "\"")
                .body(new ByteArrayResource(file.getBytes()));
	
	


	}
}
