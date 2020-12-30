public class MediaItem 
{
	private String title;
	private String format;
	private boolean onLoan;
	private String loanedTo;
	private String dateLoaned;
	
	//Default constructor sets null/empty values to all variables
	public MediaItem()
	{
		title = "";
		format = "";
		onLoan = false;
		loanedTo = "";
		dateLoaned = "";
	}
	
	//Constructor that takes a title and format to populate class variables
	public MediaItem(String name, String mediaType)
	{
		title = name;
		format = mediaType;
		onLoan = false;
		loanedTo = "";
		dateLoaned = "";
	}
	
	//Constructor that takes all fields as input
	public MediaItem(String name, String mediaType, boolean loanStatus, String loaner, String loanDate)
	{
		title = name;
		format = mediaType;
		onLoan = loanStatus;
		loanedTo = loaner;
		dateLoaned = loanDate;
	}
	
	//Returns the title
	public String getTitle()
	{
		return title;
	}
	
	//Returns the format
	public String getFormat()
	{
		return format;
	}
	
	//Returns onLoan
	public boolean getLoanStatus()
	{
		return onLoan;
	}
	
	//Returns who the media was loaned to
	public String getLoanedTo()
	{
		return loanedTo;
	}
	
	//Returns the date the media was loaned out
	public String getDateLoaned()
	{
		return dateLoaned;
	}
	
	//Sets the media title
	public void setTitle(String name)
	{
		title = name;
	}
	
	//Sets the media format
	public void setFormat(String mediaType)
	{
		format = mediaType;
	}
	
	//Sets the media on loan
	public void markOnLoan()
	{
		onLoan = true;
	}
	
	//Sets the media as returned (not on loan)
	public void returnItem()
	{
		onLoan = false;
	}
	
	//Sets who the media was loaned to
	public void setLoanedTo(String loaner)
	{
		loanedTo = loaner;
	}
	
	//Sets the date that the media was loaned out
	public void setLoanDate(String date)
	{
		dateLoaned = date;
	}
	
	//Sets the loan status to true while also taking arguments (String String) to fill in who the media was loaned to and when
	public void loanOutMedia(String name, String date)		
	{
		onLoan = true;
		loanedTo = name;
		dateLoaned = date;
	}
}
