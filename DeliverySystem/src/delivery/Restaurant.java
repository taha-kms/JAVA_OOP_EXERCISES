package delivery;

import java.util.*;

public class Restaurant {
    private String category;
    private String name;
    private Map<String, Dish> dishes;

    public Restaurant(String name, String category) {
        this.category = category;
        this.name = name;
        this.dishes = new HashMap<>();
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
    
}
