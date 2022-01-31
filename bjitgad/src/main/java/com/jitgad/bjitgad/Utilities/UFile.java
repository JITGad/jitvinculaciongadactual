package com.jitgad.bjitgad.Utilities;

import com.jitgad.bjitgad.DataStaticBD.Configuration;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;

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

        String reponsePath = new File(fullPath).getPath() + File.separatorChar;
        // to read a file from webcontent
        return reponsePath;
    }

    public boolean saveFile(String base64, String fileurl) {
        String[] parts = base64.split(",");
        try {
            byte[] dataBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(parts[1]);
            try (FileOutputStream out = new FileOutputStream(fileurl)) {
                out.write(dataBytes);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error creating image:" + e.getMessage());
            return false;
        }
    }

    /**
     * Obtain the extension.
     *
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

    public boolean B64StringtoImageFile(String base64, String fileurl) throws IOException {
        byte[] data = DatatypeConverter.parseBase64Binary(base64);
        File file = new File(fileurl);
        
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
            return true;
        } catch (IOException e) {
            if (Configuration.DEBUG) {
                System.err.println(e.getMessage());
            }
            throw e;
        }
    }

    public String encodeFileToBase64Binary(File file) throws IOException {
        String encodedfile;
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        encodedfile = new String(Base64.encodeBase64(bytes));
        return encodedfile;
    }

}
