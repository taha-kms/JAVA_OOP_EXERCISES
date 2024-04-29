package diet;


import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order {

	private Restaurant restaurant;
	private Customer customer;
	private Map<String, Integer> items = new HashMap<>();
	private String estimatedDeliveryTime;
	private OrderStatus status;
	private PaymentMethod paymentMethod;
	
	public Order(Customer customer, Restaurant restaurant, String deliveryTime) {

		this.customer = customer;
		this.restaurant = restaurant;
		this.status = OrderStatus.ORDERED;
		this.paymentMethod = PaymentMethod.CASH;
		this.estimatedDeliveryTime = restaurant.adjustDeliveryTime(deliveryTime);


	}

	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		this.paymentMethod = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		this.status = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return this.status;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
    public Order addMenus(String menu, int quantity) {
        items.put(menu, quantity);
        return this;
    }



	    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(restaurant.getName()).append(", ")
          .append(customer.getFirstName()).append(" ").append(customer.getLastName())
          .append(" : (").append(estimatedDeliveryTime).append("):\n");

        // Sort items alphabetically by menu name
        Map<String, Integer> sortedItems = new TreeMap<>(items);

        for (Map.Entry<String, Integer> entry : sortedItems.entrySet()) {
            String menu = entry.getKey();
            int quantity = entry.getValue();
            sb.append("\t").append(menu).append("->").append(quantity).append("\n");
        }

        return sb.toString();
    }

	public String getCustomer() {
		return this.customer.getFullName();
	}

	public String getDeliveryTime(){
		return this.estimatedDeliveryTime;
	}

	public Map<String, Integer> getMenus() {
		return this.items;
	}
}

