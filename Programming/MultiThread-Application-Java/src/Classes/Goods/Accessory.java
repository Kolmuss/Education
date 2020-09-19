package Classes.Goods;

public class Accessory extends Good{
    private String type;

    public Accessory(Long id, double price, int available, int sold, String brand, String name, String size, String season, String kind, String poster, String type) {
        super(id, price, available, sold, brand, name, size, season, kind, poster);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void getSale() {

    }

    @Override
    public String toString() {
        return "ACCESSORY: " + "\n" +
                name + "\n" +
                "price: " + price + '\n' +
                "brand: " + brand + '\n' +
                "size: " + size + '\n' +
                "type: " + type + '\n' +
                "season: " + season;
    }
}
