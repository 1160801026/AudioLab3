package test;

import java.io.IOException;
import org.junit.Test;
import realtime.Recognizer;

public class TestAccuracy {

  @Test
  public void test() throws IOException {
//    fail("Not yet implemented");
    int i,j,k=0;
    for(i=1;i<=10;i++) {
      for(j=1;j<=5;j++) {
        Recognizer rec=new Recognizer("C:\\Users\\samma\\Desktop\\Records\\"+i+"-"+j+".mfc");
        switch(i) {
        case 1:
          if(rec.Recognize().equals("��ʼ")){k++;}
          break;
        case 2:
          if(rec.Recognize().equals("����")){k++;}
          break;
        case 3:
          if(rec.Recognize().equals("��")){k++;}
          break;
        case 4:
          if(rec.Recognize().equals("�ر�")){k++;}
          break;
        case 5:
          if(rec.Recognize().equals("����")){k++;}
          break;
        case 6:
          if(rec.Recognize().equals("�½�")){k++;}
          break;
        case 7:
          if(rec.Recognize().equals("����")){k++;}
          break;
        case 8:
          if(rec.Recognize().equals("����")){k++;}
          break;
        case 9:
          if(rec.Recognize().equals("���")){k++;}
          break;
        case 10:
          if(rec.Recognize().equals("�ټ�")){k++;}
          break;
        }
      }
    }
    System.out.println("ʶ����ȷ�ʣ�"+k/50.0);
  }
}