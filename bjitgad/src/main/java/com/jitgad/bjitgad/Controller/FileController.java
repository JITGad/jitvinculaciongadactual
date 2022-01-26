package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DataStaticBD.Configuration;
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

    public Object[] createfile(String base64String, String type, String name, String realpath) throws UnsupportedEncodingException {

        boolean band = false;
        UFile uf = new UFile();

        String[] strings = base64String.split(",");
        String extension;
        boolean ext = false;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                ext = true;
                break;
            case "data:image/png;base64":
                extension = "png";
                ext = true;
                break;
            case "data:video/mp4;base64":
                extension = "mp4";
                ext = true;
                break;
            case "data:image/jpg;base64":
                extension = "jpg";
                ext = true;
                break;
            case "data:audio/mpeg;base64":
                extension = "mpeg";
                ext = true;
                break;
            default:
                extension = "";
                break;
        }
        try {
            String baserutarelativa = (realpath + "").replace('\\', '/');
            //  String baseextraimage = "";

            Object[] Permt = validateformat(extension, baserutarelativa);
            if (Boolean.parseBoolean(Permt[0].toString())) {

                if (createfilebase(Permt[1].toString())) {
                    String ruta = Permt[1].toString() + type + "/";
                    //COMPROBAR SI EXISTEN LAS CARPETAS DE IMAGENES O VIDEOS
                    if (!new File(ruta).exists()) {  //se comprueba si la ruta existe o no
                        //  System.out.println("El directorio " + new File(ruta).getName() + " no existe");
                        if (new File(ruta).mkdir()) { //se crea la ruta. Si se ha creado correctamente
                            //   System.out.println("Directorio creado");
                            band = uf.B64StringtoImageFile(strings[1], ruta + name + "." + extension);

                            return new Object[]{band, Permt[2], name + "." + extension};
                        } else {
                            // System.out.println("No se ha podido crear " + new File(ruta).getName());
                        }
                    } else {
                        band = uf.B64StringtoImageFile(strings[1], ruta + name + "." + extension);

                        return new Object[]{band, Permt[2], name + "." + extension};
                    }
                } else {
                    //   System.out.println("No se ha podido crear archivo base " + new File(baseextraimage).getName());
                    return new Object[]{band, Permt[2], name + "." + extension};
                }
            }
            return new Object[]{band, Permt[2], name + "." + extension};

        } catch (Exception e) {
            return new Object[]{ext, "", ""};
        }
    }

    public boolean createfilebase(String rutabase) {
        // SI SON VARIAS CARPETAS, SE SEPARA LOS NOMBRES DE CADA CARPETA CON UN 
        // String[] carpetas = rutabase.split("/");
        boolean band = false;
        if (!new File(rutabase).exists()) {
            if (!new File(rutabase).getName().equals("images")) {
                new File(rutabase).mkdir();
            } else {
                if (!new File(rutabase).getName().equals("videos")) {
                    new File(rutabase).mkdir();
                } else {
                    if (Configuration.DEBUG) {
                        System.out.println("Archivos base creados");
                    }
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
                if (extension.equals("mpeg")) {
                    return new Object[]{true, (baserutarelativa + "audio/").replace('\\', '/'), "audio"};
                } else {
                    if (Configuration.DEBUG) {
                        System.out.println("NO EXISTE ESTA EXTENSIÓN");
                    }
                    return new Object[]{false, ""};
                }
            }

        }
    }

}
