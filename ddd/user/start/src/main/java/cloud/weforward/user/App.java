package cloud.weforward.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import cn.weforward.boot.support.AbstractSpringApp;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App extends AbstractSpringApp {

    public static void main(String[] args) {
        init();
        try {
            final ApplicationContext ac = SpringApplication.run(App.class, args);
            end(ac);
        } catch (Throwable e) {
            initError(e);
        }
    }

}

