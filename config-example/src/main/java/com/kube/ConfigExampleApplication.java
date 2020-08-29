package com.kube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController("/")
public class ConfigExampleApplication {


	@Value("${message:default}")
	private String message;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConfigExampleApplication.class, args);
	}

	@GetMapping
	public String getMessage(HttpServletRequest request)
	{
		System.out.println(request.getLocalAddr());
		return message + " "+"Local IP:"+request.getLocalAddr();
	}

	@GetMapping("/discover")
	public String discover(){
		ResponseEntity<String> response=null;
		String url  = "http://users-service/";
		System.out.println(url+" Connecting........");
		response = restTemplate.getForEntity(url,String.class);
		return  response ==null?"Unknown":response.getBody();
	}

}
