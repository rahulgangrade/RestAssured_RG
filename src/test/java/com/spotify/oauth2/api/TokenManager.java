package com.spotify.oauth2.api;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

import java.time.Instant;
import java.util.HashMap;

import com.spotify.oauth2.utils.ConfigLoader;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TokenManager {
 //static String access_token = "BQBX7-zf87_QayI-sI2s6X7dBwC66RlhEBgm58KXzqlHyyJ2Nf-6_w5wczsM51BfRDAjI2kes7TgvWrZDHRgCMVvhlzcd7nhsfIQtkVq6L4u2nI_xB7JUMmrsJVoDx0c8JqlK4rQDfSP0BLDM5WC8BGIaa8l3YW9VdG7e5W71PGtWy0kftPHKVcenLGmgjy-01bnnlOxoML99exDH45dIm72uHCoNB-YtvBRw2mrB2ks";
	private static String  access_token;
	private static Instant expiry_time;
	
 public static synchronized String getToken() {
	
	 
	 try {
		 if (access_token==null || Instant.now().isAfter(expiry_time))
		 {
		System.out.println("Renewing Token.........");
		 Response response = renewToken();
		 access_token=  response.path("access_token");
		 int expiryDurationInSeconds= response.path("expires_in");
		 expiry_time= Instant.now().plusSeconds(expiryDurationInSeconds-300);
	} 
		 else  System.out.println("Token is good to use.....");
	 }catch (Exception e) {
		throw new RuntimeException("ABORT!!! Failed to get token");
		}
  return access_token;
	
	 
	
 }
 
 
 
 private static Response renewToken()
 {
	 HashMap<String, String> formParams= new HashMap<String,String>();
	 formParams.put("client_id",ConfigLoader.getInstance().getClientId());
	 formParams.put("client_secret",ConfigLoader.getInstance().getSecret());
	 formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
	 formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
	 
	Response response =  RestResource.postAccount(formParams);
	 
	if(response.statusCode()!=200) {
		throw  new RuntimeException("ABORT!!   Renew Token  failed");
	}
	return response;
 }
 
}
