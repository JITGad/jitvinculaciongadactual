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

    public Object[] createfile(String base64String, String type) throws UnsupportedEncodingException {

        boolean band = false;
        UFile uf = new UFile();

        String[] strings = base64String.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            case "data:video/mp4;base64":
                extension = "mp4";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }

        String baserutarelativa = (uf.getPath() + "").replace('\\', '/');
        String baseextraimage = "";

        Object[] Permt = validateformat(extension, baserutarelativa);
        if (Boolean.parseBoolean(Permt[0].toString())) {

            if (createfilebase(Permt[1].toString())) {
                String ruta = Permt[1].toString() + type + "/";
                System.out.println(ruta);
                //COMPROBAR SI EXISTEN LAS CARPETAS DE IMAGENES O VIDEOS
                // FIN COMPROBACIÓN CARPETAS IMAGENES O VIDEO
                if (!new File(ruta).exists()) {  //se comprueba si la ruta existe o no
                    System.out.println("El directorio " + new File(ruta).getName() + " no existe");
                    System.out.println(ruta);
                    if (new File(ruta).mkdir()) { //se crea la ruta. Si se ha creado correctamente
                        System.out.println("Directorio creado");
//                        System.out.println((uf.B64StringtoImageFile(strings[1], ruta + "ARCHIVOGENERADOJAVA.jpeg"))
//                                ? "Imagen creada" : "Imagen no creada");
                        band = uf.B64StringtoImageFile(strings[1], ruta + "ARCHIVOGENERADOJAVA.jpeg");
                        return new Object[]{band, Permt[2], "ARCHIVOGENERADOJAVA.jpeg"};
                      //  return band;
                    } else {
                        System.out.println("No se ha podido crear " + new File(ruta).getName());
                    }
                } else {
                    // System.out.println("El directorio ya existe");
//                    System.out.println((uf.B64StringtoImageFile(strings[1], ruta + "ARCHIVOGENERADOJAVA.jpeg"))
//                            ? "Imagen creada" : "Imagen no creada");
                    band = uf.B64StringtoImageFile(strings[1], ruta + "ARCHIVOGENERADOJAVA.jpeg");
                    return new Object[]{band, Permt[2], "ARCHIVOGENERADOJAVA.jpeg"};
                }
            } else {
                System.out.println("No se ha podido crear archivo base " + new File(baseextraimage).getName());
                return new Object[]{band, Permt[2], "ARCHIVOGENERADOJAVA.jpeg"};
            }
        }
        return new Object[]{band, Permt[2], "ARCHIVOGENERADOJAVA.jpeg"};

    }

    public boolean createfilebase(String rutabase) {
        boolean band = false;
        if (!new File(rutabase).exists()) {
            if (!new File(rutabase).getName().equals("images")) {
                new File(rutabase).mkdir();
            } else {
                if (!new File(rutabase).getName().equals("videos")) {
                    new File(rutabase).mkdir();
                } else {
                    System.out.println("Archivos base creados");
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public Object[] validateformat(String extension, String baserutarelativa) {

        if (extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg")) {
            return new Object[]{true, (baserutarelativa + "images/").replace('\\', '/'), "images"};
        } else {
            if (extension.equals("mp4")) {
                return new Object[]{true, (baserutarelativa + "video/").replace('\\', '/'), "video"};
            } else {
                System.out.println("NO EXISTE ESTA EXTENSIÓN");
                return new Object[]{false, ""};
            }
        }
    }

}
