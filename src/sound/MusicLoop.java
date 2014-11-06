package sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicLoop {
	private Clip loop;
	public MusicLoop(InputStream sound){
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(new BufferedInputStream(sound));
			loop = AudioSystem.getClip();
			loop.open(in);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public void startLoop(){
		loop.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void pauseLoop(){
		loop.stop();
	}
}
