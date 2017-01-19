package io.github.learnjaxws;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.learnjaxws.ws.AircraftInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnJaxwsClientApplicationTests {

	@Autowired
	private AircraftInterface aircraftInterface;

	@Test
	public void contextLoads() {
		assertNotNull(aircraftInterface);
	}
}
