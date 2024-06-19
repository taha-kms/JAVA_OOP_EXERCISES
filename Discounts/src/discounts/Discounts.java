package discounts;
import java.util.*;

public class Discounts {

	private Integer nextCardNum;
	private Map<Integer, String> cardsMap;


	public Discounts(){

		this.nextCardNum = 1;
		this.cardsMap = new HashMap<>();
	}
	
	//R1
	public int issueCard(String name) {
		int newCardNum = this.nextCardNum;
		this.cardsMap.put(newCardNum, name);
		this.nextCardNum++;

	    return newCardNum;
	}
	
    public String cardHolder(int cardN) {
        return this.cardsMap.get(cardN);
    }
    

	public int nOfCards() {
	       return this.cardsMap.size();

	}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) 
			throws DiscountsException {
	}
	
	public double getPrice(String productId) 
			throws DiscountsException {
        return -1.0;
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
        return -1;
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
	}

	public int getDiscount(String categoryId) {
        return -1;
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {
        return -1;
	}

	public int addPurchase(String... items) throws DiscountsException {
        return -1;
	}
	
	public double getAmount(int purchaseCode) {
        return -1.0;
	}
	
	public double getDiscount(int purchaseCode)  {
        return -1.0;
	}
	
	public int getNofUnits(int purchaseCode) {
        return -1;
	}
	
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
        return null;
	}
	
	public SortedMap<Integer, Double> totalPurchasePerCard() {
        return null;
	}
	
	public int totalPurchaseWithoutCard() {
        return -1;
	}
	
	public SortedMap<Integer, Double> totalDiscountPerCard() {
        return null;
	}


}
