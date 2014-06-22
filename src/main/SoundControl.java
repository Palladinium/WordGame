package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

// Written by Edward Desmond-Jones

public class SoundControl {
	private static final double FADE_TIME = 2;
	
	private MediaPlayer menuMusic;
	private MediaPlayer levelMusic;
	private MediaPlayer winMusic;
	private MediaPlayer loseMusic;
	
	public enum GameMusicTypes {
		NO_MUSIC,
		MENU_MUSIC,
		LEVEL_MUSIC,
		WINNING_MUSIC,
		LOSING_MUSIC
	}
	
	private MediaPlayer currentSongRef, incomingSongRef;
	private GameMusicTypes currentSongType, incomingSongType;
	
	public SoundControl() {
		menuMusic = new MediaPlayer(new Media(SoundControl.class.getResource("/MenuMusic.wav").toString()));
		levelMusic = new MediaPlayer(new Media(SoundControl.class.getResource("/LevelMusic.wav").toString()));
		winMusic = new MediaPlayer(new Media(SoundControl.class.getResource("/WinMusic.wav").toString()));
		loseMusic = new MediaPlayer(new Media(SoundControl.class.getResource("/LoseMusic.wav").toString()));
		
		currentSongRef = menuMusic;
		currentSongRef.play();
		
		currentSongType = GameMusicTypes.MENU_MUSIC;
		incomingSongType = GameMusicTypes.NO_MUSIC;
	}
	
	public boolean setNextSong(GameMusicTypes nextSongType) {
		if (incomingSongType != GameMusicTypes.NO_MUSIC)
			return false;

		if (nextSongType != currentSongType)
		{
			incomingSongType = nextSongType;
			
			double repeatPoint = 0;
			
			switch (incomingSongType) {
				case NO_MUSIC:
				return true; // No need to change anything
					
				case MENU_MUSIC:
					incomingSongRef = menuMusic;
				break;
				
				case LEVEL_MUSIC:
					incomingSongRef = levelMusic;
					repeatPoint = 8700.0; // Around nine seconds from start
				break;
				
				case WINNING_MUSIC:
					incomingSongRef = winMusic;
					repeatPoint = 3700.0; // Around three seconds from start
				break;
				
				case LOSING_MUSIC:
					incomingSongRef = loseMusic;
				break;
			}
			
			/* Start the incoming song, but make sure it starts
			 * 'muted' so that it can be faded in.
			 */
			incomingSongRef.setStartTime(Duration.ZERO);
			
			incomingSongRef.play();
			incomingSongRef.setVolume(0);
			
			incomingSongRef.setStartTime(new Duration(repeatPoint));
			incomingSongRef.setCycleCount(MediaPlayer.INDEFINITE);
		}
		
		return true;
	}
	
	public void updateMusic(double elapsed) {
		if (incomingSongType == GameMusicTypes.NO_MUSIC)
			return;

		/* Fade music in and out */
		if (currentSongRef.getVolume() > 0.0) {
			currentSongRef.setVolume(Math.max(0.0, currentSongRef.getVolume() - (elapsed *1.0 / FADE_TIME)));
			incomingSongRef.setVolume(incomingSongRef.getVolume() + (elapsed * 1.0 / FADE_TIME));
		} else {
			incomingSongRef.setVolume(1.0);
			
			currentSongRef.stop();
			
			currentSongRef = incomingSongRef;
			currentSongType = incomingSongType;
			
			incomingSongRef = null;
			incomingSongType = GameMusicTypes.NO_MUSIC;
		}
	}
}
