package edu.pnu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/manager")
public class ManagerController {

	private final MemberService memberService;
	
	@GetMapping()
	public ResponseEntity<?> manager() {
		return ResponseEntity.ok("text response type3 sample");
	}
	
	@GetMapping("/member")
	public ResponseEntity<?> getMembers(String username) {
		if (username == null) {
			log.info("getMembers: All");
			return ResponseEntity.ok(memberService.getMembers());
		}

		log.info("getMembers: " + username);
		List<Member> list = new ArrayList<>();
		list.add(memberService.getMember(username));
		return ResponseEntity.ok(list);
	}
}







