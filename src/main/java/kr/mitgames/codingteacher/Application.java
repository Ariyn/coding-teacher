package kr.mitgames.codingteacher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@Controller
public class Application {
    @RequestMapping("/")
    public String hello(Model model) {
        model.addAttribute("textData", "Hello, World!");
        return "index";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

@Configuration
class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
    private final static Logger LOG = Logger.getGlobal();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOG.setLevel(Level.INFO);
        File currentDirFile = new File("src/main/resources");
        String helper = currentDirFile.getAbsolutePath();
        LOG.warning("[resource folder] "+helper);
        registry.addResourceHandler("/**")
                .addResourceLocations("file:"+helper);
    }
}