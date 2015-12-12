package pl.biometria.voice.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class AbstractTab extends JPanel {
  protected static final long serialVersionUID = 1L;

  public AbstractTab() {
    this.setAutoscrolls(true);
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
  }
}
