package cc.yanyong.teampassman;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cc.yanyong.teampassman.mapper")
public class TeampassmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeampassmanApplication.class, args);
	}

}
