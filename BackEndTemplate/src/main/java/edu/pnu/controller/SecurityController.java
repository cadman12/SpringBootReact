package edu.pnu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class SecurityController {

	@GetMapping()
	public ResponseEntity<?> home() {
		return ResponseEntity.ok("인증없이 볼 수 있는 데이터");
	}

	@PostMapping("/jwtcallback")
	public ResponseEntity<?> jwtcallback(HttpServletRequest request) {
		String jwtToken = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("jwtToken")) {
				try {
					jwtToken = URLDecoder.decode(cookie.getValue(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		}
		
		System.out.println("jwtcallback:" + jwtToken);
		
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtToken).build();
	}
	
	@GetMapping("/auth")
	public ResponseEntity<?> auth(@AuthenticationPrincipal User user) {
		if (user == null)	return ResponseEntity.ok("user is not found!");
		return ResponseEntity.ok(user.toString());
	}
}
