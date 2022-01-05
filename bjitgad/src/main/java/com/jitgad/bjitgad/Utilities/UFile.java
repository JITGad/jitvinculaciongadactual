package com.jitgad.bjitgad.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.xml.bind.DatatypeConverter;
import net.iharder.Base64;
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

    public boolean B64StringtoImageFile(String base64, String fileurl) {
        try {
            String encoded = DatatypeConverter.printBase64Binary(base64.getBytes("UTF-8"));
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(encoded);
            DatatypeConverter.parseBase64Binary(encoded);
            FileUtils.writeByteArrayToFile(new File(fileurl), decodedBytes);
            return true;
        } catch (Exception e) {
            System.out.println("Error creating image:" + e.getMessage());
            return false;
        }
    }
    
     public boolean B64StringtoImageFileC(String base64, String fileurl) {
        String[] parts = base64.split(",");
        try {
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(parts[1]);
            DatatypeConverter.parseBase64Binary(parts[1]);
            FileUtils.writeByteArrayToFile(new File(fileurl), decodedBytes);
            return true;
        } catch (Exception e) {
            System.out.println("Error creating image:" + e.getMessage());
            return false;
        }
    }

    public String encodetext(String value) {
        return Base64.encodeBytes(value != null ? value.getBytes() : null);
    }

    public String encodeFileToBase64Binary(File file) throws IOException {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = org.apache.commons.codec.binary.Base64.encodeBase64(bytes).toString();

            byte[] bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64(bytes);
            encodedfile = new String(bytesEncoded);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        return encodedfile;
    }

    public String decode(String base64) {
        try {
            byte[] value = Base64.decode(base64);
            return new String(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

}
