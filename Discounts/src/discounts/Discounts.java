package discounts;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Discounts {

	private Integer nextCardNum;
	private Map<Integer, String> cardsMap;
	private Map<String, Category> categoryMap;
	private Map<String, Product> productMap;


	public Discounts(){

		this.nextCardNum = 1;
		this.cardsMap = new HashMap<>();
		this.productMap = new HashMap<>();
		this.categoryMap = new HashMap<>();
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
	public void addProduct(String categoryId, String productId, double price) throws DiscountsException {
		if(this.productMap.containsKey(productId)) throw new DiscountsException();


		this.categoryMap.computeIfAbsent(categoryId, k -> new Category(categoryId));
		Product product = new Product(productId, price, this.categoryMap.get(categoryId));		
		
		this.productMap.put(productId, product);

	}
	
	public double getPrice(String productId) throws DiscountsException {
		if(!this.productMap.containsKey(productId)) throw new DiscountsException();

        return this.productMap.get(productId).getPrice();
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
		
		
		List<Product> products = this.categoryMap.get(categoryId).getProductList();

		double total = products.stream()
							.mapToDouble(p -> p.getPrice())
							.sum();
	
		double average = total / products.size();


		BigDecimal avgBigDecimal = BigDecimal.valueOf(average);
		BigDecimal roundedAvg = avgBigDecimal.setScale(0, RoundingMode.CEILING);

		return (int)roundedAvg.doubleValue();
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {

		if(!this.categoryMap.containsKey(categoryId) || percentage > 50) throw new DiscountsException();
		this.categoryMap.get(categoryId).setDiscountRatio(percentage);
	}

	public int getDiscount(String categoryId) {
        return this.categoryMap.get(categoryId).getDiscountRatio();
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
