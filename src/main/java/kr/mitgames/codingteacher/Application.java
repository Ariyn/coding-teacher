package kr.mitgames.codingteacher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@Controller
public class Application {
    public final static Logger LOG = Logger.getGlobal();
    @RequestMapping("/")
    public String hello(Model model) {
        model.addAttribute("textData", "Hello, World!");
        return "index";
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

@Configuration
class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Application.LOG.setLevel(Level.INFO);
        String[] staticFilePaths = {"src/main/resources/css", "src/main/resources/image", "src/main/resources/script"};

        ResourceHandlerRegistration rhr = registry.addResourceHandler("/**");
        for(String path:staticFilePaths) {
            File currentDirFile = new File(path);
            rhr.addResourceLocations("file:" + currentDirFile.getAbsolutePath());
        }
    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}