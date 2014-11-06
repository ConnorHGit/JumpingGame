package sound;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound {
	public static void playSound(InputStream sound){
		AudioInputStream in = null;
		try {
			in = AudioSystem.getAudioInputStream(new BufferedInputStream(sound));

		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		Clip myFile = null;
		try {
			myFile = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			myFile.open(in);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myFile.start();
	}
}

