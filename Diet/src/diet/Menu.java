package diet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu extends Food implements NutritionalElement {

	String name;
    double calories;
    double proteins;  
    double carbs;
    double fat;
	double ratio;
	List<RawMaterial> materials;

    public Menu(String name) {
        this.name = name;
        this.calories = 0.0;
        this.proteins = 0.0;
        this.carbs = 0.0;
        this.fat = 0.0;
		this.ratio = 0.0;
		this.materials = new ArrayList<>();
    }


	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String user_recipe, double quantity) {
		NutritionalElement recipe = this.recipes.get(user_recipe);
		double factor = quantity / 100.0;
		if (recipe == null) {
			throw new IllegalArgumentException("Unknown raw product: " + recipe);
		}else{
			
			this.calories += recipe.getCalories() * factor;
			this.proteins += recipe.getProteins() * factor;
			this.carbs += recipe.getCarbs() * factor;
			this.fat += recipe.getFat() * factor;
		}
		return this;
	}

	

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String input_product) {
		NutritionalElement product = this.products.get(input_product);

		if (product == null) {
			throw new IllegalArgumentException("Unknown raw product: " + product);
		}else{
			
			this.calories += product.getCalories();
			this.proteins += product.getProteins();
			this.carbs += product.getCarbs();
			this.fat += product.getFat();
		}
		return this;
	}

	

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return this.calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return this.proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return this.carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return this.fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}