import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.Scanner;


import org.apache.commons.io.IOUtils;

public class NotVulnerableClass {

  private final InputStream input;

  public NotVulnerableClass() {
    input = System.in;
  }

  public NotVulnerableClass(InputStream input) {
    this.input = input;
  }
  
  /**
    * @author Giancarlo 
    *     Ao receber uma string contendo o nome de um arquivo, o metodo 
    *     le do ou escreve no arquivo se este existir e tiver um nome
    *     valido.
  */
  public void notVulnerableMethod(String filename) {
    if (!filename.matches("[A-Za-z0-9\\._]*")) {
      String msg = "Caractere inválido presente. Nome do arquivo só pode conter letras "
          + "maiúsculas e minúsculas, números, ponto e underline.";
      System.out.println(msg);
      throw new IllegalArgumentException(msg);
    }

    File file = new File(filename);
    if (!file.exists() || file.isDirectory()) {
      String msg = "Arquivo inexistente!";
      System.out.println(msg);
      throw new IllegalArgumentException(msg);
    }

    Scanner console = new Scanner(input);

    try {

      String opr = "";

      do {
        System.out.print("Digite a operacao desejada para realizar no arquivo <R para ler " 
            + "um arquivo, W para escrever em um arquivo, S para sair>? ");
        opr = console.nextLine();
        if (opr.equals("R")) {
          readFromFile(filename);
        } else if (opr.equals("W")) {
          System.out.println("Escreva algo: ");
          String novaLinha = console.nextLine();
          if (novaLinha == null) {
            novaLinha = "";
          }
          writeToFile(filename, novaLinha);
        } else if (!opr.equals("S")) {
          System.out.println("Operação inválida! Por favor, escreva R, W ou S.");
        }
      } while (!opr.equals("S"));

      System.out.println("Adeus!");
    } finally {
      console.close();
    }
  }

  private void readFromFile(String filename) {
    BufferedReader br = null;
    FileReader fr = null;

    try {

      fr = new FileReader(filename);
      br = new BufferedReader(fr);

      String currentLine;

      br = new BufferedReader(new FileReader(filename));
      
      while ((currentLine = br.readLine()) != null) {
        String linha = Normalizer.normalize(currentLine, Normalizer.Form.NFKC);
        System.out.println(linha);
      }

    } catch (IOException e) {

      String msg = "Ocorreu um problema na leitura do arquivo!";
      System.out.println(msg);
      throw new IllegalArgumentException(msg);
    } finally {
      IOUtils.closeQuietly(br);
      IOUtils.closeQuietly(fr);
    }
  }

  private void writeToFile(String filename, String newLine) {
    BufferedWriter buffWrite = null;
    FileWriter fileWriter = null;

    try {
      fileWriter = new FileWriter(filename, true);
      buffWrite = new BufferedWriter(fileWriter);

      String linha = Normalizer.normalize(newLine, Normalizer.Form.NFKC);
      buffWrite.append(linha + "\n");

    } catch (IOException e) {
      String msg = "Ocorreu um problema na escrita do arquivo!";
      System.out.println(msg);
      throw new IllegalArgumentException(msg);
    } finally {
      IOUtils.closeQuietly(buffWrite);
      IOUtils.closeQuietly(fileWriter);
    }
  }
}
