package com.white.isntagram.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * ManyToOne은 Many쪽에 FK가 들어간다
 * ManyToOne 기본 fetch전략이 EAGER = 바로들고옴.
 * OneToMany는 기본 fetch전략이 LAZY = 호출할 때 가져옴0
 */



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Images {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 102400000)
	
	private String location;
	private String caption;
	private String mimeType;
	private String fileName;
	private String filePath;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private Users user;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "image")
	@Builder.Default private List<Tags> tags = new ArrayList<Tags>();
	
	@CreationTimestamp
	private LocalDate createDate;
	@CreationTimestamp
	private LocalDate updateDate;
}
