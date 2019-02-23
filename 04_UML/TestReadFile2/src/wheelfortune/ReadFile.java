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
        String aFileName = JOptionPane.showInputDialog(
                "Please enter a file name for reading (e.g. wheel1.txt)");
        filePath = "../txt/" + aFileName;
        URL url = getClass().getResource(filePath);
        fileName = url.getPath();

        try {
            // Create object of FileReader
            inputFile = new FileReader(fileName);
        } catch (FileNotFoundException fex) {
        }
    }

    public String readContent() {
        BufferedReader bufferedReader = new BufferedReader(inputFile);
        String line = null;
        int numLines;
        int lineNum;
        int count;

        String lineNumStr = JOptionPane.showInputDialog(
                "How many lines in the file? (an integer)");
        numLines = Integer.parseInt(lineNumStr);
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
