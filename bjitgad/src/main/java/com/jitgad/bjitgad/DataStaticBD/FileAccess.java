package com.jitgad.bjitgad.DataStaticBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 */
public class FileAccess {

    public FileAccess() {
    }

    public String readFileText(String location) throws IOException {
        String result = "";
        try {
            File archivo = new File(location);
            try (FileReader fr = new FileReader(archivo)) {
                try (BufferedReader br = new BufferedReader(fr)) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        result += linea;
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
        return result;
    }

    public boolean writeFileText(String location, String structure) throws IOException {
        try {
            try(FileWriter fichero  = new FileWriter(location)){
                try(PrintWriter pw = new PrintWriter(fichero)){
                    pw.println(structure);
                }
            }
        } catch (IOException e) {
            throw e;
        }
        return true;
    }

    public boolean SaveImg(String base64, String rutaImagen) throws IOException {
        File file = new File(rutaImagen);
        return writeOutputStream(base64, file);
    }

    private boolean writeOutputStream(String value, File outputStream) throws IOException {
        String[] partes = value.split(",");
        try {
            byte[] imgBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(partes[1]);
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
            ImageIO.write(bufImg, "png", outputStream);
            return true;
        } catch (IOException e) {
            throw e;
        }
    }
}
