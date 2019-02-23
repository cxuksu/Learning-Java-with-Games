/*
 * ReadFile.java - A class for opening a file and read a line from it.
 */
package anagramgui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author cxu
 */
public class ReadFile {

    private URL url;
    private FileReader inputFile;
    private BufferedReader bufferedReader;
    private String filePath;
    private String fileName;
    private int numLines;

    public ReadFile() {
    }

    public void selectFile(String selection) {
        filePath = "../txt/" + selection;
        
        try {
            url = getClass().getResource(filePath);
            fileName = url.getPath();
            //Create object of FileReader
            inputFile = new FileReader(fileName);
            numLines = countNumLines();
        } catch (NullPointerException nullex) {
            System.out.println("Program is aborted by the user.");
            System.exit(0);
        } catch (FileNotFoundException fex) {
            System.out.println("The file is not found.");
            System.exit(0);
        }
    }

    public int countNumLines() {
        bufferedReader = new BufferedReader(inputFile);
        int num = 0;
        try {
            while ((bufferedReader.readLine()) != null) {
                num++;
            }
            inputFile.close(); // close file for rewinding its pointer
            bufferedReader.close();
        } catch (IOException ioex) {
        }
        return num;
    }
    
    public String readContent() {
        String aWord = null;
        try {
            //Create object of FileReader since inputFile was closed
            inputFile = new FileReader(fileName);
            bufferedReader = new BufferedReader(inputFile);
        } catch (FileNotFoundException fex) {
        }
        int lineNum;
        int count;

        lineNum = (int) (Math.random() * numLines);
        try {
            count = 0;
            while ((aWord = bufferedReader.readLine()) != null) {
                if (count == lineNum) { // reach the random line
                    break;
                }
                count++;
            }
            inputFile.close();
            bufferedReader.close();
        } catch (IOException ioex) {
        }
        return aWord;
    }
}
