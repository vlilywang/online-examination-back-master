package com.init.online_examination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

//@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
//		RequestMethod.DELETE })
@SpringBootApplication
public class OnlineExaminationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineExaminationApplication.class, args);
	}
}
