package cloud.weforward.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import cn.weforward.boot.support.AbstractSpringApp;

/**
 * Hello world!
 */
@SpringBootApplication
public class Application extends AbstractSpringApp {

	public static void main(String[] args) {
		init();
		try {
			final ApplicationContext ac = SpringApplication.run(Application.class, args);
			end(ac);
		} catch (Throwable e) {
			initError(e);
		}
	}

}
