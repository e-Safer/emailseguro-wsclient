package br.com.esafer.safersend.wsclient;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class MessageClientTest 
{
	private static final Logger logger = LoggerFactory.getLogger(MessageClientTest.class);
	private static final String url = "http://emailseguro/mail/attach-send";
	private static final String PATHNAME = "<caminho completo do arquivo>";
	private static final String user = "<user>";
	private static final String token = "<token>";
	
	@Test
	public void sendMessageWithAttachment() throws Exception 
	{
		MessageClient client = new MessageClient(user, token);

		HttpResponse response = client
				.send(url, PATHNAME, "recipient@e-safer.com.br",
						"Email de teste",
						"<html><head></head><body>Teste de Envio de Email</body></html>");
		
		HttpEntity responseEntity = response.getEntity();
		
		Assert.assertNotNull(responseEntity);
		
		String page = EntityUtils.toString(responseEntity);
		logger.debug("-------------------------------------------------");
		logger.debug(page);
		logger.debug("-------------------------------------------------");

		assertEquals(201, response.getStatusLine().getStatusCode());
	}
	
}
