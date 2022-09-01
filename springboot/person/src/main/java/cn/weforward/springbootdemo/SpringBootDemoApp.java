package cn.weforward.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class SpringBootDemoApp {

	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringBootDemoApp.class, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
