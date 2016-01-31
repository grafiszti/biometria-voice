package pl.biometria.voice.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import pl.biometria.voice.db.NamedVoicePrintDao;
import pl.biometria.voice.gui.table.DataTable;
import pl.biometria.voice.recognition.utils.NamedVoicePrint;

import com.google.common.collect.Lists;

public class DataTab extends JPanel {
  private static final long serialVersionUID = 8905841630728935957L;

  DataTable dataTable;
  private JTextField textFieldIdsToDelete;
  JLabel labelInfo;
  JButton buttonDeleteSelected;
  JButton buttonReloadList;

  public DataTab() {
    setLayout(null);

    initialize();
  }

  private void initialize() {
    dataTable = DataTable.getInstance();

    reloadVoicePrints();

    initPanelDataTable();
    initButtonReloadList();
    initTextFieldIdsToDelete();
    initLabelInfo();
    initButtonDeleteSelected();
  }

  private void initPanelDataTable() {
    JPanel dataTablePane = new JPanel();
    dataTablePane.setLocation(0, 20);
    dataTablePane.setBounds(0, 60, 800, 580);
    dataTablePane.setLayout(new BorderLayout(0, 0));
    dataTablePane.add(new JScrollPane(dataTable));
    add(dataTablePane, BorderLayout.CENTER);
  }

  private void initButtonReloadList() {
    buttonReloadList = new JButton("Reload list");
    buttonReloadList.setBounds(0, 0, 117, 29);
    buttonReloadList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        labelInfo.setText("Data refreshed");
        reloadVoicePrints();
      }
    });
    add(buttonReloadList);
  }

  private void reloadVoicePrints() {
    List<NamedVoicePrint> allVoicePrints = NamedVoicePrintDao.getInstance().findAll();
    Object[] headers = getHeaders();
    Object[][] data = getData(allVoicePrints);
    dataTable.setNewData(data, headers);
  }

  private Object[] getHeaders() {
    return new Object[] {"ID", "Name", "Voice print"};
  }

  private Object[][] getData(List<NamedVoicePrint> voicePrints) {
    Object[][] result = new Object[voicePrints.size()][];
    for (int i = 0; i < voicePrints.size(); i++) {
      result[i] = new Object[] {i + 1, voicePrints.get(i).getName(), voicePrints.get(i).toString()};
    }
    return result;
  }

  private void initTextFieldIdsToDelete() {
    textFieldIdsToDelete = new JTextField();
    textFieldIdsToDelete.setBounds(119, 26, 325, 28);
    add(textFieldIdsToDelete);
    textFieldIdsToDelete.setColumns(10);
  }

  private void initLabelInfo() {
    labelInfo = new JLabel("Info: ");
    labelInfo.setBounds(119, 5, 61, 200);
    add(labelInfo);
  }

  private void initButtonDeleteSelected() {
    buttonDeleteSelected = new JButton("Delete");
    buttonDeleteSelected.setBounds(0, 27, 117, 29);
    buttonDeleteSelected.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        List<Integer> ids = Lists.newArrayList();
        String[] stringIds = textFieldIdsToDelete.getText().split(",");
        for (int i = 0; i < stringIds.length; i++) {
          ids.add(new Integer(stringIds[i]));
        }
        removeGivenIds(ids);
        reloadVoicePrints();
      }
    });
    add(buttonDeleteSelected);
  }

  private void removeGivenIds(List<Integer> ids) {
    List<NamedVoicePrint> voicePrints = NamedVoicePrintDao.getInstance().findAll();
    for (Integer id : ids) {
      voicePrints.remove(id-1);
    }
    NamedVoicePrintDao.getInstance().saveAll(voicePrints);
  }
}
