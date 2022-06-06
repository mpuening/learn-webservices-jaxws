package io.github.learnjaxws.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import io.github.learnjaxws.ws.messages.AcknowledgeAircraftType;
import io.github.learnjaxws.ws.messages.GetAircraftType;
import io.github.learnjaxws.ws.messages.ShowAircraftType;
import io.github.learnjaxws.ws.messages.UpdateAircraftType;
import io.github.learnjaxws.ws.schemas.AircraftType;
import io.github.learnjaxws.ws.schemas.ManufacturerType;

/**
 * Just support a very simple implementation for demonstration purposes, but you
 * should see how easily it would be to integrate with a database.
 */
@Endpoint
public class AircraftService {

	private static final String NAMESPACE_URI = "https://learnjaxws.github.io/learn/webservice/soap";

	protected final Map<String, AircraftType> aircrafts = new ConcurrentHashMap<>();

	public AircraftService() {
		ManufacturerType generalDynamics = new ManufacturerType();
		generalDynamics.setName("General Dynamics");
		generalDynamics.setHeadquarters("West Falls Church, Virginia");
		Date founded = Date
				.from(LocalDate.of(1899, Month.FEBRUARY, 7).atStartOfDay(ZoneId.systemDefault()).toInstant());
		generalDynamics.setFounded(founded);

		AircraftType f16 = new AircraftType();
		f16.setDesignation("F-16");
		f16.setName("Fighting Falcon");
		f16.setNickname("Viper");
		f16.setManufacturer(generalDynamics);
		Date firstFlight = Date
				.from(LocalDate.of(1974, Month.JANUARY, 20).atStartOfDay(ZoneId.systemDefault()).toInstant());
		f16.setFirstFlight(firstFlight);
		f16.setProduced(4_573);

		aircrafts.put(f16.getDesignation(), f16);

		ManufacturerType boeing = new ManufacturerType();
		boeing.setName("Boeing");
		boeing.setHeadquarters("Chicago, Illinois");
		founded = Date.from(LocalDate.of(1916, Month.JULY, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());
		boeing.setFounded(founded);

		AircraftType b17 = new AircraftType();
		b17.setDesignation("B-17");
		b17.setName("Flying Fortress");
		b17.setNickname("None");
		b17.setManufacturer(boeing);
		firstFlight = Date.from(LocalDate.of(1935, Month.JULY, 28).atStartOfDay(ZoneId.systemDefault()).toInstant());
		b17.setFirstFlight(firstFlight);
		b17.setProduced(12_731);

		aircrafts.put(b17.getDesignation(), b17);

		ManufacturerType lockheed = new ManufacturerType();
		lockheed.setName("Lockheed");
		lockheed.setHeadquarters("Burbank, California");
		founded = Date.from(LocalDate.of(1912, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		lockheed.setFounded(founded);

		AircraftType sr71 = new AircraftType();
		sr71.setDesignation("SR-71");
		sr71.setName("Blackbird");
		sr71.setNickname("None");
		sr71.setManufacturer(lockheed);
		firstFlight = Date
				.from(LocalDate.of(1964, Month.DECEMBER, 22).atStartOfDay(ZoneId.systemDefault()).toInstant());
		sr71.setFirstFlight(firstFlight);
		sr71.setProduced(32);

		aircrafts.put(sr71.getDesignation(), sr71);
	}

	@ResponsePayload
	@SoapAction("GetAircraft")
	public JAXBElement<ShowAircraftType> getAircraft(@RequestPayload JAXBElement<GetAircraftType> getAircraftType) {
		Optional<String> designation = Optional.of(getAircraftType.getValue().getDesignation());
		Optional<String> name = Optional.of(getAircraftType.getValue().getName());
		List<AircraftType> searchResults = aircrafts.values().stream()
				.filter(aircraft -> (aircraft.getDesignation().startsWith(designation.orElse(""))
						&& aircraft.getName().startsWith(name.orElse(""))))
				.collect(Collectors.toList());
		ShowAircraftType response = new ShowAircraftType();
		response.getAircrafts().addAll(searchResults);
		return new JAXBElement<ShowAircraftType>(new QName(NAMESPACE_URI, "GetAircraft"), ShowAircraftType.class,
				response);
	}

	@ResponsePayload
	@SoapAction("UpdateAircraft")
	public JAXBElement<AcknowledgeAircraftType> updateAircraft(
			@RequestPayload JAXBElement<UpdateAircraftType> updateAircraftType) {
		aircrafts.put(updateAircraftType.getValue().getAircraft().getDesignation(),
				updateAircraftType.getValue().getAircraft());
		AcknowledgeAircraftType response = new AcknowledgeAircraftType();
		response.setCode(200);
		response.setStatus("SUCCESS");
		return new JAXBElement<AcknowledgeAircraftType>(new QName(NAMESPACE_URI, "UpdateAircraft"),
				AcknowledgeAircraftType.class, response);
	}

	@ResponsePayload
	@SoapAction("Ping")
	public JAXBElement<String> ping(@RequestPayload JAXBElement<String> message) {
		return new JAXBElement<String>(new QName(NAMESPACE_URI, "Ping"), String.class, message.getValue());
	}
}
