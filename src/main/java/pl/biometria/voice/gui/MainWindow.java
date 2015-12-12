package pl.biometria.voice.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainWindow {
  private JFrame frame;
  private MainTabbedPane mainTabbedPane;

  public static void run() {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MainWindow window = new MainWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private MainWindow() {
    initialize();
  }

  private void initialize() {
    initMainFrame();
    initTabbedOptionContainer();
  }

  private void initMainFrame() {
    frame = new JFrame("Biometria Voice");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setMinimumSize(new Dimension(640, 480));
    frame.getContentPane().setLayout(new BorderLayout());
  }

  private void initTabbedOptionContainer() {
    mainTabbedPane = new MainTabbedPane();
    mainTabbedPane.setPreferredSize(frame.getPreferredSize());
    frame.getContentPane().add(mainTabbedPane, BorderLayout.CENTER);
    frame.pack();
  }
}
