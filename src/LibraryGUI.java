/*TODO
Set up file saving and loading operations
*/

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryGUI extends Application
{
	//Defining classes
	Library mediaItems = new Library();

	//Creating a dynamic list view 
	ObservableList<String> mediaTitles = FXCollections.observableArrayList();
	ListView<String> listView = new ListView<String>(mediaTitles);

	@Override
	public void start(Stage primaryStage)
	{	
		//Asking the user if this is their first run so that saving and loading can be performed
		int reply = JOptionPane.showConfirmDialog(null, "Is this your first time running this program?", "Save Operation", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) 
		{
		  mediaItems.save();
		} 
		else 
		{
		  mediaItems.load();
		  updateList();
		}
		//Creating an organizational border pane
		BorderPane orgPane = new BorderPane();
		orgPane.setPadding(new Insets(10, 10, 10, 10));
		
		//Creating a gridpane for buttons
		HBox mainPane = new HBox();
		mainPane.setPadding(new Insets(10, 10, 10, 10));
		
		
		//Creating buttons
		Button btnAddItem = new Button("Add Item");
		Button btnDeleteItem = new Button("Delete Item");
		Button btnCheckOut = new Button("Check Out");
		Button btnCheckIn = new Button("Check In");
		
		//Adding buttons to the pane
		mainPane.getChildren().addAll(btnAddItem, btnDeleteItem, btnCheckOut, btnCheckIn);
		orgPane.setBottom(mainPane);
		mainPane.setAlignment(Pos.CENTER);
		
		//Creating a scroll pane for the list view
		orgPane.setCenter(new ScrollPane(listView));
		
		
		//Creating handlers
		addItemHandler handler1 = new addItemHandler();
		deleteItemHandler handler2 = new deleteItemHandler();
		checkOutItemHandler handler3 = new checkOutItemHandler();
		checkInItemHandler handler4 = new checkInItemHandler();
		
		//Setting handlers
		btnAddItem.setOnAction(handler1);
		btnDeleteItem.setOnAction(handler2);
		btnCheckOut.setOnAction(handler3);
		btnCheckIn.setOnAction(handler4);
		
		//Creating a scene and assigning it to the main stage
        Scene scene = new Scene(orgPane, 1000, 500);
        listView.setPrefWidth(scene.getWidth());
        primaryStage.setTitle("Library");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	//The add item handler that adds a media item to the mediaItems list
	class addItemHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			String title = "";
			String format = "";
			
			title = JOptionPane.showInputDialog("Please Enter the title of the media you wish to add");
			format = JOptionPane.showInputDialog("Please Enter the format of this media");
			
			MediaItem newItem = new MediaItem(title, format);
			mediaItems.addToList(newItem);
			updateList();
			mediaItems.save();
		}
	}
	
	//The delete item handler that will search the mediaItems list to find a selection and delete it
	class deleteItemHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			if(listView.getSelectionModel().isEmpty())
			{
				JOptionPane.showInternalMessageDialog(null, "Please select an item from the list");
			}
			else
			{
				int index = listView.getSelectionModel().getSelectedIndex();
				mediaItems.removeItem(index);
				updateList();
				mediaItems.save();
				
			}
			
			
		}
	}
	
	//The check out item handler will mark an item as not checked in and set the loan status fields to blank
	class checkOutItemHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{	
			if(listView.getSelectionModel().isEmpty())
			{
				JOptionPane.showInternalMessageDialog(null, "Please select an item from the list");
			}
			else
			{
				int index = listView.getSelectionModel().getSelectedIndex();
				if(mediaItems.getMediaList().get(index).getLoanStatus())
				{
					JOptionPane.showMessageDialog(null, "Sorry this item is already loaned out");
				}
				else
				{
					String loanedTo = "";
					String dateLoaned = "";
					loanedTo = JOptionPane.showInputDialog("Please Enter the name of who is checking this out");
					dateLoaned = JOptionPane.showInputDialog("Please Enter the current date");
					mediaItems.getMediaList().get(index).loanOutMedia(loanedTo, dateLoaned);
					updateList();
					mediaItems.save();
				}
			}
		}
	}
	
	//The check in item handler will mark an item as checked in and populate the loan status fields
	class checkInItemHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			if(listView.getSelectionModel().isEmpty())
			{
				JOptionPane.showInternalMessageDialog(null, "Please select an item from the list");
			}
			else
			{
				int index = listView.getSelectionModel().getSelectedIndex();
				if(!mediaItems.getMediaList().get(index).getLoanStatus())
				{
					JOptionPane.showMessageDialog(null, "Sorry this item is not loaned out");
				}
				else
				{
					mediaItems.getMediaList().get(index).returnItem();
					mediaItems.getMediaList().get(index).setLoanedTo("");
					mediaItems.getMediaList().get(index).setLoanDate("");
					updateList();
					mediaItems.save();
				}
		
			}
					
		}
	}
	
	//The update function is used to update the mediaTitles list for the listView
	public void updateList()
	{
		mediaTitles.clear();
		for(int i = 0; i < mediaItems.getMediaList().size(); i++)
		{
			mediaTitles.add(mediaItems.getMediaList().get(i).getTitle() + " | " + mediaItems.getMediaList().get(i).getFormat() + " | On Loan: " + mediaItems.getMediaList().get(i).getLoanStatus() + " | Loaned To: " + mediaItems.getMediaList().get(i).getLoanedTo() + " | Loaned On: " + mediaItems.getMediaList().get(i).getDateLoaned());
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
}
