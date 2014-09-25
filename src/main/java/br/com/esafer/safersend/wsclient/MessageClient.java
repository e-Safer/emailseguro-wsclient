package br.com.esafer.safersend.wsclient;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageClient 
{
	private static final Logger logger = LoggerFactory.getLogger(MessageClient.class);
	
	private String user;
	private String token;
	
	public MessageClient(String user, String token) 
	{
		this.user = user;
		this.token = token;
	}

	/**
	 * 
	 * @param url - endpoint que receberá requisição.
	 * @param pathname - caminho completo do arquivo a ser enviado.
	 * @param recipient - endereço de email do destinatário.
	 * @param subject - assunto do email.
	 * @param body - corpo do email em html.
	 * @return {@link org.apache.http.HttpResponse}
	 * @throws Exception
	 */
	public HttpResponse send(String url, String pathname, String recipient, String subject, String body) throws Exception 
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		MultipartEntity requestEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		
		File f = new File(pathname);
		
		requestEntity.addPart("doc", new FileBody(f));
		requestEntity.addPart("recipient", new StringBody(recipient));
		requestEntity.addPart("subject", new StringBody(subject));
		requestEntity.addPart("body", new StringBody(body));
		requestEntity.addPart("user", new StringBody(this.user));
		requestEntity.addPart("token", new StringBody(this.token));
		
		httppost.setEntity(requestEntity);

		logger.debug("-------------------------------------------------");
		logger.debug("Executing Request " + httppost.getRequestLine());
		logger.debug("-------------------------------------------------");
		 
		return httpClient.execute(httppost);
	}

}
