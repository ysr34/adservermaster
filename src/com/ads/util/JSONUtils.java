package com.ads.util;

import java.io.IOException;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSONUtils {
  private static final Gson gson = new Gson();

  private JSONUtils(){}

  public static boolean isJSONValid(String jsonInString) {
      try {
          gson.fromJson(jsonInString, Object.class);
          return true;
      } catch(com.google.gson.JsonSyntaxException ex) { 
          return false;
      }
  }
  public static boolean isJSONValid_databind(String jsonInString ) {
	    try {
	       final ObjectMapper mapper = new ObjectMapper();
	       mapper.readTree(jsonInString);
	       return true;
	    } catch (IOException e) {
	       return false;
	    }
	  }
}
