/*
 * AllClips.java - A class that holds all clips and provides a static 
 * method for playing back a clip so that it can be invoked from anywhere.
 */
package worm;

/**
 *
 * @author cxu
 */
public class AllClips {

    private String[] soundFiles;
    private static MakeClip[] clips;

    public AllClips() {
        soundFiles = new String[4];
        soundFiles[0] = "../sounds/TOFF.wav"; // hits wall
        soundFiles[1] = "../sounds/CRISP.wav"; // hits bricks
        soundFiles[2] = "../sounds/WBLBEEP.wav"; // hits paddle
        soundFiles[3] = "../sounds/CRASHBUZ.wav"; // ball lost

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
