package finalexam_client;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Scanner;
import java.util.ArrayList;

import java.io.*; 
import java.net.*; 
import javafx.application.Application; 
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Label; 
import javafx.scene.control.ScrollPane; 
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane; 

import finalexam_client.AuctionedItem;
public class ClientUI extends Application {
	public static ArrayList<AuctionedItem> items = new ArrayList<AuctionedItem>();
	private BufferedReader reader;
	private PrintWriter writer;
	Label itemName;
	TextField currentBid, status;
	
	public static void main(String[]args) {
		addItems();
		launch();
	}
	public void start(Stage primaryStage) {
		  Label label = new Label();
	      label.setText("Enter your username");
	      label.setFont(Font.font("Bodoni", FontWeight.BOLD, 20));
	      Label label2 = new Label();
	      label2.setText("below to enter the Auction!");
	      label2.setFont(Font.font("Bodoni", FontWeight.BOLD, 20));
	      Label label3 = new Label();
	      label3.setText("");
	      TextField username = new TextField();       
	      username.setOpacity(0.5);
	      Button start = new Button("Start the Auction"); 
	      GridPane gp = new GridPane();    
	      label3.setMinSize(100, 40);
	      gp.setHgap(10);
	      gp.setVgap(10);
	      gp.add(label, 0, 0);
	      gp.add(label2, 1, 0);
	      gp.add(label3, 2, 0);
	      gp.add(username, 1, 3);
	      gp.add(start, 1, 4);
	      
	      
	      gp.setStyle("-fx-background-color: BISQUE;");
	      Scene loginScene = new Scene(gp);  
	      primaryStage.setTitle("Auction Log-in"); 
	      primaryStage.setScene(loginScene); 
	      primaryStage.show(); 
		
		GridPane gridPane = new GridPane();
		String backgroundPath = new File("src/finalexam_client/background.mp3").getAbsolutePath();
		String cashierPath = new File("src/finalexam_client/cashier.mp3").getAbsolutePath();
		String bidPath = new File("src/finalexam_client/ring.mp3").getAbsolutePath();
		Media buyNowSound = new Media(new File(cashierPath).toURI().toString());
		Media bidSound = new Media(new File(bidPath).toURI().toString());
		Media backgroundSound = new Media(new File(backgroundPath).toURI().toString());
		MediaPlayer mainPlayer = new MediaPlayer(backgroundSound);
		mainPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mainPlayer.seek(Duration.ZERO);
			}
		});
		
		Label loginName = new Label();
		loginName.setText("Logged in as : "+username.getText());
//		loginName.setFont(f);
		
		itemName = new Label();
		itemName.setText(items.get(0).getItemName());
//		itemName.setFont(f);
		
		Label itemDescription = new Label();
		itemDescription.setText("Description: "+items.get(0).getItemDescription());
		
		Label startingPrice = new Label();
		startingPrice.setText("Starting Price: "+items.get(0).getStartingPrice());
		
		Label buyNowPrice = new Label();
		buyNowPrice.setText("Highest Possible Bid/Buy Now Price: "+items.get(0).getBuyNowPrice());
		
		currentBid = new TextField();
		currentBid.setText("Current Bid: "+items.get(0).getBid());
		
		status = new TextField();
		status.setText("Status: "+items.get(0).boughtStatus());
		
		ComboBox itemList = new ComboBox();
		for(AuctionedItem item: items) {
			itemList.getItems().add(item.getItemName());
		}
		itemList.setValue(items.get(0).getItemName());
	    TextField enterBid = new TextField();
	    enterBid.setOpacity(0.5);
		
		Button bid = new Button("Bid");
	    bid.setOnAction(new EventHandler<ActionEvent>() {
	          @Override
	          public void handle(ActionEvent event) {
	        	  MediaPlayer player = new MediaPlayer(bidSound);
	        	  player.play();
	        	  try {
		        	  for(int i=0;i<items.size();i++) {
		        		  if(items.get(i).getItemName().equals(itemList.getValue())) {
		        			  if(!(items.get(i).boughtOrNot())&&items.get(i).getBid()<Double.parseDouble(enterBid.getText())&&items.get(i).getStartingPrice()<Double.parseDouble(enterBid.getText())) {
		        				  writer.println(enterBid.getText()+","+username.getText()+","+itemList.getValue());
		        	        	  writer.flush();
		        			  }
		        			  break;
		        		  }
		        	  }
	        	  }
	        	  catch(NumberFormatException e) {
	        		  enterBid.setText("invalid bid");
	        	  }
	          }
	    });
	    
	    Button buyNow = new Button("Buy Now");
	    buyNow.setOnAction(new EventHandler<ActionEvent>() {
	          @Override
	          public void handle(ActionEvent event) {
	        	  MediaPlayer player = new MediaPlayer(buyNowSound);
	        	  player.play();
	        	  for(int i=0;i<items.size();i++) {
	        		  if(items.get(i).getItemName().equals(itemList.getValue())) {
	        			  if(!(items.get(i).boughtOrNot())) {
	        				  writer.println(items.get(i).getBuyNowPrice()+","+username.getText()+","+itemList.getValue());
	        	        	  writer.flush();
	        			  }
	        		  }
	        	  }
	          }
	    });
		  
	    Button logOut = new Button("Log Out");
	    
	    Image itemImage = items.get(0).getImage();
	    ImageView imageView = new ImageView(itemImage);
		imageView.setFitHeight(80);
		imageView.setFitWidth(80);
		
		
		itemList.setOnAction(new EventHandler<ActionEvent>() {
	          @Override
	          public void handle(ActionEvent event) {
	            	for(int i=0;i<items.size();i++) {
	            		if(items.get(i).getItemName().equals(itemList.getValue())) {
	            			itemName.setText(items.get(i).getItemName());
	            			itemDescription.setText("Description: "+items.get(i).getItemDescription());
	            			startingPrice.setText("Starting Price: "+items.get(i).getStartingPrice());
	            			buyNowPrice.setText("Highest Possible Bid/Buy Now Price: "+items.get(i).getBuyNowPrice());
	            			currentBid.setText("Current Bid: "+items.get(i).getBid());
	            			status.setText("Status: "+items.get(i).boughtStatus());
	            			imageView.setImage(items.get(i).getImage());
	            			break;
	            		}
	            	}
	          }
	    });
		
	    gridPane.add(loginName,1,0);
	    gridPane.add(itemName,0,0);
	    gridPane.add(itemDescription,0,2);
	    gridPane.add(startingPrice,0,3);
	    gridPane.add(buyNowPrice,0,4);
	    gridPane.add(currentBid,0,5);
	    gridPane.add(status,0,6);
	    gridPane.add(itemList,2,0);
	    gridPane.add(bid,1,4);
	    gridPane.add(buyNow,1,5);
	    gridPane.add(logOut,1,6);
	    gridPane.add(imageView,0,1);
	    gridPane.add(enterBid, 1, 3);
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setStyle("-fx-background-color: BISQUE;");
	    Scene scene = new Scene(gridPane);  
	    
	      start.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
//	            	Stage secondaryStage = new Stage();
	        	    primaryStage.setTitle("Online Auction"); 
	        	    loginName.setText("Logged in as : "+username.getText());
	        	    primaryStage.setScene(scene); 
	        	    primaryStage.show(); 
	        	    mainPlayer.play();
	            }
	        });
	      
	      
	      try { 
				// Create a socket to connect to the server 
				@SuppressWarnings("resource")
				Socket socket = new Socket("localhost", 4000); 

				// Create an input stream to receive data from the server 
				InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(streamReader);
				writer = new PrintWriter(socket.getOutputStream());
				System.out.println("networking established");
				Thread readerThread = new Thread(new IncomingReader());
				readerThread.start();
			} 
			catch (IOException ex) { 
				System.out.println("Error connecting to server");
			}
		    logOut.setOnAction(new EventHandler<ActionEvent>() {
		          @Override
		          public void handle(ActionEvent event) {
		            	System.exit(0);
		          }
		    });
	}
	class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					String[] arr = message.split(",");
					if(arr.length==3) {
						for(int i=0;i<items.size();i++) {
			        		  if(items.get(i).getItemName().equals(arr[2])) {
			        			  items.get(i).setBid(Double.parseDouble(arr[0]));
			        			  if(items.get(i).getBuyNowPrice()<=items.get(i).getBid()) {
			        				  items.get(i).boughtItem(arr[1]);
			        			  }
			        			  if(itemName.getText().equals(arr[2])) {
			        				  currentBid.setText("Current Bid: "+Double.parseDouble(arr[0]));
			        				  status.setText("Status: "+items.get(i).boughtStatus());
			        			  }
			        		  }
			        	  }
					}
					else if(arr.length==2) {
						for(int i=0;i<items.size();i++) {
							if(items.get(i).getItemName().equals(arr[0])) {
								items.get(i).setBid(Double.parseDouble(arr[1]));
								if(items.get(i).getBid()>=items.get(i).getBuyNowPrice()) {
									items.get(i).boughtItem("Unknown");
								}
								if(i==0) {
									currentBid.setText("Current Bid: "+Double.parseDouble(arr[1]));
									status.setText(items.get(0).boughtStatus());
								}
							}
						}
					}
			
						
				}
			} catch (IOException ex) {
				System.out.println();
			}
		}
	}
	public static void addItems() {
		try {
			File input = new File(System.getProperty("user.dir") + "\\src\\finalexam_client"+"\\input.txt");
			Scanner sc = new Scanner(input);
			while(sc.hasNextLine()) {
				String[]arr = sc.nextLine().split(",");
				items.add(new AuctionedItem(arr[0],arr[1],Double.parseDouble(arr[2]),Double.parseDouble(arr[3]),Double.parseDouble(arr[4]),arr[5]));
			}
		}
		catch(Exception e) {
			System.out.println("Error reading auction item input");
			e.printStackTrace();
		}

	}
}
