/*
 * AllClips.java
 *
 * Created on September 26, 2006, 1:17 PM
 */
package othello;

/**
 *
 * @author cxu
 */
public class AllClips {

    private String[] soundFiles;
    private static MakeClip[] clips;

    public AllClips() {
        soundFiles = new String[4];
        soundFiles[0] = "../sounds/flip.wav"; // flip pieces
        soundFiles[1] = "../sounds/valid.wav"; // possible playing cell
        soundFiles[2] = "../sounds/tada.wav"; // bye
        soundFiles[3] = "../sounds/invalid.wav"; // invalid

        clips = new MakeClip[soundFiles.length];
        for (int i = 0; i < soundFiles.length; i++) {
            MakeClip aClip = new MakeClip();
            aClip.setFilename(soundFiles[i]);
            aClip.loadClip();
            clips[i] = aClip;
        }
    }

    public static MakeClip getClips(int idx) {
        return clips[idx];
    }
}
