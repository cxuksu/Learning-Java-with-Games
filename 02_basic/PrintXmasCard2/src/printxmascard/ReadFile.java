/*
 * ReadFile.java -- To read a string from a file.
 */
package printxmascard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author cxu
 */
public class ReadFile {

    private FileReader inputFile;

    public ReadFile() {
        initFile();
    }

    private void initFile() {
        File aFile;
        URL url = getClass().getResource("../txt/toXmas1.txt");
        aFile = new File(url.getPath());
        try {
            inputFile = new FileReader(aFile);
        } catch (FileNotFoundException ex) {
        }
    }

    public String readGreeting() {
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(inputFile);

        try {
            line = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException ioex) {
        }
        return line;
    }
}
