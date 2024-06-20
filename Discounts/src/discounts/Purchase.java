package discounts;


import java.util.*;;
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

    
    public double getAmount(){
        double totalAmount = 0;
        for(Map.Entry<Product, Integer> entry : purchasedItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            totalAmount += price * quantity;
        }
        if(cardId != 0) {
            totalAmount = totalAmount * (1 - (getDiscount() / 100.0));
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
