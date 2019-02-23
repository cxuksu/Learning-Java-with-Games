/*
 * TryString.java - The main class of the project TryString.
 */
package trystring;

/**
 *
 * @author cxu
 */
public class TryString {

    private String sentence;
    private String subStr;

    public TryString() {
        sentence = "Hello";
    }

    public void completeSentence() {
        sentence = sentence.concat(", World");
        System.out.println("Complete sentence = " + sentence);
    }

    public void strLength() {
        System.out.println(sentence.length());
    }

    public void getSubStr() {
        subStr = sentence.substring(7, 9); // (beginIndex, endIndex)
        System.out.println("Sub-string = " + subStr);
    }

    public void compareStr() {
        System.out.println(sentence.compareTo("hello, world"));
        System.out.println(sentence.compareToIgnoreCase("hello, world"));
    }

    public void containStr() {
        System.out.println(sentence.contains(subStr));
    }

    public void charIndex() {
        System.out.println(sentence.indexOf('l'));
    }

    public void convertToString() {
        String theValue = "" + 123;
        int aInt = Integer.parseInt(theValue);
        System.out.println("aInt = " + aInt);
        
    }

    public void convertValue() {
        String aStr = "123";
        System.out.println("Value is " + String.valueOf(aStr));
    }

    public void convertType() {
        String aStr = "" + 12.34;
        String bStr = String.valueOf(12.34);
        System.out.println("aStr " + aStr + " bStr = " + bStr);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TryString tryString = new TryString();
        tryString.completeSentence();
        tryString.strLength();
        tryString.getSubStr();
        tryString.compareStr(); // returns -1, 0, +1
        tryString.containStr();
        tryString.charIndex();
        tryString.convertToString();
        tryString.convertValue();
        tryString.convertType();
    }
}
