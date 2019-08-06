package com.springboot.spring_data_jpa;

import com.springboot.spring_data_jpa.entity.Customer;
import com.springboot.spring_data_jpa.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @SpringBootApplication is a convenience annotation that adds all of the following:
 * @Configuration tags the class as a source of bean definitions for the application context.
 * @EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * <p>
 * Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
 * @ComponentScan tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the controllers.
 * <p>
 * By default, Spring Boot will enable JPA repository support and look in the package (and its subpackages) where @SpringBootApplication is located.
 * If your configuration has JPA repository interface definitions located in a package not visible,
 * you can point out alternate packages using @EnableJpaRepositories and its type-safe basePackageClasses=MyRepository.class parameter.
 */
@SpringBootApplication
public class SpringDataJpaApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringDataJpaApplication.class);

    /**
     * The main() method uses Spring Boot’s SpringApplication.run() method to launch an application.
     * <p>
     * Application includes a main() method that puts the CustomerRepository through a few tests.
     * <p>
     * By default, Spring Boot will enable JPA repository support and look in the package (and its subpackages) where @SpringBootApplication is located.
     * If your configuration has JPA repository interface definitions located in a package not visible, you can point out alternate packages using @EnableJpaRepositories and its type-safe basePackageClasses=MyRepository.class parameter.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    /**
     * First, it fetches the CustomerRepository from the Spring application context.
     * Then it saves a handful of Customer objects, demonstrating the save() method and setting up some data to work with.
     * Next, it calls findAll() to fetch all Customer objects from the database.
     * Then it calls findOne() to fetch a single Customer by its ID.
     * Finally, it calls findByLastName() to find all customers whose last name is "Bauer".
     *
     * @param repository
     * @return
     */
    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            log.error("纸纷飞");
            // save a couple of customers
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}
