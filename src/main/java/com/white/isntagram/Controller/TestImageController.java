package com.white.isntagram.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/tag")
	public List<Tags> tag(String name) {
		List<Tags> tags = tagRepository.findByNameContaining(name);
		return tags;
	}
	
	@GetMapping("/image/{id}")
	public Images imageDetail(@PathVariable int id) {
		Optional<Images> imageOptional =
				imageRepository.findById(id);
		if(imageOptional.isPresent()) {
			Images image =imageOptional.get();
			return image;
			
		}else {
			return null;
		}
	}
	
	@GetMapping("/images")
	public List<Images> images(){
		List<Images> list = imageRepository.findAll();
		return list;
	}
	
	@GetMapping("/image/page")
	public Page<Images> ImageList(@PageableDefault(sort= {"id"}, direction = Direction.DESC, size = 5) Pageable pageable){
		Page<Images> list = imageRepository.findAll(pageable);
		
		return list;
		
		
	}
	@GetMapping("/image/page/{num}")
	public Page<Images> ImageList(@PathVariable int num, @PageableDefault(sort= {"id"}, direction = Direction.DESC, size = 5) Pageable pageable){
		
		Page<Images> list = imageRepository.findAll(pageable);
		
		return list;
		
		
	}
	
	@PostMapping("/image/upload")
	public Images imageUpload(@RequestParam("file") MultipartFile file, String caption, String location, String tags) throws IOException {
		Path filePath = Paths.get(MyUtils.getResourcePath()+file.getOriginalFilename());
		System.out.println(context+filePath);
		Files.write(filePath, file.getBytes());
		Users user =MyUtils.getUser();
		
		String password = user.getPassword();
		String encPassword = passwordEncoder.encode(password);
		System.out.println("password: "+ encPassword);
		user.setPassword(encPassword);
		
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
