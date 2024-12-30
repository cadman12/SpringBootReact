package edu.pnu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/board")
	public ResponseEntity<?> getBoards(Long id, String writer) {
		
		Map<String, Object> ret = new HashMap<>();
		ret.put("key", "board");
		
		if (id != null) {
			log.info("getBoards: " + id);
			List<Board> list = new ArrayList<>();
			list.add(boardService.getBoard(id));
			ret.put("jsondata", list);
		}
		else if (writer != null) {
			log.info("getBoards: " + writer);
			ret.put("jsondata", boardService.getBoard(writer));
		}
		else {
			log.info("getBoards: All");
			ret.put("jsondata", boardService.getBoards());
		}
		return ResponseEntity.ok(ret);		
	}
}








