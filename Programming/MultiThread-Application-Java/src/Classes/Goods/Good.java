package Classes.Goods;

import java.io.Serializable;

public abstract class Good implements Serializable {
    protected Long id;
    protected double price;
    protected int available;
    protected int sold;
    protected String brand;
    protected String name;
    protected String size;
    protected String season;
    protected String kind;
    protected String poster;

    public Good(Long id, double price, int available, int sold, String brand, String name, String size, String season, String kind, String poster) {
        this.id = id;
        this.price = price;
        this.available = available;
        this.sold = sold;
        this.brand = brand;
        this.name = name;
        this.size = size;
        this.season = season;
        this.kind = kind;
        this.poster = poster;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void buy(){
        this.available--;
        this.sold++;
    }

    public abstract void getSale();
}
