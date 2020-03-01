package io.github.learnjaxws;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.learnjaxws.ws.AircraftInterface;

@SpringBootTest
public class LearnJaxwsClientApplicationTests {

	@Autowired
	private AircraftInterface aircraftInterface;

	@Test
	public void contextLoads() {
		assertNotNull(aircraftInterface);
	}
}
