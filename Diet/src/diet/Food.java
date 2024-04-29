package diet;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {

	public static Food instance;
	protected Map<String, NutritionalElement> rawMaterialsMap = new HashMap<>();
	protected Map<String, NutritionalElement> products = new HashMap<>();
	protected Map<String, NutritionalElement> recipes = new HashMap<>();
	protected Map<String, NutritionalElement> menues = new HashMap<>();

	/**
	 * Define a new raw material.
	 * The nutritional values are specified for a conventional 100g quantity
	 * @param name unique name of the raw material
	 * @param calories calories per 100g
	 * @param proteins proteins per 100g
	 * @param carbs carbs per 100g
	 * @param fat fats per 100g
	 */
	public Food(){
		this.rawMaterialsMap = new HashMap<>();
		this.products = new HashMap<>();
		this.recipes = new HashMap<>();

	}


	public void defineRawMaterial(String name, double calories, double proteins, double carbs, double fat) {
        NutritionalElement rawMaterial = new RawMaterial(name, calories, proteins, carbs, fat);
        this.rawMaterialsMap.put(name, rawMaterial);
	}

	/**
	 * Retrieves the collection of all defined raw materials
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials() {
		return this.rawMaterialsMap.values();
	}

	/**
	 * Retrieves a specific raw material, given its name
	 * @param name  name of the raw material
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name) {
		return this.rawMaterialsMap.get(name);
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * @param name unique name of the product
	 * @param calories calories for a product unit
	 * @param proteins proteins for a product unit
	 * @param carbs carbs for a product unit
	 * @param fat fats for a product unit
	 */
	public void defineProduct(String name, double calories, double proteins, double carbs, double fat) {
		NutritionalElement product = (NutritionalElement) new Product(name, calories, proteins, carbs, fat);
		this.products.put(name, product);
	}

	/**
	 * Retrieves the collection of all defined products
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products() {
		return this.products.values();
	}

	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name) {
		return this.products.get(name);
	}

	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe recipe = new Recipe(name);
		this.recipes.put(name, (NutritionalElement) recipe);

		recipe.rawMaterialsMap = this.rawMaterialsMap;

		return recipe;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes() {
		return this.recipes.values();
	}

	/**
	 * Retrieves a specific recipe, given its name
	 * @param name  name of the recipe
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name) {
		return this.recipes.get(name);
	}

	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu menu = new Menu(name);
		menu.rawMaterialsMap = this.rawMaterialsMap;
		menu.products = this.products;
		menu.recipes = this.recipes;
		return menu;
	}

}