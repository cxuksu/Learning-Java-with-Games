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
        soundFiles = new String[3];
        soundFiles[0] = "../sounds/eatTreat.wav"; // eat treat
        soundFiles[1] = "../sounds/gameOver.wav"; // game over
        soundFiles[2] = "../sounds/BLIP2.wav"; // wormAuto eats treat

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
