package io.github.learnjaxws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.learnjaxws.ws.AircraftInterface;
import io.github.learnjaxws.ws.AircraftService;
import io.github.learnjaxws.ws.messages.AcknowledgeAircraftType;
import io.github.learnjaxws.ws.messages.GetAircraftType;
import io.github.learnjaxws.ws.messages.ShowAircraftType;
import io.github.learnjaxws.ws.messages.UpdateAircraftType;
import io.github.learnjaxws.ws.schemas.AircraftType;
import io.github.learnjaxws.ws.schemas.ManufacturerType;

@ActiveProfiles("unittest")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LearnJaxwsServerApplicationTests {

	@LocalServerPort
	private int testServerPort;

	private AircraftInterface aircraftInterface;

	@Before
	public void setup() {
		String endpoint = String.format("http://localhost:%d/ws/AircraftService", testServerPort);
		URL wsdlLocation = AircraftService.class.getResource("/META-INF/services/service.wsdl");
		AircraftService aircraftService = new AircraftService(wsdlLocation,
				new QName("https://learnjaxws.github.io/learn/webservice/soap", "AircraftService"));
		this.aircraftInterface = aircraftService.getAircraftInterfaceBinding();
		Map<String, Object> requestContext = ((BindingProvider) aircraftInterface).getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	@Test
	public void testPing() {

		String response = aircraftInterface.ping("message");
		assertNotNull(response);
		assertEquals("message", response);
	}

	@Test
	public void testGetAircraftService() {

		GetAircraftType request = new GetAircraftType();
		request.setDesignation("");
		request.setName("Fighting Falcon");
		ShowAircraftType response = aircraftInterface.getAircraft(request);
		assertNotNull(response.getAircrafts());
		assertEquals(1, response.getAircrafts().size());
		assertEquals("F-16", response.getAircrafts().get(0).getDesignation());
	}

	@Test
	public void testUpdateAircraftService() {

		ManufacturerType boeing = new ManufacturerType();
		boeing.setName("Boeing");
		boeing.setHeadquarters("Chicago, Illinois");
		Date founded = Date.from(LocalDate.of(1916, Month.JULY, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());
		boeing.setFounded(founded);

		AircraftType b52 = new AircraftType();
		b52.setDesignation("B-52");
		b52.setName("Stratofortress");
		b52.setNickname("BUFF");
		b52.setManufacturer(boeing);
		Date firstFlight = Date
				.from(LocalDate.of(1952, Month.APRIL, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());
		b52.setFirstFlight(firstFlight);
		b52.setProduced(744);

		UpdateAircraftType request = new UpdateAircraftType();
		request.setAircraft(b52);
		AcknowledgeAircraftType response = aircraftInterface.updateAircraft(request);
		assertNotNull(response);
		assertEquals(200, response.getCode());
		assertEquals("SUCCESS", response.getStatus());
	}
}
