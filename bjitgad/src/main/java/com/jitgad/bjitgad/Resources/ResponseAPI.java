package com.jitgad.bjitgad.Resources;

/**
 *
 * @author jorge
 */
public class ResponseAPI {
    
     public ResponseAPI() {
    }
   
   public String AdminResponse(String message, int CountingPage, boolean flag, String data){
       int TotalPages = (CountingPage / 10) + 1;
       String responseJson = "{\"message\":\""+message+"\",\"CountingPage\": "+CountingPage+",\"TotalPages\": "+TotalPages+",\"flag\": "+flag+",\"data\":" + data + "}";
       return responseJson;
   }
   
   public String Response(String message, boolean flag,String data){
       String responseJson = "{\"message\":\""+message+"\",\"flag\": "+flag+",\"data\":" + data + "}";
       return responseJson;
   }
    
}
