
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;

/**
 *
 * @author jorge
 */
public class GameimageDAO {
   
  Conection con;
  String sentence;

  public GameimageDAO() {
  con = new Conection();
  }
  
  public String selectGameimage(){
    sentence = "select * from tblgameimage";
    String json = con.getRecordsInJson(sentence);
    return json;
  }
  
  
  
}
