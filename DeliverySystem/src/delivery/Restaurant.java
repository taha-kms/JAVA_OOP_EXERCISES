package delivery;

import java.util.*;

public class Restaurant {
    private String category;
    private String name;
    private List<Integer> ratingsList;
    private Map<String, Dish> dishes;


    public Restaurant(String name, String category) {
        this.category = category;
        this.name = name;
        this.dishes = new HashMap<>();
        this.ratingsList = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public List<String> getDishes() {
        return new ArrayList<>(dishes.keySet());
    }

    public void addDish(String dish, float price) throws DeliveryException {
        if (dishes.containsKey(dish)) {
            throw new DeliveryException("Dish already exists");
        }
        this.dishes.put(dish, new Dish(dish, price));
    }

    public float getDishPrice(String dish) {
        return dishes.get(dish).getPrice();
    }

    public Dish getDish(String dish) {
        return dishes.get(dish);
    }

    public void addRating(int rating) {
        if(rating >= 1 || rating <= 5) ratingsList.add(rating);    
    }

    public float getAverageRating() {

        int sum = 0;
        for(int rating : ratingsList) sum += rating;
        return (float) sum / ratingsList.size();
    }
    
}
