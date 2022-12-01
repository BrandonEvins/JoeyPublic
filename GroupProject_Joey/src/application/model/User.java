package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * This class represents a User object which contains two strings and a boolean
 * @author Justin Gilberti wye778
 * UTSA CS 3443 - Group Project
 * Fall 2022
 */
public class User {
	private String userName;
	private String password;
	private boolean isValid;
	private static HashMap<String, String> users;
	
	public User () {
		this.setUserName(userName);
		this.setPassword(password);
		this.setValid(isValid);
	}
	
	/**
	 * Method loads a .csv file of user names and passwords into a hash map of [key, value] pairs 
	 * @param fileName - String representation of a file path and name
	 */
	public static void loadUsers (String fileName) {
		/*
		 * Declare a scanner and users hash map
		 */
		Scanner scanner = null;
		users = null;
		try {
			/*
			 * Initialize new scanner and open file
			 * Initialize new hash map
			 */
			scanner = new Scanner(new File(fileName));
			users = new HashMap<>();
			while(scanner.hasNextLine()) {
				/*
				 * Get next line from the file
				 */
				String line = scanner.nextLine();
				if(line != null) {
					/*
					 * Split line at ',' and store the [key, value] pair in hash map
					 */
					String[] tokens = line.split(",");
					users.put(tokens[0], tokens[1]);
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(scanner != null)
				/*
				 * If scanner is not null, close scanner
				 */
				scanner.close();
		}
	}
	
	/**
	 * Method takes in a user object and validates the user name and password and
	 * sets a boolean flag to true if the user object is validated
	 * @param user - This is a User object representation of a user
	 */
	public static void validateUser(User user) {
		if(user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
			/*
			 * The userName or password variable is the empty string
			 * Boolean isValid = false
			 */
			if(user.getUserName().isEmpty())
				/*
				 * The userName variable is empty
				 * Set userName = username_empty
				 */
				user.setUserName("username_empty");
			if(user.getPassword().isEmpty())
				/*
				 * The password variable is empty
				 * Set password = password_empty
				 */
				user.setPassword("password_empty");
		} else if (user.getUserName().matches("^[a-z]{3}+[0-9]{3}$") && 
				user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")) {
			/*
			 * UserName and password pass regex requirements
			 */
			if(users.containsKey(user.getUserName())) {
				/*
				 * The userName exists in the HashMap
				 */
				if (users.get(user.getUserName()).equals(user.getPassword()))
					/*
					 * Password from the user matches the [key, value] pair stored in the hash map
					 * Boolean isValid = true
					 */
					user.setValid(true);
				else
					/*
					 * The password from the user does not match the [key, value] pair stored in the hash map
					 */
					user.setPassword("password_incorrect");
			}else {
				/*
				 * The userName does not exist
				 * Set values to prompt user to create a new account
				 * Boolean isValid = false
				 */
				user.setUserName("create_username");
				user.setPassword("password");
			}
		} else {
			/*
			 * The userName or password did not meet regex requirements
			 * Set values to show user name and password requirements
			 * Boolean isValid = false
			 */
			user.setUserName("username_format");
			user.setPassword("password_format");
		}
	}
	
	/**
	 * Method takes in a user object and validates the user name and password and
	 * sets a boolean flag to true if the user object is validated. If the user is 
	 * validated, they are added to the users hash map and then the hash map is written
	 * to the User_info.csv file
	 * @param user
	 */
	public static void addUser(User user) {
		if(user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
			/*
			 * The userName or password variable is the empty string
			 * Boolean isValid = false
			 */
			if(user.getUserName().isEmpty())
				/*
				 * The userName variable is empty
				 * Set userName = username_empty
				 */
				user.setUserName("username_empty");
			if(user.getPassword().isEmpty())
				/*
				 * The password variable is empty
				 * Set password = password_empty
				 */
				user.setPassword("password_empty");
		} else if (user.getUserName().matches("^[a-z]{3}+[0-9]{3}$") && 
				user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$")) {
			/*
			 * UserName and password pass regex requirements
			 */
			if(users.containsKey(user.getUserName())) {
				/*
				 * userName already exists in the hash map
				 * Set values to show user name exists
				 * Boolean isValid = false
				 */
				user.setUserName("username_exists");
				user.setPassword("password_exists");
			} else {
				/*
				 * userName does not exist in the hash map
				 * Store userName and password [key, value] pair into the hash map
				 * Boolean isValid = true
				 * Declare new file writer and set to null
				 */
				users.put(user.getUserName(), user.getPassword());
				user.setValid(true);
				Writer writer = null;
				try {
					/*
					 * Initialize file writer and open User_infor.csv
					 * Create end of line variable and set to system line separator
					 */
					writer = new FileWriter("data/User_info.csv");
					String eol = System.getProperty("line.separator");
					for(Map.Entry<String, String> entry : users.entrySet()) {
						/*
						 * Iterate across the hash map and write to file
						 */
						writer.append(entry.getKey());
						writer.append(",");
						writer.append(entry.getValue());
						writer.append(eol);
					}
					if(writer != null)
						/*
						 * If writer is not null, close writer
						 */
						writer.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			/*
			 * The userName or password did not meet regex requirements
			 * Set values to show user name and password requirements
			 * Boolean isValid = false
			 */
			user.setUserName("username_format");
			user.setPassword("password_format");
		}
	}
	
	public String toString() {
		String ret = getUserName() + " " + getPassword() + " " + isValid();
		return ret;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	/**
	 * Getter method for user name	
	 * @return userName - String representation of a user name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Setter method for user name
	 * @param userName - String representation of a user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Getter method for password
	 * @return password - String representation of a password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setter method for password
	 * @param password - String representation of a password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
