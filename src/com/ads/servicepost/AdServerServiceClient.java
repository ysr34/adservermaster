package com.ads.servicepost;
/*
 * @author Srinivas Yannam
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
 




import org.json.JSONObject;
public class AdServerServiceClient {

	public void callByGet()
	{
		try {
		 URL url = new URL("http://localhost:8080/adServerPost/api/adService?partner_id=2"
				 	+"&duration=2000"
				 	+ "&ad_Content=Testing with GET");
		 System.out.println("invoking Rest service using url "+ url);
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");

	        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                + conn.getResponseCode());
	        }

	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	        }

	        conn.disconnect();

	      } catch (MalformedURLException e) {

	        e.printStackTrace();

	      } catch (IOException e) {

	        e.printStackTrace();

	     }		 
		 
	}
	public void callByFile()
	{
		String string = "";
		try {
 
			// Step1: Let's 1st read file from fileSystem
			// c:\\test\\test.json

			InputStream adClientInputStream = new FileInputStream("c:\\test\\test.json");
			InputStreamReader adClientReader = new InputStreamReader(adClientInputStream);
			BufferedReader br = new BufferedReader(adClientReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
 
			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);
			// step 1a: verify is the service is up and running or not
			// http://localhost:8080/adServerPost/api/verify
			// Step2: Now pass JSON File Data to REST Service
			// @ http://localhost:8080/adServerPost/api/adService
			try {
				
				URL url = new URL("http://localhost:8080/adServerPost/api/adService");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				while (in.readLine() != null) {
				}
				System.out.println("\n Ad Server REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				System.out.println("\n Error while calling Ad Server REST Service");
				System.out.println(e);
			}
 
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		AdServerServiceClient adsClient = new AdServerServiceClient();
		//created a .json file which contains my query attributes
		adsClient.callByFile();
		adsClient.callByGet();
	}
	
}
