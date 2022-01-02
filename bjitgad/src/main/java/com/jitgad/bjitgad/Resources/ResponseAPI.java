package com.jitgad.bjitgad.Resources;

/**
 *
 * @author jorge
 */
public class ResponseAPI {
    
     public ResponseAPI() {
    }
   
   public String getAdminResponse(String message, int CountingPage, String flag, String data){
       int TotalPages = (CountingPage / 10) + 1;
       String responseJson = "{\"message\":\""+message+"\",\"CountingPage\": "+CountingPage+",\"TotalPages\": "+TotalPages+",\"flag\": "+flag+",\"data\":" + data + "}";
       return responseJson;
   }
   
   public String getResponse(String message, String flag,String data){
       String responseJson = "{\"message\":\""+message+"\",\"flag\": "+flag+",\"data\":" + data + "}";
       return responseJson;
   }
    
}
