package Classes.Goods;

public class Shoes extends Good {
    private String material;

    public Shoes(Long id, double price, int available, int sold, String brand, String name, String size, String season, String kind, String poster, String material) {
        super(id, price, available, sold, brand, name, size, season, kind, poster);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public void getSale() {

    }

    @Override
    public String toString() {
        return "SHOES: " + "\n" +
                name + "\n" +
                "price: " + price + '\n' +
                "brand: " + brand + '\n' +
                "size: " + size + '\n' +
                "material: " + material + '\n' +
                "season: " + season;
    }
}
