// package com.bt.bt07;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class Bt07Application {

// 	public static void main(String[] args) {
// 		SpringApplication.run(Bt07Application.class, args);
// 	}

// }

package com.bt.bt07;

import com.bt.bt07.model.User;
import com.bt.bt07.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Bt07Application {

	public static void main(String[] args) {
		SpringApplication.run(Bt07Application.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepo.findById("admin").isEmpty()) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("123"));
				admin.setFullname("Administrator");
				admin.setEmail("admin@test.com");
				admin.setAdmin(true); // set quyền Admin
				admin.setActive(true); // set tài khoản hoạt động
				userRepo.save(admin);
			}
		};
	}
}