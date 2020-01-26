import java.io.*;

public class schreiben {

  public static void main (String[] args) throws IOException{
    String text = "3*(4+5";
    String dateiName = "testdatei.txt";
    FileOutputStream schreibeStrom = 
                     new FileOutputStream(dateiName);
    for (int i=0; i < text.length(); i++){
      schreibeStrom.write((byte)text.charAt(i));
    }
    schreibeStrom.close();
    System.out.println("Datei ist geschrieben!");
  }
}