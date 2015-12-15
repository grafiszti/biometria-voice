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
import pl.biometria.voice.recognition.MatchResult;
import pl.biometria.voice.recognition.Recognito;
import pl.biometria.voice.recognition.utils.NamedVoicePrint;
import pl.biometria.voice.recorder.Recorder;
import pl.biometria.voice.recorder.Stopper;
import pl.biometria.voice.utils.CollectionUtils;

import com.google.common.collect.Lists;

public class ActionsTab extends JPanel {
  private static final long serialVersionUID = 1L;

  JButton buttonRecord;
  ImagePanel histogramImage;
  JButton buttonSave;
  JButton buttonVerify;
  JButton buttonPlay;
  JButton buttonSaveDb;
  JTextField textFieldNewName;
  JLabel labelName;
  JLabel labelAutorizationWord;
  JLabel labelInfo;
  JLabel labelLastRecordedFileInfo;

  Recognito<String> recognito;
  NamedVoicePrintDao namedVoicePrintDao;

  File currentRecordedFile;

  Recorder recorder;
  Thread stopper;

  public ActionsTab() {
    setLayout(null);
    initializeComponents();
    namedVoicePrintDao = new NamedVoicePrintDao();
    initRecognitio();
  }

  private void initializeComponents() {
    initButtonRecord();
    initHistogramImage();
    initButtonPlay();
    initButtonSave();
    initButtonVerify();
    initButtonSaveDb();
    initTextFieldName();
    initLabelName();
    initLabelInfo();
    initLabelLastRecordedFileInfo();
    initLabelAutorizationWord();
  }

  private void initButtonRecord() {
    buttonRecord = new JButton("Record");
    buttonRecord.setBounds(600, 360, 117, 29);
    buttonRecord.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (recorder == null || !recorder.isRecording()) {
          recordSoundFile();
          updateInfoLastRecordedFile();
        }
      }
    });
    add(buttonRecord);
  }

  private void recordSoundFile() {
    recorder = new Recorder();
    stopper = new Stopper(Constants.RECORD_TIME, recorder);
    stopper.start();
    recorder.start();
    currentRecordedFile = recorder.getAudioFile();
  }

  private void updateInfoLastRecordedFile() {
    String lastRecordedFileInfo = buildLastRecordedFileInfo();
    labelLastRecordedFileInfo.setText(lastRecordedFileInfo);
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
          labelInfo.setText("Brak nagranego pliku dzwieku");
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
          String newName = textFieldNewName.getText();
          File sampleFile = new File(Constants.AUDIO_SAMPLE_NAME);
          if (newName.isEmpty() || sampleFile == null) {
            labelInfo.setText("Errors in saving");
          } else {
            recognito.createVoicePrint(textFieldNewName.getText(), new File(Constants.AUDIO_SAMPLE_NAME));
            labelInfo.setText("Saved sucessfully!");
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
          String recognitionInfo = buildRecognitionInfo(match.getKey(), match.getLikelihoodRatio());
          labelInfo.setText(recognitionInfo);
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

  private void initTextFieldName() {
    textFieldNewName = new JTextField();
    textFieldNewName.setBounds(75, 322, 634, 28);
    add(textFieldNewName);
    textFieldNewName.setColumns(10);
  }

  private void initLabelName() {
    labelName = new JLabel("Name:");
    labelName.setBounds(10, 328, 51, 16);
    add(labelName);
  }

  private void initLabelInfo() {
    labelInfo = new JLabel("");
    labelInfo.setBounds(450, 0, 200, 200);
    add(labelInfo);
  }

  private void initLabelAutorizationWord() {
    labelAutorizationWord = new JLabel("Autorization word: AUTORYZACJA");
    labelAutorizationWord.setBounds(10, 300, 300, 16);
    add(labelAutorizationWord);
  }

  private void initLabelLastRecordedFileInfo() {
    labelLastRecordedFileInfo = new JLabel("");
    labelLastRecordedFileInfo.setBounds(450, 75, 200, 300);
    add(labelLastRecordedFileInfo);
  }

  private void initRecognitio() {
    List<NamedVoicePrint> allNamedVoicePrints = namedVoicePrintDao.findAll();
    recognito = new Recognito<String>(Constants.AUDIO_SAMPLE_RATE, CollectionUtils.convertNamedVoicePrintsToMap(allNamedVoicePrints));
  }
}
