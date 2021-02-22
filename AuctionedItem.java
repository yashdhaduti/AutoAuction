package finalexam_server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class AuctionedItem {

	
	private double startingPrice;
	private double buyNowPrice;
	private String itemDescription;
	private String itemName;
	private double bid;
	private boolean bought;
	private Image image;
	
	public AuctionedItem(String itemName, String itemDescription, double startingPrice, double bid, double buyNowPrice, String image) {
		this.itemName=itemName;
		this.itemDescription=itemDescription;
		this.startingPrice=startingPrice;
		this.bid=bid;
		this.buyNowPrice=buyNowPrice;
		this.bought=false;
		try {
			this.image = new Image(new FileInputStream("src\\finalexam_client\\"+image+".jpg"));
		}
		catch(FileNotFoundException e) {
			System.out.print("Input file could not be found");
		}
	}

	public double getStartingPrice() { return startingPrice; }
	public double getBid() { return bid; }
	public double getBuyNowPrice() { return buyNowPrice; }
	public String getItemName() { return itemName; }
	public String getItemDescription() { return itemDescription; }
	public boolean boughtOrNot() { return bought; }
	public String boughtStatus() {
		if(bought)
			return "CLOSED";
		else
			return "OPEN";
	}
	
	public void setBid(double bid) { this.bid=bid; }
	public void boughtItem() { bought=true; }
	public Image getImage() { return image; }
	
	
}
