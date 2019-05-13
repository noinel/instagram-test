package com.white.isntagram.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/*
 *	@Jsonignore를 사용하면 객체를 참조하지않는다. 
 * 
 */

@Data
@Entity
public class Tags {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@JsonIgnoreProperties({"username","name","website","bio","gedder","phone","createDate","updateDate"})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private Users user;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "imageId")
	@JsonBackReference
	private Images image;

	//이렇게하면 시리어라이저블(직렬화)이 되지않는다.
	//public Images getImages(){
	// return null;
	//}
	
	@CreationTimestamp
	private LocalDate createDate;
	@CreationTimestamp
	private LocalDate updateDate;
	
}
