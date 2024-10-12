package com.gizasystems.PoC;

import com.gizasystems.PoC.logging.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoCApplication {

	public static void main(String[] args) {
		FileUtils.createLogsDirectory();
		SpringApplication.run(PoCApplication.class, args);
	}

}
