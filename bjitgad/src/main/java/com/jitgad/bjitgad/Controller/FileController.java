package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.Utilities.UFile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class FileController {

    public FileController() {
    }

    public boolean createfile(String rutaimg) throws UnsupportedEncodingException {
         File ruta = new File("C:/Users/jorge/Downloads/");
       // File f = new File(ruta, "WhatsApp Image 2022-01-03 at 16.48.07.jpeg");
        File f = new File(rutaimg);
        UFile uf = new UFile();
        
        String type = "activities";
        
        String brimage = "images/"+type+"/".replace('\\', '/');;
        String brivideo = "videos/"+type+"/".replace('\\', '/');;
        
        String baserutarelativa = (uf.getPath() + "").replace('\\', '/');
        String baseextraimage = (baserutarelativa + brimage).replace('\\', '/');
        
        System.out.println(baseextraimage);

        if (!new File(baseextraimage).exists()) {  //se comprueba si la ruta existe o no
            System.out.println("El directorio " + new File(baseextraimage).getName() + " no existe");
            if (new File(baseextraimage).mkdir()) { //se crea la ruta. Si se ha creado correctamente
                System.out.println("Directorio creado");
                try {
                    System.out.println((uf.B64StringtoImageFile(uf.encodeFileToBase64Binary(f),baseextraimage + "ARCHIVOGENERADOJAVA.jpeg"))
                    ?"Imagen creada":"Imagen no creada");
                } catch (IOException ex) {
                    Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("No se ha podido crear " + new File(baseextraimage).getName());
            }
        } else {
           // System.out.println("El directorio ya existe");
            try {
               System.out.println((uf.B64StringtoImageFile(uf.encodeFileToBase64Binary(f),baseextraimage + "ARCHIVOGENERADOJAVA.jpeg"))
                    ?"Imagen creada":"Imagen no creada");
            } catch (IOException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

}
