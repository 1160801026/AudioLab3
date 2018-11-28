package realtime_ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class Capture {

  String filePath = "D:\\temp.wav";

  AudioFormat audioFormat;
  TargetDataLine targetDataLine;

  private void stopRecognize() {
    targetDataLine.stop();
    targetDataLine.close();
  }

  private AudioFormat getAudioFormat() {
    float sampleRate = 16000;
    // 8000,11025,16000,22050,44100
    int sampleSizeInBits = 16;
    // 8,16
    int channels = 1;
    // 1,2
    boolean signed = true;
    // true,false
    boolean bigEndian = false;
    // true,false
    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
  }// end getAudioFormat


  public void startRecognize() {
    try {
      // ���ָ������Ƶ��ʽ
      audioFormat = getAudioFormat();
      DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
      targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

      // Create a thread to capture the microphone
      // data into an audio file and start the
      // thread running. It will run until the
      // Stop button is clicked. This method
      // will return after starting the thread.
      run();
    } catch (Exception e) {
      e.printStackTrace();
    } // end catch
  }// end captureAudio method

  public void run() {
    File audioFile = new File(filePath);

    // ����¼���Ȩֵ
    int weight = 20;
    // �ж��Ƿ�ֹͣ�ļ���
    int downSum = 0;

    ByteArrayInputStream bais = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    AudioInputStream ais = null;
    try {
      targetDataLine.open(audioFormat);
      targetDataLine.start();
      byte[] fragment = new byte[1024];

      ais = new AudioInputStream(targetDataLine);
      while (true) {

        targetDataLine.read(fragment, 0, fragment.length);
        // ������ĩλ����weightʱ��ʼ�洢�ֽڣ����������룩��һ����ʼ������Ҫ�ж�ĩλ
        if (Math.abs(fragment[fragment.length - 1]) > weight || baos.size() > 0) {
          baos.write(fragment);
          System.out.println("�����С���");
          // �ж������Ƿ�ֹͣ
          if (Math.abs(fragment[fragment.length - 1]) <= weight) {
            downSum++;
          } else {   
            downSum = 0;
          }
          // ��������5˵���˶�ʱ��û����������(ֵҲ�ɸ���)
          if (downSum > 10) {
            System.out.println("ֹͣ¼��");
            break;
          }
        }
      }

      // ȡ��¼��������
      audioFormat = getAudioFormat();
      byte audioData[] = baos.toByteArray();
      bais = new ByteArrayInputStream(audioData);
      ais = new AudioInputStream(bais, audioFormat, audioData.length / audioFormat.getFrameSize());
      // �������ձ�����ļ���
      System.out.println("��ʼ���������ļ�");
      AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
      downSum = 0;
      stopRecognize();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // �ر���

      try {
        ais.close();
        bais.close();
        baos.reset();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }// end run
}// end inner class CaptureThread
