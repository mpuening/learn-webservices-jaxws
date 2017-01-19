package io.github.learnjaxws.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.learnjaxws.ws.AircraftInterface;
import io.github.learnjaxws.ws.AircraftService;

@Configuration
public class WebServiceClientConfig {

	/**
	 * The end point is defined by the context that the AxisServlet is running
	 * on and the Service Name as defined in the server-config.wsdd file
	 */
	@Value("${ws.endpoint:http://localhost:8080/ws/AircraftService}")
	protected String endpoint;

	@Value("${ws.username:username}")
	protected String username;

	@Value("${ws.password:password}")
	protected String password;

	@SuppressWarnings("rawtypes")
	@Bean
	public AircraftInterface aircraftInterface() throws MalformedURLException {
		URL wsdlLocation = AircraftService.class.getResource("/META-INF/services/service.wsdl");
		AircraftService aircraftService = new AircraftService(wsdlLocation,
				new QName("https://learnjaxws.github.io/learn/webservice/soap", "AircraftService"));
		AircraftInterface aircraftInterface = aircraftService.getAircraftInterfaceBinding();
		
		// Basic HTTP Authentication, see application.yml for how the
		// credentials are set in this example
		Map<String, Object> requestContext = ((BindingProvider) aircraftInterface).getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
		
	    Binding binding = ((BindingProvider) aircraftInterface).getBinding();
	    List<Handler> handlerChain = binding.getHandlerChain();
	    handlerChain.add(new SOAPLogHandler());
	    binding.setHandlerChain(handlerChain);


		return aircraftInterface;
	}
}