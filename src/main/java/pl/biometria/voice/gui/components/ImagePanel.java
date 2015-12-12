package pl.biometria.voice.gui.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
