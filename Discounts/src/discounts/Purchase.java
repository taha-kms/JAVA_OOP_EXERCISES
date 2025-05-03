package discounts;


import java.util.*;
import java.util.stream.Collectors;;
public class Purchase {
    
    private int cardId;
    private int purchaseId;
    private Map<Product, Integer> purchasedItems;

    public Purchase(int cardId, int purchaseId){
        this.cardId = cardId;
        this.purchaseId = purchaseId;
        this.purchasedItems = new HashMap<>();
    }

    public void addPurchaseItem(Product productCode, int Qnty){
        this.purchasedItems.put(productCode, Qnty);
    }

    public int getCardId() {
        return cardId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public Map<Product, Integer> getPurchasedItems() {
        return purchasedItems;
    }

    
    public double getAmount() {
        double totalAmount = 0;
    
        // Group products by category ID
        Map<String, List<Product>> productsByCategory = purchasedItems.keySet()
            .stream()
            .collect(Collectors.groupingBy(
                p -> p.getCategory().getId(),
                Collectors.toList()
            ));
    
        for (Map.Entry<String, List<Product>> entry : productsByCategory.entrySet()) {
            double amount = 0;
            double discount = 0;
            
            List<Product> products = entry.getValue();
            int discountPercentage = products.get(0).getCategory().getDiscountRatio();
    
            for (Product product : products) {
                int quantity = purchasedItems.get(product); // Assumes Product overrides equals & hashCode
                double price = product.getPrice();
                amount += price * quantity;
            }
    
            if (discountPercentage > 0 && cardId != 0) {
                discount = amount * discountPercentage / 100.0;
            }
    
            totalAmount += (amount - discount);
        }
    
        return totalAmount;
    }

    public double getDiscount(){
        double totalDiscount = 0;
        if(cardId == 0) {
            return totalDiscount;
        }
        for(Map.Entry<Product, Integer> entry : purchasedItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            int discountPercentage = product.getCategory().getDiscountRatio();
            totalDiscount += price * quantity * discountPercentage / 100.0;
        }
        return totalDiscount;
    }
}
