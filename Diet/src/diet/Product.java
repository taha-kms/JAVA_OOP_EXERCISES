package diet;

public class Product implements NutritionalElement {

    String name;
    double calories;
    double proteins;  
    double carbs;
    double fat;


    public Product(String name, double calories, double proteins, double carbs, double fat) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }
	/**
	 * Retrieves the name of the nutritional element.
	 * Typically the name is unique within a specific category (e.g. raw materials)
	 * @return the string containing the name
	 */
	public String getName(){
        return this.name;
	}
	/**
	 * Retrieves the quantity of calories for the element.
	 * Such value can be referred to 100g of element (if {@link #per100g()} returns {@code true})
	 * or to a unit of element (if {@link #per100g()} returns {@code false}).
	 * @return calories
	 */
	public double getCalories(){
        return this.calories;
    }
	/**
	 * Retrieves the quantity of proteins for the element.
	 * Such value can be referred to 100g of element (if {@link #per100g()} returns {@code true})
	 * or to a unit of element (if {@link #per100g()} returns {@code false}).
	 * @return calories
	 */
	public double getProteins(){
        return this.proteins;
    }
	
	/**
	 * Retrieves the quantity of carbs for the element.
	 * Such value can be referred to 100g of element (if {@link #per100g()} returns {@code true})
	 * or to a unit of element (if {@link #per100g()} returns {@code false}).
	 * @return calories
	 */
	public double getCarbs(){
        return this.carbs;
    }
	
	/**
	 * Retrieves the quantity of fat for the element.
	 * Such value can be referred to 100g of element (if {@link #per100g()} returns {@code true})
	 * or to a unit of element (if {@link #per100g()} returns {@code false}).
	 * @return calories
	 */
	public double getFat(){
        return this.fat;
    }

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * @return boolean indicator
	 */
	public boolean per100g(){
        return false;
    }

}

