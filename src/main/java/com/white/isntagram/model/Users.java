package com.white.isntagram.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Lob;
import javax.persistence.OneToMany;


import lombok.Data;

@Entity
@Data
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String name;
	private String website;
	private String bio;
	private String email;
	private String phone;
	private String gender;
	
//	@OneToMany(mappedBy = "from_user")
//	private List<Follow> from_user;
//	
//	@OneToMany(mappedBy = "to_user")
//	private List<Follow> to_user;
	
	@Lob
	private byte[] profile;
	
	private Timestamp create_date;
	private Timestamp update_date;
	
}
