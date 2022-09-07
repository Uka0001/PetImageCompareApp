package org.uka0001;
/*The getCodeBase method used in this example
 returns the URL of the directory containing
  this applet when the applet is deployed on
  a web server. If the applet is deployed locally,
   getCodeBase returns null and the applet will not run.*/

import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.*;

public class LoadImageApplet extends Applet {

    public static void main(String[] args) {

    }

    private BufferedImage img;

    public void init() {
        try {
            URL url = new URL(getCodeBase(), "src/resources/actual.png");
            img = ImageIO.read(url);
        } catch (IOException e) {
        }
    }

    public void paint(Graphics g) {
        g.drawImage(img, 50, 50, null);
    }
}

