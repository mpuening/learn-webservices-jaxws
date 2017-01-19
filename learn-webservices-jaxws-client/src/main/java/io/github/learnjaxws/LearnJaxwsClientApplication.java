package io.github.learnjaxws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.learnjaxws.ws.AircraftInterface;

@SpringBootApplication
public class LearnJaxwsClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LearnJaxwsClientApplication.class, args);
		invokeService(ctx.getBean(AircraftInterface.class));
	}

	/**
	 * Just a silly example to ping to the service
	 * 
	 * @param aircraftInterface
	 */
	protected static void invokeService(AircraftInterface aircraftInterface) {
		String response = aircraftInterface.ping("Ping Message");
		System.out.println("=====================");
		System.out.println(response);
		System.out.println("=====================");
	}
}
