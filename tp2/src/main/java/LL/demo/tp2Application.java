package LL.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import LL.demo.models.ERole;
import LL.demo.models.Role;
import LL.demo.repositories.RoleRepository;



@SpringBootApplication

@EnableJpaRepositories(basePackages="LL.demo")

public class tp2Application implements CommandLineRunner{
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(tp2Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
	    
	   
		// TODO Auto-generated method stub
		 	    roleRepository.save(new Role(ERole.ROLE_USER));
	            roleRepository.save(new Role(ERole.ROLE_ADMIN));
	
	}
}
