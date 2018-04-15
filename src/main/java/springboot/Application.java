package springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springboot.ltq.scheduled.JdOrder;

@SpringBootApplication
@MapperScan(basePackages = "springboot.ltq.dao")
@EnableScheduling
public class Application extends WebMvcConfigurerAdapter {
//    @Autowired
//    JdOrder jdOrder;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
//        jdOrder.computer();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
