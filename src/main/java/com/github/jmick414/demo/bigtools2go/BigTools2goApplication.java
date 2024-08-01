package com.github.jmick414.demo.bigtools2go;

import com.github.jmick414.demo.bigtools2go.checkout.CheckoutRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BigTools2goApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(BigTools2goApplication.class, args);
		final CheckoutRunner checkoutRunner = context.getBean(CheckoutRunner.class);
		checkoutRunner.run();
	}

}
