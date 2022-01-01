
package com.jitgad.bjitgad.Utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import sun.awt.image.ToolkitImage;

/**
 *
 * @author jorge
 */
public class CropImage {
    
String ruta = "C:/Users/jorge/Downloads/Captura29-12-2021.png";
int dimensiones=150;
String[]partes= new String[16];

    public CropImage() throws IOException{
        
        int filas = 4;
        int columnas = 4;

        //File arc = new File(ruta);
        //System.err.println(arc.getAbsolutePath()+"--\n-"+arc.getCanonicalPath());
        
        BufferedImage imagenSuper = ImageIO.read(new File("C:/Users/jorge/Downloads/Captura29-12-2021.png"));
        ImageIcon Sup = new ImageIcon(imagenSuper.getScaledInstance(filas * dimensiones, columnas * dimensiones, BufferedImage.SCALE_AREA_AVERAGING));
        Image img1 = Sup.getImage();
        ToolkitImage tk = (ToolkitImage) img1;
        BufferedImage imagenSuper2 = tk.getBufferedImage();
        //ruta="img/cuadros.png";
        //ImageIO.write(imagenSuper2, "png", new File("C:/Users/tonyp/OneDrive/Documentos/NetBeansProjects/trataimg/web/img/img3.png"));
        ruta="C:/Users/jorge/Downloads/Captura29-12-2021.png";
        int cortef = 0, cortec = 0,i=0;
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) 
            {
                BufferedImage temporary = imagenSuper2.getSubimage(cortec, cortef, dimensiones, dimensiones);
                String rutatempo="G:/Mi unidad/2021/Universidad/Vinculación/JIT_VINCULACION/img/partes/"+f + "-" + c + ".png";
                File file = new File(rutatempo);
                ImageIO.write(temporary, "png", file);
                String itemsM=String.format("img/partes/%s-%s.png", f,c);
                partes[i++]=itemsM;
                cortec += dimensiones;
            }
            cortec = 0;
            cortef += dimensiones;
        }
    }
    
  
    
    public String getRuta() {
        return ruta;
    }
    public String getPartes0() {
        return partes[0];
    }
    public String getPartes1() {
        return partes[1];
    }
    public String getPartes2() {
        return partes[2];
    }
    public String getPartes3() {
        return partes[3];
    }
    public String getPartes4() {
        return partes[4];
    }
    public String getPartes5() {
        return partes[5];
    }
    public String getPartes6() {
        return partes[6];
    }
    public String getPartes7() {
        return partes[7];
    }
    public String getPartes8() {
        return partes[8];
    }
    public String getPartes9() {
        return partes[9];
    }
    public String getPartes10() {
        return partes[10];
    }
    public String getPartes11() {
        return partes[11];
    }
    public String getPartes12() {
        return partes[12];
    }
    public String getPartes13() {
        return partes[13];
    }
    public String getPartes14() {
        return partes[14];
    }
    public String getPartes15() {
        return partes[15];
    }

}
