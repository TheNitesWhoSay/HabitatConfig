package main.client;

import com.google.gwt.storage.client.Storage;

/**
 * Handles all persistent configuration options
 */
public class ConfigOptions {

	private boolean loginRequired;
	private boolean loginNameCaseSensative;
	private boolean loginPassCaseSensative;
	private String loginName;
	private String loginPass;
	
	/**
	 * Sets all default configuration settings
	 */
	public ConfigOptions() {
		
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
		this.loginRequired = true;
		this.loginNameCaseSensative = false;
		this.loginPassCaseSensative = true;
		this.loginName = "mars";
		this.loginPass = "12345";
=======
>>>>>>> Stashed changes
		loginRequired = true;
		loginNameCaseSensative = false;
		loginPassCaseSensative = true;
		loginName = "mars";
		loginPass = "12345";
>>>>>>> FETCH_HEAD
	}
	
	/**
	 * Saves the configuration information to the client's computer
	 * Should be called every time a configuration variable is changed
	 * @return whether the save was successful
	 */
	public boolean saveToClient() {
		
		Storage storage = Storage.getLocalStorageIfSupported();
		if ( storage != null )
		{
			storage.setItem("loginRequired", loginRequired?"t":"f");
			storage.setItem("loginNameCaseSensative", loginNameCaseSensative?"t":"f");
			storage.setItem("loginPassCaseSensative", loginPassCaseSensative?"t":"f");
			storage.setItem("loginName", loginName);
			storage.setItem("loginPass", loginPass);
		}
		return false; // Unimplemented
	}
	
	/**
	 * Loads the programs configuration options from storage
	 * @return whether loading was 100% successful
	 */
	public boolean load() {
		
		Storage storage = Storage.getLocalStorageIfSupported();
		if ( storage != null )
		{
			String sLoginRequired = storage.getItem("loginRequired");
			String sLoginNameCaseSensative = storage.getItem("loginNameCaseSensative");
			String sLoginPassCaseSensative = storage.getItem("loginPassCaseSensative");
			String sLoginName = storage.getItem("loginName");
			String sLoginPass = storage.getItem("loginPass");
			
			if ( sLoginRequired != null )
			{
				if ( sLoginRequired.equals("t") )
					loginRequired = true;
				else if ( sLoginRequired.equals("f") )
					loginRequired = false;
			}
			
			if ( sLoginNameCaseSensative != null )
			{
				if ( sLoginNameCaseSensative.equals("t") )
					loginNameCaseSensative = true;
				else if ( sLoginNameCaseSensative.equals("f") )
					loginNameCaseSensative = false;
			}
			
			if ( sLoginPassCaseSensative != null )
			{
				if ( sLoginPassCaseSensative.equals("t") )
					loginPassCaseSensative = true;
				else if ( sLoginPassCaseSensative.equals("f") )
					loginPassCaseSensative = false;
			}
			
			if ( sLoginName != null )
				loginName = sLoginName;
			
			if ( sLoginPass != null )
				loginPass = sLoginPass;
			
			return true;
		}
		return false; // Unimplemented
	}
	
	/**
	 * Checks if the given username and password match the
	 * stored username and password
	 * @param loginName the given username
	 * @param loginPass the given password
	 * @return whether the username and password are correct
	 */
	public boolean validateLogin(String loginName, String loginPass) {
		
		String compareName = this.loginName,
			   comparePass = this.loginPass;
		
		if ( loginNameCaseSensative == false )
		{
			loginName = loginName.toUpperCase();
			compareName = this.loginName.toUpperCase();
		}
		if ( loginPassCaseSensative == false )
		{
			loginPass = loginPass.toUpperCase();
			comparePass = this.loginPass.toUpperCase();
		}
		
		return compareName.equals(loginName) &&
			   comparePass.equals(loginPass);
	}
	
	/**
	 * Returns whether or not the program should require a username/password
	 * @return whether the program requires a username/password
	 */
	public boolean loginRequired() {
		
		return loginRequired;
	}
	
	/**
	 * Checks if the login name is case sensative
	 * @return whether the login name is case sensative
	 */
	public boolean loginNameCaseSensative() {
		
		return loginNameCaseSensative;
	}
	
	/**
	 * Checks if the login password is case sensative
	 * @return whether the login password is case sensative
	 */
	public boolean loginPassCaseSensative() {
		
		return loginPassCaseSensative;
	}
	
	/**
	 * Sets whether a login is required
	 * @param loginName the current login name
	 * @param loginPass the current login pass
	 * @param loginRequired whether a login should be required
	 * @return if the the change to a login being required/not required was successful
	 */
	public boolean setLoginRequired(String loginName, String loginPass, boolean loginRequired) {
		
		if ( validateLogin(loginName, loginPass) )
		{
			this.loginRequired = loginRequired;
			saveToClient();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Sets whether the login name is case sensative
	 * @param loginName the current login name
	 * @param loginPass the current login pass
	 * @param loginNameCaseSensative whether the login name should be case sensative
	 * @return if the change to the login name being case sensative/not case sensative was successful
	 */
	public boolean setLoginNameCaseSensative(String loginName, String loginPass, boolean loginNameCaseSensative) {
		
		if ( this.validateLogin(loginName, loginPass) )
		{
			this.loginNameCaseSensative = loginNameCaseSensative;
			this.saveToClient();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Sets whether the login pass is case sensative
	 * @param loginName the current login name
	 * @param loginPass the current login pass
	 * @param loginPassCaseSensative whether the password should be case sensative
	 * @return if the change to the login name being case sensative/not case sensative was successful
	 */
	public boolean setLoginPassCaseSensative(final String loginName, final String loginPass, final boolean loginPassCaseSensative) {
		
		if ( this.validateLogin(loginName, loginPass) )
		{
			this.loginPassCaseSensative = loginPassCaseSensative;
			this.saveToClient();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Sets a new login username
	 * @param loginName the current login name
	 * @param loginPass the current login pass
	 * @param newUserName the new user name
	 * @return whether the username change was successful
	 */
	public boolean setUserName(String loginName, String loginPass, String newUserName) {
		
		if ( this.validateLogin(loginName, loginPass) && newUserName.length() > 0 )
		{
			this.loginName = newUserName;
			this.saveToClient();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Sets a new login password
	 * @param loginName the current login name
	 * @param loginPass the current login pass
	 * @param newPassword the new password
	 * @return whether the password change was successful
	 */
	public boolean setPassword(String loginName, String loginPass, String newPassword) {
		
		if ( validateLogin(loginName, loginPass) && newPassword.length() > 0 )
		{
			this.loginPass = newPassword;
			this.saveToClient();
			return true;
		}
		else
			return false;
	}
	
}
