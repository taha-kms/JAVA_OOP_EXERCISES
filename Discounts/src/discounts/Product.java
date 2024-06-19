package discounts;

public class Product {

    private String id;
	private double price;
	private Category category;

    public Product(String id, double price, Category category){
        this.id = id;
        this.price = price;
        this.category = category;
        this.category.addProduct(this);
    }

    public Category getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price * ( 1 - (category.getDiscountRatio() / 100) );
    }

}
