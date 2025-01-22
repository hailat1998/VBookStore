package vbookstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication()
@ComponentScan(basePackages = {"com.hd.domain",
"com.hd.data",
"com.hd.web",
"com.hd.restclient"})
public class VBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(VBookStoreApplication.class);
    }

}
