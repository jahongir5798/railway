package uz.jahonservice.railwayproject;

import org.springframework.boot.SpringApplication;

public class TestRailwayProjectApplication {

    public static void main(String[] args) {
        SpringApplication.from(RailwayProjectApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
