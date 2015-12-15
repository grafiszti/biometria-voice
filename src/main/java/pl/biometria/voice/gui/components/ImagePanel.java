package pl.biometria.voice.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private static final long serialVersionUID = 1348928428086680431L;
    private BufferedImage image;

    public ImagePanel(File imageFile) {
        addImage(imageFile);
    }

    public void addImage(File imageFile) {
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
