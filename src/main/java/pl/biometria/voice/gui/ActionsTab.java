package pl.biometria.voice.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pl.biometria.voice.Constants;
import pl.biometria.voice.gui.components.ImagePanel;
import pl.biometria.voice.player.WavPlayer;
import pl.biometria.voice.recorder.Recorder;
import pl.biometria.voice.recorder.Stopper;

import com.bitsinharmony.recognito.Recognito;
import com.bitsinharmony.recognito.VoicePrint;

public class ActionsTab extends JPanel {
  private static final long serialVersionUID = 1L;

  JButton recordButton;
  ImagePanel histogramImage;
  JButton buttonSave;
  JButton buttonVerify;
  JButton buttonPlay;
  JTextField newNameTextField;
  JLabel labelName;
  JLabel infoLabel;
  JLabel lastRecordedFileInfoLabel;

  Recognito<String> recognito;

  File currentRecordedFile;

  final Recorder recorder = new Recorder();
  Thread stopper = new Stopper(Constants.RECORD_TIME, recorder);

  public ActionsTab() {
    setLayout(null);
    initializeComponents();
  }

  private void initializeComponents() {
    initRecordButton();
    initHistogramImage();
    initButtonPlay();
    initButtonSave();
    initButtonVerify();
    initNameTextField();
    initLabelName();
    initInfoLabel();
    initLastRecordedFileInfoLabel();

    recognito = new Recognito<String>(Constants.AUDIO_SAMPLE_RATE);
  }

  private void initRecordButton() {
    recordButton = new JButton("Record");
    recordButton.setBounds(600, 360, 117, 29);
    recordButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        recordSoundFile();
        updateInfoLastRecordedFile();
      }
    });
    add(recordButton);
  }

  private void recordSoundFile() {
    stopper.start();
    recorder.start();
    while (recorder.isRecording()) {
    }
    currentRecordedFile = recorder.getAudioFile();
  }

  private void updateInfoLastRecordedFile() {
    String lastRecordedFileInfo = buildLastRecordedFileInfo();
    lastRecordedFileInfoLabel.setText(lastRecordedFileInfo);
  }

  private String buildLastRecordedFileInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    sb.append("File info:").append("<br>");
    sb.append("Filename: ").append(currentRecordedFile.getName()).append("<br>");
    sb.append("Size: ").append(currentRecordedFile.length()).append("<br>");
    sb.append("Last modified: ").append(new Date(currentRecordedFile.lastModified()).toString()).append("<br>");
    sb.append("</html>");
    return sb.toString();
  }

  private void initHistogramImage() {
    histogramImage = new ImagePanel(new File("res/kompot.jpg"));
    histogramImage.setBounds(10, 10, 400, 250);
    add(histogramImage);
  }

  private void initButtonPlay() {
    buttonPlay = new JButton("Play");
    buttonPlay.setBounds(600, 400, 117, 29);
    buttonPlay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currentRecordedFile != null) {
          playRecordedSound();
        } else {
          infoLabel.setText("Brak nagranego pliku dzwieku");
        }
      }
    });
    add(buttonPlay);
  }

  private void playRecordedSound() {
    WavPlayer player = new WavPlayer(recorder.getAudioFile());
    player.play();
  }

  private void initButtonSave() {
    buttonSave = new JButton("Save");
    buttonSave.setBounds(600, 440, 117, 29);
    buttonSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          VoicePrint print = recognito.createVoicePrint("Elvis", new File(Constants.AUDIO_SAMPLE_NAME));
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    });
    add(buttonSave);
  }

  private void initButtonVerify() {
    buttonVerify = new JButton("Verify");
    buttonVerify.setBounds(600, 480, 117, 29);
    add(buttonVerify);
  }

  private void initNameTextField() {
    newNameTextField = new JTextField();
    newNameTextField.setBounds(75, 322, 634, 28);
    add(newNameTextField);
    newNameTextField.setColumns(10);
  }

  private void initLabelName() {
    labelName = new JLabel("Name:");
    labelName.setBounds(10, 328, 51, 16);
    add(labelName);
  }

  private void initInfoLabel() {
    infoLabel = new JLabel("Info: ");
    infoLabel.setBounds(450, 25, 200, 30);
    add(infoLabel);
  }

  private void initLastRecordedFileInfoLabel() {
    lastRecordedFileInfoLabel = new JLabel("File info: ");
    lastRecordedFileInfoLabel.setBounds(450, 75, 200, 300);
    add(lastRecordedFileInfoLabel);
  }
}
