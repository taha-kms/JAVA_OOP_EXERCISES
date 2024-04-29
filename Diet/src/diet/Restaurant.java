package diet;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import diet.Order.OrderStatus;
import java.util.Optional;
import java.util.Collection;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	
	
	Map<String, Menu> menuses;
	List<String> hours;
	String name;

	public Restaurant(String name) {
		this.name = name;
		this.hours = new ArrayList<>();
		this.menuses = new HashMap<>();

	}

	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
    public void setHours(String... hm) {
        if (hm.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid time format");
        } else {
            for (int i = 0; i < hm.length; i += 2) {
                String hour = hm[i] + "-" + hm[i + 1];
                this.hours.add(hour);
            }
        }
    }

    public boolean isOpenAt(String time) {
        LocalTime checkTime = LocalTime.parse(time);

        for (int i = 0; i < this.hours.size(); i++) {
            String[] parts = this.hours.get(i).split("-");
            LocalTime openingTime = LocalTime.parse(parts[0]);
            LocalTime closingTime = LocalTime.parse(parts[1]);

            if (checkTime.isAfter(openingTime) && checkTime.isBefore(closingTime)) {
                return true;
            }
        }
        return false;
    }

    public String adjustDeliveryTime(String deliveryTime) {
        LocalTime time = LocalTime.parse(deliveryTime);

        if (isOpenAt(time.toString())) {
            return deliveryTime; // No adjustment needed
        } else {
            LocalTime nextOpeningTime = findNextOpeningTime(time);
            return nextOpeningTime.toString();
        }
    }

	private LocalTime findNextOpeningTime(LocalTime time) {
		for (String hour : this.hours) {
			String[] parts = hour.split("-");
			LocalTime openingTime = LocalTime.parse(parts[0]);
			LocalTime closingTime = LocalTime.parse(parts[1]);
	
			if (time.isBefore(openingTime)) {
				// If the given time is before the opening time of the current interval,
				// return the opening time of this interval
				return openingTime;
			} else if (time.isAfter(closingTime)) {
				// If the given time is after the closing time of the current interval,
				// continue to check the next interval
				continue;
			} else {
				// If the given time falls within the current interval,
				// it means the next opening time is the opening time of this interval
				return openingTime;
			}
		}
	
		// If no next opening time found, return the opening time of the first interval of the next day
		String[] firstHours = this.hours.get(0).split("-");
		return LocalTime.parse(firstHours[0]);
	}
	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		this.menuses.put(menu.getName(), menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		return this.menuses.get(name);
	}

	public String ordersWithStatus(OrderStatus status) {
		StringBuilder sb = new StringBuilder();

		Collection<Order> ordersCollection = Takeaway.restaurantOrders.get(this.name);
		List<Order> orders = new ArrayList<>(ordersCollection);
		List<String> customerNames = orders.stream()
										  .map(Order::getCustomer) // Simplified method reference
										  .distinct()
										  .sorted()
										  .collect(Collectors.toList());
	
		for (String customerName : customerNames) {
			Optional<Order> orderOptional = orders.stream()
												  .filter(order -> order.getStatus() == status && order.getCustomer().equals(customerName))
												  .findFirst();
			if (orderOptional.isPresent()) {
				Order order = orderOptional.get();
				sb.append(this.name).append(", ").append(customerName).append(" : (").append(order.getDeliveryTime()).append("):\n");
				order.getMenus().forEach((menu, quantity) -> sb.append("\t"+menu).append("->").append(quantity).append("\n"));
			}
		}
	
		return sb.toString();
	}


}