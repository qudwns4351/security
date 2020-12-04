package com.jbj.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jbj.security1.model.User;
import com.jbj.security1.repository.UserRepository;

@Controller //View를 리턴하겠다.
public class IndexController {

		@Autowired
		private UserRepository userRepository;
	
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
		
		@GetMapping({"","/"})
		public String index() {
			//머스테치 기본폴더 src/main/resources/
			//뷰리졸버 설정 : templates(prefix), mustach(suffix) 생략가능!
			return "index"; //src/main/resources/templates/index.mustache
		}
		
		@GetMapping("/user")
		public @ResponseBody String user() {
			return "user";
		}
		
		@GetMapping("/admin")
		public @ResponseBody String admin() {
			return "admin";
		}
		
		@GetMapping("/manager")
		public @ResponseBody String manager() {
			return "manager";
		}
		
		//스프링시큐리티 해당주소를 낚아챔
		@GetMapping("/loginForm")
		public String loginForm() {
			return "loginForm";
		}
		
		@GetMapping("/joinForm")
		public String joinForm() {
			return "joinForm";
		}
		
		@PostMapping("/join")
		public String join(User user) {
			user.setRole("ROLE_USER");
			String rawPassword = user.getPassword();
			String encPassword = bCryptPasswordEncoder.encode(rawPassword);
			user.setPassword(encPassword);
			userRepository.save(user); //	
			return "redirect:/loginForm";
		}				
}
