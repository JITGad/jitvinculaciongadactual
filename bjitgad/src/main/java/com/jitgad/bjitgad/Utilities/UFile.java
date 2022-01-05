package com.jitgad.bjitgad.Utilities;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jorge
 */
public class UFile {

    public UFile() {
    }

    public String getPath() throws UnsupportedEncodingException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        System.out.println(fullPath);
        System.out.println(pathArr[0]);
        fullPath = pathArr[0];

        String reponsePath = "";
        // to read a file from webcontent
        reponsePath = new File(fullPath).getPath() + File.separatorChar;
        return reponsePath;
    }

    public boolean saveFile(String base64, String fileurl) {
        String[] parts = base64.split(",");
        try {
            byte[] dataBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(parts[1]);
            FileOutputStream out = new FileOutputStream(fileurl);
            out.write(dataBytes);
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error creating image:" + e.getMessage());
            return false;
        }
    }

    /**
     * Obtain the extension.
     * @param filename
     * @return 
     */
    public String extensionfile(String filename) {
        String fe = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            fe = filename.substring(i + 1);
        }
        return fe;
    }

    public boolean B64StringtoImageFile(String base64, String fileurl) {
        
        String base64String = "data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAAHkAAAB5C...";
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
        
        byte[] data = DatatypeConverter.parseBase64Binary(base64);
        File file = new File(fileurl);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String encodeFileToBase64Binary(File file) throws IOException {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return encodedfile;
    }

}

