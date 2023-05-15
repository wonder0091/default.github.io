package com.bypx.synthesis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//让mybatis能够识别interface文件
@MapperScan("com.bypx.synthesis.dao")
public class SynthesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynthesisApplication.class, args);
	}

}
