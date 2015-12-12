package pl.biometria.voice.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import pl.biometria.voice.gui.components.ImagePanel;
import pl.biometria.voice.recorder.Recorder;
import pl.biometria.voice.recorder.Stopper;

import javax.swing.JTextField;
import javax.swing.JLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class ActionsTab extends JPanel {
  private static final long serialVersionUID = 1L;
  static final Integer RECORD_TIME = 1000; // 1 minute

  JButton recordButton;
  ImagePanel histogramImage;
  JButton buttonSave;
  JButton buttonVerify;
  JButton buttonPlay;
  JTextField newNameTextField;
  JLabel labelName;
  JLabel infoLabel;

  File currentRecordedFile;

  final Recorder recorder = new Recorder();
  Thread stopper = new Stopper(RECORD_TIME, recorder);

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
  }

  private void initRecordButton() {
    recordButton = new JButton("Record");
    recordButton.setBounds(600, 360, 117, 29);
    recordButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        recordSoundFile();
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

  private void initHistogramImage() {
    histogramImage = new ImagePanel(new File("res/kompot.jpg"));
    histogramImage.setBounds(10, 10, 500, 300);
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
    FileHandle fileHandle = new FileHandle(currentRecordedFile);
    Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("RecordAudio.wav"));
    wavSound.play();
  }

  private void initButtonSave() {
    buttonSave = new JButton("Save");
    buttonSave.setBounds(600, 440, 117, 29);
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
    infoLabel.setBounds(522, 24, 198, 29);
    add(infoLabel);
  }
}
