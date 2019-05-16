package com.white.isntagram.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.white.isntagram.model.Users;
import com.white.isntagram.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		CustomUserDetails userDetails = null;
		Users user=userRepository.findByUsername(username);
		if (user != null) {
			userDetails = new CustomUserDetails();
			userDetails.setUser(user);
			return userDetails;
		}else {
			throw new UsernameNotFoundException("유저를 찾을 수없습니다."+username);
		}
	}

}
