package discounts;



import java.util.*;

public class Category {
    
	private String id;
    private Integer discountRatio;
    private List<Product> productList;

    public Category(String id){
        this.id = id;
        this.productList = new ArrayList<>();
        this.discountRatio = 0;
    }

    public void addProduct(Product product){
        this.productList.add(product);
    }

    public String getId() {
        return this.id;
    }

    public int getDiscountRatio() {
        return this.discountRatio;
    }

    public void setDiscountRatio(int percentage) {
        this.discountRatio = percentage;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
