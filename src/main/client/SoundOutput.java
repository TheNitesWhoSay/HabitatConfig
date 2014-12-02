package main.client;

import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.media.client.Audio;

/**
 * Call methods here to output sound in response to events
 */
public class SoundOutput {
	final private Audio loginSuccess;
	final private Audio goodbyeMessage;
	final private Audio moduleLoggedMessage;
	final private Audio mapMessage;
	final private Audio firstHabitatMessage;
	
	/**
	 * Default constructor for SoundOutput.
	 */
	public SoundOutput() {
		loginSuccess = Audio.createIfSupported();
		loginSuccess.addSource("audio/Computer_Magic.mp3",
				AudioElement.TYPE_MP3);
		goodbyeMessage = null;
		moduleLoggedMessage = Audio.createIfSupported();
		moduleLoggedMessage.addSource("audio/module_details_saved.mp3", 
				AudioElement.TYPE_MP3);
		mapMessage = Audio.createIfSupported();
		mapMessage.addSource("audio/modules_and_landing_pos.mp3", 
				AudioElement.TYPE_MP3);
		firstHabitatMessage = Audio.createIfSupported();
		firstHabitatMessage.addSource("audio/first_habitat_config.mp3", 
				AudioElement.TYPE_MP3);
	}
	
	/**
	 * Plays the selected noise for a successful login of an Astronaut.
	 * @return The login success audio.
	 */
	public void playLoginSuccess () {
		if (loginSuccess != null)
			loginSuccess.play();
	}
	
	/**
	 * Plays the selected audio for a successful logoff of an Astronaut.
	 * @return The logoff success audio.  Currently, silence.
	 * @deprecated Do not use
	 */
	public void playLogoffSuccess () {
		if (goodbyeMessage != null)
			goodbyeMessage.play();
	}
	
	/**
	 * Plays the selected audio for a successful manual input of a module.
	 * @return The audio, "Manually entered module details have been saved."
	 */
	public void playModuleLogged() {
		if (moduleLoggedMessage != null)
			moduleLoggedMessage.play();
	}
	
	/**
	 * Plays the selected audio for the map when modules and landing positions exist.
	 * @return The audio, "The map shows modules and their landing positions."
	 */
	public void playMapMessage() {
		if (mapMessage != null)
			mapMessage.play();
	}
	
	/**
	 * Plays the selected audio when a minimum configuration exists.
	 * @return The audio, "The map shows the first suggested habitat configuration."
	 */
	public void playFirstHabitat() {
		if (firstHabitatMessage != null)
			firstHabitatMessage.play();
	}
}