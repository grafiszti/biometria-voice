package pl.biometria.voice.gui.table;

import javax.swing.JTable;

public class DataTable extends JTable {
  private static final long serialVersionUID = 2081032014734262163L;

  private DataTable() {
    super(new TableModel());
    this.setAutoResizeMode(AUTO_RESIZE_OFF);
    this.setAutoCreateRowSorter(true);
    this.setAutoscrolls(true);
  }
}
