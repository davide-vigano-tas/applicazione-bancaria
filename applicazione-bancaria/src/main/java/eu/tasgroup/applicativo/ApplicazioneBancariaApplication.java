package eu.tasgroup.applicativo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ApplicazioneBancariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicazioneBancariaApplication.class, args);
	}

}
