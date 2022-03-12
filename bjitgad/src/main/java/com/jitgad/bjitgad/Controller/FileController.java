package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.Utilities.ExtensionFile;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.UFile;
import com.jitgad.bjitgad.Utilities.ValidateFormat;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author jorge
 */
public class FileController {

    public FileController() {
    }

    public ResponseCreateFile createfile(String base64String, String type, String name, String realpath) throws IOException {
        
        if(base64String == null || base64String.isEmpty()){
            return new ResponseCreateFile(false, "", "");
        }
        UFile uf = new UFile();
        Path rutaPadre = Paths.get(realpath).getParent();
        String RutaRelativa = String.join(File.separator, new String[]{"jit", "static"});
        
        Path RutaBaseGuardar = Paths.get(String.join(File.separator, new String[]{rutaPadre.toAbsolutePath().toString(), RutaRelativa}));
        if (Files.notExists(RutaBaseGuardar)) {
            Files.createDirectories(RutaBaseGuardar);
        }
        
        String[] strings = base64String.split(",");
        ExtensionFile extension = getExtension(strings[0]);
        ValidateFormat format = validateformat(extension.getExtension(), RutaBaseGuardar, RutaRelativa, type);
        if (format.isFormatValid()) {
            if (createfilebase(format.getRutaAbsoluta())) {
                Path RutaGuardar = Paths.get(String.join(File.separator, new String[]{format.getRutaAbsoluta(), type}));
                if (Files.notExists(RutaGuardar)) {  //se comprueba si la ruta existe o no
                    Files.createDirectories(RutaGuardar);
                }
                String NombreArchivo = String.join(".", new String[]{name + System.currentTimeMillis() , extension.getExtension()});
                String rutaArchivo = String.join(File.separator, new String[]{RutaGuardar.toAbsolutePath().toString(), NombreArchivo});
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

    public boolean createfilebase(String rutabase) throws IOException {
        // SI SON VARIAS CARPETAS, SE SEPARA LOS NOMBRES DE CADA CARPETA CON UN 
        // String[] carpetas = rutabase.split("/");
        Path RutaBaseFile = Paths.get(rutabase);
        if (Files.notExists(RutaBaseFile)) {
            Path result = Files.createDirectories(RutaBaseFile);
            return Files.exists(result);
        }
        return true;
    }

    public ValidateFormat validateformat(String extension, Path RutaBase, String RutaRelativa, String type) {
        ValidateFormat format = new ValidateFormat(true);
        if (extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg")) {
            format.setRutaAbsoluta(String.join(File.separator, new String[]{RutaBase.toAbsolutePath().toString(), "images"}));
            format.setRutaRelativa(String.join("/", new String[]{RutaRelativa.replace("\\", "/"), "images", type}));
            return format;
        }

        if (extension.equals("mp4")) {
            format.setRutaAbsoluta(String.join(File.separator, new String[]{RutaBase.toAbsolutePath().toString(), "video"}));
            format.setRutaRelativa(String.join(File.separator, new String[]{RutaRelativa.replace("\\", "/"), "video", type}));
            return format;
        }

        if (extension.equals("mpeg")) {
            format.setRutaAbsoluta(String.join(File.separator, new String[]{RutaBase.toAbsolutePath().toString(), "audio"}));
            format.setRutaRelativa(String.join(File.separator, new String[]{RutaRelativa.replace("\\", "/"), "audio", type}));
            return format;
        }

        if (Configuration.DEBUG) {
            System.out.println("NO EXISTE ESTA EXTENSIÓN");
        }
        return new ValidateFormat(false, "", "");
    }

}
