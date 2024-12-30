package edu.pnu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping("/member")
	public ResponseEntity<?> getMembers(String id) {
		
		Map<String, Object> ret = new HashMap<>();
		ret.put("key", "member");
		
		if (id == null) {
			log.info("getMembers: All");
			ret.put("jsondata", memberService.getMembers());
		}
		else {
			log.info("getMembers: " + id);
			ret.put("jsondata", memberService.getMember(id));
		}
		return ResponseEntity.ok(ret);
	}
}








