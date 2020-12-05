package com.jbj.security1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jbj.security1.config.auth.PrincipalDetails;
import com.jbj.security1.model.User;
import com.jbj.security1.repository.UserRepository;

@Controller //View를 리턴하겠다.
public class IndexController {

		@Autowired
		private UserRepository userRepository;
	
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
		
		@GetMapping("/test/login")
		public @ResponseBody String testLogin(
				Authentication authentication,
				@AuthenticationPrincipal PrincipalDetails userDetails){ //DI (의존성주입)
			System.out.println("/test/login ============");
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println("authentication :" + principalDetails.getUser());
			
			System.out.println("userDetails :" +userDetails.getUser());
			return "세션 정보 확인하기";
		}
		
		@GetMapping("/test/oauth/login")
		public @ResponseBody String testOAuthLogin(
				Authentication authentication,
				@AuthenticationPrincipal OAuth2User oauth){ //DI (의존성주입)
			System.out.println("/test/login ============");
			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
			System.out.println("authentication :" + oauth2User.getAttributes());
			System.out.println("oauth2User :" +oauth.getAttributes());
			return "OAuth 세션 정보 확인하기";
		}
		
		
		
		@GetMapping({"","/"})
		public String index() {
			//머스테치 기본폴더 src/main/resources/
			//뷰리졸버 설정 : templates(prefix), mustach(suffix) 생략가능!
			return "index"; //src/main/resources/templates/index.mustache
		}
		
		// OAuth 로그인을 해도 PrincipalDetails
		// 일반 로그인을 해도 PrincipalDetails
		@GetMapping("/user")
		public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails  ) {
			System.out.println("PrincipalDetails" +principalDetails.getUser());
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
		
		@Secured("ROLE_ADMIN")
		@GetMapping("/info")
		public @ResponseBody String info() {
			return "개인정보";		
		}
		
		@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		@GetMapping("/date")
		public @ResponseBody String date() {
			return "데이터정보";		
		}
}

