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
	final private Audio minHabitatExists;
	final private Audio moduleAdd;
	final private Audio moduleDelete;
	
	/**
	 * Default constructor for SoundOutput.
	 */
	public SoundOutput() {
		loginSuccess = Audio.createIfSupported();
		loginSuccess.addSource("audio/Computer_Magic.mp3",
				AudioElement.TYPE_MP3);
		goodbyeMessage = Audio.createIfSupported();
		goodbyeMessage.addSource("audio/goodbye.mp3",
				AudioElement.TYPE_MP3);
		moduleLoggedMessage = Audio.createIfSupported();
		moduleLoggedMessage.addSource("audio/module_details_saved.mp3", 
				AudioElement.TYPE_MP3);
		mapMessage = Audio.createIfSupported();
		mapMessage.addSource("audio/modules_and_landing_pos.mp3", 
				AudioElement.TYPE_MP3);
		firstHabitatMessage = Audio.createIfSupported();
		firstHabitatMessage.addSource("audio/first_habitat_config.mp3", 
				AudioElement.TYPE_MP3);
		minHabitatExists = Audio.createIfSupported();
		minHabitatExists.addSource("audio/min_habitat_exists.mp3", 
				AudioElement.TYPE_MP3);
		moduleAdd = Audio.createIfSupported();
		moduleAdd.addSource("audio/add_success.mp3",
				AudioElement.TYPE_MP3);
		moduleDelete = Audio.createIfSupported();
		moduleDelete.addSource("audio/delete_success.mp3", 
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
	 * @return The audio, "Goodbye, friend"
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
	 * @return The audio, "A minimum habitat configuration exists."
	 */
	public void playMinHabitat() {
		if (minHabitatExists != null)
			minHabitatExists.play();
	}
	
	/**
	 * Audio for when a module is added or deleted
	 * @param choice 1 - module added, 2 - module deleted, 3 - module modified 
	 * @return if choice 1, "Module successfully added". If choice 2, "Module successfully deleted".
	 * 			Choice 3 not currently implemented.
	 */
	public void playModuleAudio(int choice) {
		
		switch (choice) {
			case 1: moduleAdd.play(); break;
			case 2: moduleDelete.play(); break;
			case 3:
			default: break;
		}
		
			
	}
	
}