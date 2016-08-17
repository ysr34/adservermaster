package com.ads.servicepost;
/*
 * @author Srinivas Yannam
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/")
public class AdServerRESTService {

	/*
	 * 
    /api/adService – POST call – we will use this with our test
    /api/verify – GET call – just to make sure service started successfully

	 * 
	 */
	
	@POST
	@Path("/adService")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adServiceREST(InputStream incomingData) {
		StringBuilder strBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
			return Response.status(400).entity(strBuilder.toString()).build();
		}
		System.out.println("Data Received: " + strBuilder.toString());
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(strBuilder.toString()).build();
	}
 
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "Ad Server RESTService Successfully started..";
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
}
