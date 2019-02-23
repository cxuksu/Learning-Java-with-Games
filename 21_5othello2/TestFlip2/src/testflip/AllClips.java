/*
 * AllClips.java - A class that holds all clips and provides a static 
 * method for playing back a clip so that it can be invoked from anywhere.
 */
package testflip;

/**
 *
 * @author cxu
 */
public class AllClips {
    private String[] soundFiles;
    private static SoundClip[] clips;
    
    /** Creates a new instance of AllClips */
    public AllClips() {
        soundFiles = new String[4];
        soundFiles[0] = "../sounds/TOFF.wav"; // hits walls
        soundFiles[1] = "../sounds/CRASHBUZ.wav"; // miss paddle
        soundFiles[2] = "../sounds/WBLBEEP.wav"; // hits paddle
        soundFiles[3] = "../sounds/CRISP.wav"; // hits bricks
        
        clips = new SoundClip[soundFiles.length];
        for (int i=0; i<soundFiles.length; i++) {
            SoundClip aClip = new SoundClip();
            aClip.setFilename(soundFiles[i]);
            aClip.loadClip();
            clips[i] = aClip;
        }
    }
    
    public static void playClip(int i, boolean toLoop) {
        ((SoundClip)clips[i]).play(toLoop);
    }
}
