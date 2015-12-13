package pl.biometria.voice.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pl.biometria.voice.Constants;
import pl.biometria.voice.db.NamedVoicePrintDao;
import pl.biometria.voice.gui.components.ImagePanel;
import pl.biometria.voice.player.WavPlayer;
import pl.biometria.voice.recognitio.utils.NamedVoicePrint;
import pl.biometria.voice.recorder.Recorder;
import pl.biometria.voice.recorder.Stopper;
import pl.biometria.voice.utils.CollectionUtils;

import com.bitsinharmony.recognito.MatchResult;
import com.bitsinharmony.recognito.Recognito;
import com.google.common.collect.Lists;

public class ActionsTab extends JPanel {
  private static final long serialVersionUID = 1L;

  JButton recordButton;
  ImagePanel histogramImage;
  JButton buttonSave;
  JButton buttonVerify;
  JButton buttonPlay;
  JButton buttonSaveDb;
  JTextField newNameTextField;
  JLabel labelName;
  JLabel infoLabel;
  JLabel lastRecordedFileInfoLabel;

  Recognito<String> recognito;
  NamedVoicePrintDao namedVoicePrintDao;

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
    initButtonSaveDb();
    initNameTextField();
    initLabelName();
    initInfoLabel();
    initLastRecordedFileInfoLabel();

    namedVoicePrintDao = new NamedVoicePrintDao();

    initRecognitio();
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
          String newName = newNameTextField.getText();
          File sampleFile = new File(Constants.AUDIO_SAMPLE_NAME);
          if (newName.isEmpty() || sampleFile == null) {
            infoLabel.setText("Errors in saving");
          } else {
            recognito.createVoicePrint(newNameTextField.getText(), new File(Constants.AUDIO_SAMPLE_NAME));
            infoLabel.setText("Saved sucessfully!");
          }
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
    buttonVerify.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        List<MatchResult<String>> matches = Lists.newArrayList();
        try {
          matches = recognito.identify(currentRecordedFile);
        } catch (Exception exception) {
          exception.printStackTrace();
        }

        if (!matches.isEmpty()) {
          MatchResult<String> match = matches.get(0);
          String recognitionInfo = buildRecognitionInfo(match.getKey(), new Integer(match.getLikelihoodRatio()));
          infoLabel.setText(recognitionInfo);
        }
      }
    });
    add(buttonVerify);
  }

  private String buildRecognitionInfo(String name, Integer likelihood) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    sb.append("Voice sample recognized as: ").append("<br>");
    sb.append("Name : ").append(name).append("<br>");
    sb.append("Likelihood : ").append(likelihood).append(" %");
    sb.append("</html>");
    return sb.toString();
  }

  private void initButtonSaveDb() {
    buttonSaveDb = new JButton("Save db");
    buttonSaveDb.setBounds(400, 480, 117, 29);
    buttonSaveDb.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        List<NamedVoicePrint> allNamedVoicePrint = CollectionUtils.convertMapNameVoicePrintToNamedVoicePrintList(recognito.getStore());
        namedVoicePrintDao.saveAll(allNamedVoicePrint);
      }
    });
    add(buttonSaveDb);
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
    infoLabel.setBounds(450, 0, 200, 200);
    add(infoLabel);
  }

  private void initLastRecordedFileInfoLabel() {
    lastRecordedFileInfoLabel = new JLabel("File info: ");
    lastRecordedFileInfoLabel.setBounds(450, 75, 200, 300);
    add(lastRecordedFileInfoLabel);
  }

  private void initRecognitio() {
    List<NamedVoicePrint> allNamedVoicePrints = namedVoicePrintDao.findAll();
    recognito = new Recognito<String>(Constants.AUDIO_SAMPLE_RATE, CollectionUtils.convertNamedVoicePrintsToMap(allNamedVoicePrints));
  }
}
