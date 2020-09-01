package jpabook.springbootjpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootJpashopApplication {

    public static void main(String[] args) {


        Member member = new Member();
        member.setId(1l);

        System.out.println("member id : " + member.getId());

        SpringApplication.run(SpringbootJpashopApplication.class, args);
    }
}
