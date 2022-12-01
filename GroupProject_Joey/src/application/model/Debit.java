package application.model;

import java.text.DecimalFormat;
/**
 * This class represents a User object which contains <update>
 * @author Justin Gilberti wye778
 * UTSA CS 3443 - Group Project
 * Fall 2022
 */
public class Debit {
	private double value;
	private String description;
	
	public Debit(String description, double value) {
		this.description = description;
		this.value = value;
	}

	@SuppressWarnings("static-access")
	public String toString() {
		String ret = "";
		DecimalFormat df = new DecimalFormat("#####.##");
		String format = df.format(getValue());
		ret = ret.format("  $%-10s%15s", format, getDescription());
		return ret;
	}
	
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
