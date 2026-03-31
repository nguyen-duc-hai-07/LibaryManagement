package org.dto.request;

public class BookRequest {
    private String title;
    private String author;
    private int quantity;

    public BookRequest(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }
    public BookRequest() {}

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
