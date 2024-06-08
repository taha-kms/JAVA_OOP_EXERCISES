package delivery;





public class Restaurant {

    private String catagory;
    private String name;


    public Restaurant(String catagory, String name) {
        this.catagory = catagory;
        this.name = name;
    }
    public String getCatagory() {
        return catagory;
    }
    public String getName() {
        return name;
    }
}
