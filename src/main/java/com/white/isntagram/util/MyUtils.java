package com.white.isntagram.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.white.isntagram.model.Users;

public class MyUtils {
	public static String getResourcePath() {
		return "D://springinsta//instagram//src//main//resources//static//image//";
		
	}
	public static Users getUser() {
		Users user = Users.builder()
				.bio("white의 페이지")
				.email("cos@white.com")
				.gender("남")
				.name("최재원")
				.phone("01022225555")
				.username("cos")
				.website("blog.naver.com")
				.createDate(LocalDate.now())
				.updateDate(LocalDate.now())
				.build();
		
		
		return user;
	}
	
	public static List<String> tagParser(String tags){
		String temp[] = tags.split("#");
		List<String> tagList = new ArrayList<String>();
		int len = temp.length;
		for(int i = 1; i< len ; i++)
		{
			
			tagList.add(temp[i]);
		}
			
		return tagList;

	}
	
	
	
	
	
}
