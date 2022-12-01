package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import application.controller.InformationController;
import application.controller.MainController;

/**
 * This class represents a User object which contains <update>
 * @author Justin Gilberti wye778
 * @author Sai Ananthula emw832
 * UTSA CS 3443 - Group Project
 * Fall 2022
 */
public class Financial_Data 
{
	
	private String dataUserName;
	public static ArrayList<Credit> credits;
	public static ArrayList<Debit> debits;
	
	public Financial_Data(String dataUserName) 
	{
		this.dataUserName = dataUserName;
		Financial_Data.credits = new ArrayList<>();
		Financial_Data.debits = new ArrayList<>();
	}

	/**
	 * loads in data from debit and credit files and loads into the data object
	 */
	@SuppressWarnings({ "null", "static-access" })
	public void loadFinancialData() 
	{
		Scanner scanner = null;
		Credit credit = null;
		Debit debit = null;		
		try 
		{
			scanner = new Scanner(new File("data/financialData/"+MainController.user.getUserName()+"Credits.csv"));
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				if(line != null)
				{
					String[] tokens = line.split(",");
					credit = new Credit(tokens[0], Double.valueOf(tokens[1]));
					InformationController.financialData.credits.add(credit);
				}
			}
			if(scanner != null)
				scanner.close();
			scanner = new Scanner(new File("data/financialData/"+MainController.user.getUserName()+"Debits.csv"));
			while(scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				if(line != null) 
				{
					String[] tokens = line.split(",");
					debit = new Debit(tokens[0], Double.valueOf(tokens[1]));
					InformationController.financialData.debits.add(debit);
				}
			}
			if(scanner != null)
				scanner.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void saveFinancialData()
	{
		Writer writer = null;
		try 
		{
			writer = new FileWriter("data/financialData/"+MainController.user.getUserName()+"Credits.csv");
			String eol = System.getProperty("line.separator");
			for(int i = 0; i < Financial_Data.credits.size(); ++i)
			{
				writer.write(credits.get(i).getDescription());
				writer.append(",");
				writer.write(String.valueOf(credits.get(i).getValue()));
				writer.write(eol);
			}
			if(writer != null)
				writer.close();
			writer = new FileWriter("data/financialData/"+MainController.user.getUserName()+"Debits.csv");
			for(int i = 0; i < Financial_Data.debits.size(); ++i)
			{
				writer.write(debits.get(i).getDescription());
				writer.append(",");
				writer.write(String.valueOf(debits.get(i).getValue()));
				writer.write(eol);
			}
			if(writer != null)
				writer.close();
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String toString() 
	{
		String ret = "";
		for(int i = 0; i < Financial_Data.credits.size(); ++i)
		{
			ret += Financial_Data.credits.get(i).toString();
			if(i < Financial_Data.credits.size() - 1)
					ret += ",";
		}
		ret += "\n";
		for(int i = 0; i < Financial_Data.debits.size(); ++i)
		{
			ret += Financial_Data.debits.get(i).toString();
			if(i < Financial_Data.debits.size() - 1)
				ret += ",";
		}
		return ret;
	}
	
	/**
	 * @return the dataUserName
	 */
	public String getDataUserName() 
	{
		return dataUserName;
	}

	/**
	 * @param dataUserName the dataUserName to set
	 */
	public void setDataUserName(String dataUserName) 
	{
		this.dataUserName = dataUserName;
	}

	/**
	 * @return the credits
	 */
	public ArrayList<Credit> getCredits() 
	{
		return credits;
	}

	/**
	 * @param credits the credits to set
	 */
	public void setCredits(ArrayList<Credit> credits)
	{
		Financial_Data.credits = credits;
	}

	/**
	 * @return the debits
	 */
	public ArrayList<Debit> getDebits() 
	{
		return debits;
	}

	/**
	 * @param debits the debits to set
	 */
	public void setDebits(ArrayList<Debit> debits) 
	{
		Financial_Data.debits = debits;
	}
}
