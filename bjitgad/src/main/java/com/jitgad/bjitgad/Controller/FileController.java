package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.Utilities.ExtensionFile;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.UFile;
import com.jitgad.bjitgad.Utilities.ValidateFormat;
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

    public ResponseCreateFile createfile(String base64String, String type, String name, String realpath) throws Exception {
        UFile uf = new UFile();
        File rutaPadre = new File(realpath).getParentFile();
        String RutaRelativa = String.join(File.separator, new String[]{"jit", "images"});
        File RutaBaseGuardar = new File(String.join(File.separator, new String[]{rutaPadre.getAbsolutePath(), RutaRelativa}));
        if (!RutaBaseGuardar.exists()) {
            RutaBaseGuardar.mkdirs();
        }
        String[] strings = base64String.split(",");
        ExtensionFile extension = getExtension(strings[0]);
        ValidateFormat format = validateformat(extension.getExtension(), RutaBaseGuardar, RutaRelativa);
        if (format.isFormatValid()) {
            if (createfilebase(format.getRutaAbsoluta())) {
                File RutaGuardar = new File(String.join(File.separator, new String[]{format.getRutaAbsoluta(), type}));
                //COMPROBAR SI EXISTEN LAS CARPETAS DE IMAGENES O VIDEOS
                if (!RutaGuardar.exists()) {  //se comprueba si la ruta existe o no
                    RutaGuardar.mkdirs();
                }
                String NombreArchivo = String.join(".", new String[]{name, extension.getExtension()});
                String rutaArchivo = String.join(File.separator, new String[]{RutaGuardar.getAbsolutePath(), NombreArchivo});
                return new ResponseCreateFile(uf.B64StringtoImageFile(strings[1], rutaArchivo), format.getRutaRelativa(), NombreArchivo);
            }
        }
        return new ResponseCreateFile(false, "", "");
    }

    private ExtensionFile getExtension(String base64) {
        ExtensionFile extension = new ExtensionFile(true);
        switch (base64) {//check image's extension
            case "data:image/jpeg;base64":
                extension.setExtension("jpeg");
                break;
            case "data:image/png;base64":
                extension.setExtension("png");
                break;
            case "data:video/mp4;base64":
                extension.setExtension("mp4");
                break;
            case "data:image/jpg;base64":
                extension.setExtension("jpg");
                break;
            case "data:audio/mpeg;base64":
                extension.setExtension("mpeg");
                break;
            default:
                extension.setExt(false);
                extension.setExtension("");
                break;
        }
        return extension;
    }

    public boolean createfilebase(String rutabase) {
        // SI SON VARIAS CARPETAS, SE SEPARA LOS NOMBRES DE CADA CARPETA CON UN 
        // String[] carpetas = rutabase.split("/");
        File RutaBaseFile = new File(rutabase);
        if (!RutaBaseFile.exists()) {
            return RutaBaseFile.mkdirs();
        }
        return true;
    }

    public ValidateFormat validateformat(String extension, File RutaBase, String RutaRelativa) {
        ValidateFormat format = new ValidateFormat(true);
        if (extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg")) {
            format.setRutaAbsoluta(String.join(File.separator, new String[]{RutaBase.getAbsolutePath(), "images"}));
            format.setRutaRelativa(String.join(File.separator, new String[]{RutaRelativa, "images"}));
            return format;
        }

        if (extension.equals("mp4")) {
            format.setRutaAbsoluta(String.join(File.separator, new String[]{RutaBase.getAbsolutePath(), "video"}));
            format.setRutaRelativa(String.join(File.separator, new String[]{RutaRelativa, "video"}));
            return format;
        }

        if (extension.equals("mpeg")) {
            format.setRutaAbsoluta(String.join(File.separator, new String[]{RutaBase.getAbsolutePath(), "audio"}));
            format.setRutaRelativa(String.join(File.separator, new String[]{RutaRelativa, "audio"}));
            return format;
        }

        if (Configuration.DEBUG) {
            System.out.println("NO EXISTE ESTA EXTENSIÓN");
        }
        return new ValidateFormat(false, "", "");
    }

}
