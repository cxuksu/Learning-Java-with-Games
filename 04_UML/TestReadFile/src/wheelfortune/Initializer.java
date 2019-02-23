/*
 * Initializer.java - A class defines the initializer.
 */
package wheelfortune;

/**
 *
 * @author cxu
 */
public class Initializer {

    private String sentence;
    private ReadFile readFile;

    public Initializer() {
        readFile = new ReadFile();
    }

    public String initSentence() {
        readFile.selectFile();
        sentence = readFile.readContent();

        return sentence;
    }
}
