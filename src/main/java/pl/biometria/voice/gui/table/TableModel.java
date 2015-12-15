package pl.biometria.voice.gui.table;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
  private static final long serialVersionUID = -478095065946094712L;

  public TableModel() {
    super();
  }

  public TableModel(Object[] columnNames, Object[][] data) {
    super(data, columnNames);
  }
}
