package Classes.Goods;

public class Clothing extends Good{
    private String color;

    public Clothing(Long id, double price, int available, int sold, String brand, String name, String size, String season, String kind, String poster, String color) {
        super(id, price, available, sold, brand, name, size, season, kind, poster);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void getSale() {

    }

    @Override
    public String toString() {
        return "CLOTHING: " + "\n" +
                name + "\n" +
                "price: " + price + '\n' +
                "brand: " + brand + '\n' +
                "size: " + size + '\n' +
                "color: " + color + '\n' +
                "season: " + season;
    }
}
