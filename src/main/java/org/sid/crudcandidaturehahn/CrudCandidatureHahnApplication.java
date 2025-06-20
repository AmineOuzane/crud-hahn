package org.sid.crudcandidaturehahn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CrudCandidatureHahnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudCandidatureHahnApplication.class, args);
    }

}
