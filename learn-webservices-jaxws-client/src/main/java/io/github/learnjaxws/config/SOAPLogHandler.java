package io.github.learnjaxws.config;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOAPLogHandler implements SOAPHandler<SOAPMessageContext> {

	private final Logger LOG = LoggerFactory.getLogger(SOAPLogHandler.class);

	@Override
	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		if (LOG.isDebugEnabled()) {
			SOAPMessage msg = context.getMessage();
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				msg.writeTo(baos);
				LOG.debug("SOAP MESSAGE: {}", baos.toString());
			} catch (Exception ex) {
				LOG.error("Unable to log soap message");
			}
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

}
