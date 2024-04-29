package diet;


import java.util.*;
/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe extends Food implements NutritionalElement {
	String name;
    double calories;
    double proteins;  
    double carbs;
    double fat;
	double ratio;
	List<NutritionalElement> materials;


    public Recipe(String name) {
        this.name = name;
        this.calories = 0.0;
        this.proteins = 0.0;
        this.carbs = 0.0;
        this.fat = 0.0;
		this.ratio = 0.0;
		this.materials = new ArrayList<>();
    }
	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {

		NutritionalElement rawMaterial = this.rawMaterialsMap.get(material);

		if (rawMaterial == null) {
			throw new IllegalArgumentException("Unknown raw material: " + rawMaterial);
		}else{
			this.ratio = quantity/100;
			this.materials.add(rawMaterial);
			this.calories += rawMaterial.getCalories() * ratio;
			this.proteins += rawMaterial.getProteins() * ratio;
			this.carbs += rawMaterial.getCarbs() * ratio;
			this.fat += rawMaterial.getFat() * ratio;
		}
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	
	@Override
	public double getCalories() {
		return this.calories;
	}
	

	@Override
	public double getProteins() {
		return this.proteins;
	}

	@Override
	public double getCarbs() {
		return this.carbs;
	}

	@Override
	public double getFat() {
		return this.fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
}
