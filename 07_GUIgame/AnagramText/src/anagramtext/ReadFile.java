/*
 * ReadFile.java - A class for opening a file and read a line from it.
 */
package anagramtext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class ReadFile {

    FileReader inputFile;
    String filePath;
    String fileName;

    public ReadFile() {
    }

    public void selectFile() {
        Object[] selectionValues = {"wordaj.txt", "wordjz.txt"};
        String initialSelection;
        initialSelection = "wordaj.txt";
        Object selection = JOptionPane.showInputDialog(null,
                "Select the file you would like to use",
                "Select a file", JOptionPane.QUESTION_MESSAGE, null,
                selectionValues, initialSelection);
        filePath = "../txt/" + selection;

        try {
            URL url = getClass().getResource(filePath);
            fileName = url.getPath();
            //Create object of FileReader
            inputFile = new FileReader(fileName);
        } catch (NullPointerException nullex) {
            System.out.println("You clicked Cancel.");
            System.out.println("Program is aborted by the user.");
            System.exit(0);
        } catch (FileNotFoundException fex) {
            System.out.println("The file is not found.");
            System.exit(0);
        }
    }

    public String readContent() {
        BufferedReader bufferReader = new BufferedReader(inputFile);
        String aWord = null;
        int numLines = 10;
        int lineNum;
        int count;

        lineNum = (int) (Math.random() * numLines);
        try {
            count = 0;
            while ((aWord = bufferReader.readLine()) != null) {
                if (count == lineNum) { // reach the random line
                    break;
                }
                count++;
            }
            bufferReader.close();
        } catch (IOException ioex) {
        }
        return aWord;
    }
}
