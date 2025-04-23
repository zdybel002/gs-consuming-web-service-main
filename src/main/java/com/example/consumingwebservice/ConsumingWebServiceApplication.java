
package com.example.consumingwebservice;

import com.example.consumingwebservice.proxy.StudentClient;
import com.example.consumingwebservice.wsdl.GetAllStudentsResponse;
import com.example.consumingwebservice.wsdl.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ConsumingWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(StudentClient studentClient) {
		return args -> {
			GetAllStudentsResponse response = studentClient.getAllStudents();
			for (Student s : response.getStudent()) {
				System.out.println("Student: " + s.getName() + ", ID: " + s.getId());
				s.getCourses().forEach(c -> {
					System.out.println("  - Course: " + c.getTitle());
				});
			}
		};
	}
}


