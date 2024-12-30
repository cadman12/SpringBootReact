package edu.pnu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	@Query("select b from Board b where b.member.username = :username")
	List<Board> findByUsername(String username);
}
