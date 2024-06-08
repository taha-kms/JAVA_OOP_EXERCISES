package delivery;


import java.util.*;

public class Order {


    private Map<String, Integer> dishes;
    private String customerName;
    private Restaurant restaurant;
    private int deliveryTime;
    private int deliveryDistance;
    private int orderID;
    private boolean isPending;

    public Order(int orderID, String dishName[], int quantity[], String customerName, Restaurant restaurant, int deliveryTime, int deliveryDistance){
        
        this.customerName = customerName;
        this.restaurant = restaurant;
        this.deliveryTime = deliveryTime;
        this.deliveryDistance = deliveryDistance;
        this.orderID = orderID;
        this.dishes = new HashMap<>();
        this.isPending = true;

        for(int i = 0; i < dishName.length; i++){
            this.dishes.put(dishName[i], quantity[i]);
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getRestaurantName() {
        return restaurant.getName();
    }

    public boolean isPending() {
        return isPending;
    }

    public void setAssigned() {
        this.isPending = false;
    }

}
