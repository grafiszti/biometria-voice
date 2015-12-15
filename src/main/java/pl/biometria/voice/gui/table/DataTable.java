package pl.biometria.voice.gui.table;

import javax.swing.JTable;

public class DataTable extends JTable {
  private static final long serialVersionUID = 2081032014734262163L;

  private static DataTable instance = null;

  public static DataTable getInstance() {
    if (instance == null) {
      instance = new DataTable();
    }
    return instance;
  }

  public void setNewData(Object[][] data, Object[] headers) {
    instance.setModel(new TableModel(headers, data));
  }

  private DataTable() {
    super(new TableModel());
    this.setAutoResizeMode(AUTO_RESIZE_OFF);
    this.setAutoCreateRowSorter(true);
    this.setAutoscrolls(true);
  }
}
