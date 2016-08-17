package com.ads.servicepost;
/*
 * @author Srinivas Yannam
 */
 import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.ads.util.JSONUtils;

public class GsonPostRequestSample {

	 private String partner_id;
	    private int duration;
	    private String ad_content;
	    StringEntity params;
	    GsonPostRequestSample(String partner_id,int duration, String ad_content)
	    {
	    	this.partner_id = partner_id;
	    	this.duration = duration;
	    	this.ad_content = ad_content;
	    }
    public static void main(String[] args) {
    	// specify the body as 
    	/*{
    		 "partner_id": "unique_string_representing_partner',
    		 "duration": "int_representing_campaign_duration_in_seconds_from_now"
    		 "ad_content": "string_of_content_to_display_as_ad"
    		} */
    	GsonPostRequestSample reqGson = new GsonPostRequestSample("1",5000,"My First Ad");
    	
     		reqGson.httpPOSTCall("http://localhost:8080/adServerPost/api/adService");
    }

    private boolean isValidParams()
    {

		try {
			String a = "{\"partner_id\":\"" + this.partner_id + "\",\"duration\":\"" + this.duration + "\",\"ad_content\":\"" + this.ad_content + "\"}";
			this.params = new StringEntity(a);

			return JSONUtils.isJSONValid_databind(a);
					//.isJSONValid(validJsonString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    
    public HttpResponse httpPOSTCall(String url) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            System.out.println("Service URL: "+ url.toString());
            if(isValidParams())
            {	
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            com.google.gson.Gson gson = new com.google.gson.Gson();
            Response resObj = gson.fromJson(json, Response.class);
            System.out.println("Response partner_id: " + resObj.getPartner_id());
            System.out.println("Ad Content: "+resObj.getAd_content());
            return result;
            }else
            {
            	
            	
            	System.out.println("Please send the right Params");
            }
        } catch (IOException ex) {
        	 System.out.println("invalid json format");
        }
        return null;
    }
   
    public class Response{

       
        private String partner_id;
	    private int duration;
	    private String ad_content; 
		public String getPartner_id() {
			return partner_id;
		}
		public void setPartner_id(String partner_id) {
			this.partner_id = partner_id;
		}
		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
		public String getAd_content() {
			return ad_content;
		}
		public void setAd_content(String ad_content) {
			this.ad_content = ad_content;
		}
    }
}