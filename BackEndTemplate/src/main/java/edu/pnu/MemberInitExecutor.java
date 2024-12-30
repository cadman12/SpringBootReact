package edu.pnu;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.repository.BoardRepository;
import edu.pnu.repository.MemberRepository;

@Component
public class MemberInitExecutor implements ApplicationRunner {

	@Autowired
	private MemberRepository memRepo;
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member user = Member.builder()
						.username("user")
						.password(encoder.encode("abcd"))
						.role(Role.ROLE_USER)
						.nickName("홍길동")
						.enabled(true)
						.build();
		memRepo.save(user);

		Member manager = Member.builder()
							.username("manager")
							.password(encoder.encode("abcd"))
							.role(Role.ROLE_MANAGER)
							.nickName("이순신")
							.enabled(true)
							.build();
		memRepo.save(manager);
		
		for (int i = 1 ; i <= 10 ; i++) {
			boardRepo.save(Board.builder()
				.title("title" + i)
				.content("content" + i)
				.member(user)
				.build()
			);
		}
		for (int i = 100 ; i <= 105 ; i++) {
			boardRepo.save(Board.builder()
				.title("title" + i)
				.content("content" + i)
				.member(manager)
				.build()
			);
		}	
	}
}