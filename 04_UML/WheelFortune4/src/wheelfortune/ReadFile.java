/*
 * ReadFile.java - A class for opening a file and reading a line from it.
 */
package wheelfortune;

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

    private FileReader inputFile;
    String filePath;
    String fileName;

    public ReadFile() {
    }

    public void selectFile() {
        Object[] selectionValues = {"wheel1.txt", "wheel2.txt"};
        String initialSelection = "";
        Object selection = JOptionPane.showInputDialog(null,
                "Select the file you would like to use",
                "Select a file", JOptionPane.QUESTION_MESSAGE, null,
                selectionValues, initialSelection);
        filePath = "../txt/" + selection;
        URL url = getClass().getResource(filePath);
        fileName = url.getPath();

        try {
            //Create object of FileReader
            inputFile = new FileReader(fileName);
        } catch (FileNotFoundException fex) {
        } 
    }

    public String readContent() {
        BufferedReader bufferedReader = new BufferedReader(inputFile);
        String line = null;
        int numLines = 3;
        int lineNum;
        int count;

        lineNum = (int) (Math.random() * numLines);
        try {
            count = 0;    
            while ((line = bufferedReader.readLine()) != null) {
                if (count == lineNum) { // reach the random line
                    break;
                }
                count++;
            }
            bufferedReader.close();
        } catch (IOException ioex) {
        }

        return line;
    }
}
