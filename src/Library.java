import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Library
{
	private ArrayList<MediaItem> mediaList = new ArrayList<>();

	//Default constructor
	public Library() 
	{
	}
	
	//Constructor that takes an array list and populates the media list with it
	public Library(ArrayList<MediaItem> mediaList) 
	{
		this.mediaList = mediaList;
	}
	
	//Adds a media item to the list of items
	public void addToList(MediaItem item)
	{
		this.mediaList.add(item);
	}
	
	//Searches for a media Item based on its title and returns the index of where that item is in the list
	public int searchForMediaItem(String mediaTitle)
	{
		boolean found = false;
		int index = 0;
		for(int i = 0; i < mediaList.size(); i++)
		{
			if(mediaList.get(i).getTitle().equals(mediaTitle))
			{
				found = true;
				index = i;
			}
			
		}
		
		if(found == true)
		{
			return index;
		}
		else
		{
			return -1;
		}
		
	}
	
	//Removes an item in the list at a certain index
	public void removeItem(int index)
	{
		mediaList.remove(index);
	}
	
	//Returns the media list
	public ArrayList<MediaItem> getMediaList() 
	{
		return mediaList;
	}

	//Sets the media list
	public void setMediaList(ArrayList<MediaItem> mediaList) 
	{
		this.mediaList = mediaList;
	}
	
	//Saves the media items to a file for use in the next load
	public void save()
	{
		File saveFile = new File("library.txt");
		PrintWriter output = null;
		
		try 
		{
			output = new PrintWriter(saveFile);
			for(int i = 0; i < mediaList.size(); i++)
			{
				output.println(mediaList.get(i).getTitle());
				output.println(mediaList.get(i).getFormat());
				output.println(mediaList.get(i).getLoanStatus());
				output.println(mediaList.get(i).getLoanedTo());
				output.println(mediaList.get(i).getDateLoaned());
			}
		}
		
		catch(FileNotFoundException ex)
		{
			JOptionPane.showMessageDialog(null, "An error occured while saving");
		}
		
		finally
		{
			output.close();
		}
	}
	
	//Loading operation that will populate the media list based off the save file
	public void load()
	{
		File saveFile = null;
		JFileChooser saveChooser = new JFileChooser();
		int returnVal = saveChooser.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			saveFile = saveChooser.getSelectedFile();
		}
		
		String title = "";
		String format = "";
		String onLoanString = "";
		boolean onLoan = false;
		String loanedTo = "";
		String dateLoaned = "";
		
		Scanner input = null;
		try
		{
			input = new Scanner(saveFile);
			
			while(input.hasNext())
			{
				title = input.nextLine();
				format = input.nextLine();
				onLoanString = input.nextLine();
				if(onLoanString.equals("true"))
				{
					onLoan = true;
				}
				loanedTo = input.nextLine();
				dateLoaned = input.nextLine();
				MediaItem temp = new MediaItem(title, format, onLoan, loanedTo, dateLoaned);
				addToList(temp);
			}
			
		} 
		
		catch(FileNotFoundException ex)
		{
			JOptionPane.showMessageDialog(null, "An error occured while loading");
		}
		
		finally
		{
			input.close();
		}
		
		
	}

}
