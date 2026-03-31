package org.example.dto.response;

import java.time.LocalDate;

public class BorrowingDTO {
    private int id;
    private String title;
    private String author;
    private String memberName;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowingDTO(int id, String title, String author , String memberName, LocalDate borrowDate,LocalDate returnDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.memberName = memberName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    public BorrowingDTO() {}

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getMemberName() {
        return memberName;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    @Override
    public String toString() {
        return "BorrowingDTO [id=" + id + ", title=" + title
                + ", author=" + author + ", memberName=" + memberName
                + ", borrowDate=" + borrowDate + "]";
    }
}
