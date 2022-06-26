package ru.vik.testdatabase;

public class Book {

    private String name;            //Название
    private String publisherName;   //Издательский дом
    private String author;          //Автор             //TODO Подумать как лучше оформить поле автора
    private int yearPublishing;     //Год издания
    private double price;           //Цена
    private String shopAddress;     //Адрес магазина    //TODO Подумать, через что лучше оформить: Текстом, геопозицей через geohash или через +Code
    private boolean available;      //Наличие
    private int amountNum;         //Количество

    public Book() {
    }

    public Book(String name, String publisherName, String author, int yearPublishing, double price, String shopAddress, boolean available, int amountNum) {
        this.name = name;
        this.publisherName = publisherName;
        this.author = author;
        this.yearPublishing = yearPublishing;
        this.price = price;
        this.shopAddress = shopAddress;
        this.available = available;
        this.amountNum = amountNum;
    }

    public String getName() {
        return name;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearPublishing() {
        return yearPublishing;
    }

    public double getPrice() {
        return price;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getAmountNum() {
        return amountNum;
    }

}
