/*
 * SoundClip.java - A class that loads a clip from a file and play it
 * or stop playing.
 */
package othello;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author cxu
 */
public class SoundClip implements LineListener {
    private String filename;
    private Clip clip = null;
    private boolean isLooping = false;
    
    public SoundClip() {
    }
    
    public void loadClip() {
        try {
            // link an audio stream to the sound clip's file
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                    getClass().getResource(getFilename()));
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info); //get clip line resource
            clip.addLineListener(this); // listener for clip events
            clip.open(stream); // open the sound file as a clip
            stream.close(); // we're done with the input stream
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (LineUnavailableException ex) {
            System.out.println(ex.getMessage());
        } catch (UnsupportedAudioFileException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // It will be called when the clip's line detects open, 
    // close, start, or stop events.
    @Override
    public void update(LineEvent lineEvent)
    {
        // when clip is stopped or reaches its end
        if (lineEvent.getType() == LineEvent.Type.STOP) {
            clip.stop();
            if (isLooping) {
                clip.start();
            }
        }
    }
    
    public void play(boolean toLoop) {
        if (clip != null) {
            isLooping = toLoop;
            clip.start();
        }
    }
    
    public void stop()
    { if (clip != null) {
          isLooping = false;
          clip.stop();
          clip.setFramePosition(0); // start over
      }
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getFilename() {
        return filename;
    }
}

