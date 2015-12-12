package pl.biometria.voice.gui;

import javax.swing.JTabbedPane;

public class MainTabbedPane extends JTabbedPane {
  private static final long serialVersionUID = 1382591565017588576L;

  ActionsTab actionsTab;
  DataTab dataTab;

  public MainTabbedPane() {
    initializeTabs();
  }

  private void initializeTabs() {
    actionsTab = new ActionsTab();
    this.addTab("Actions", actionsTab);

    dataTab = new DataTab();
    this.addTab("Data", dataTab);
  }
}
