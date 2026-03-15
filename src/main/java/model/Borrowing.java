package model;

import java.time.LocalDate;

public class Borrowing {
    private int id;
    private int memberId;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public Borrowing(int id, int memberId, int bookId, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    public Borrowing(int memberId, int bookId) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
    }
    public Borrowing() {}
    public int getId() {
        return id;
    }
    public int getMemberId() {
        return memberId;
    }
    public int getBookId() {
        return bookId;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    @Override
    public String toString() {
        return "Borrowing{" + "id="
                + id + ", memberId="
                + memberId + ", bookId="
                + bookId + ", borrowDate="
                + borrowDate + ", returnDate="
                + (returnDate != null ? returnDate : "Chưa trả") + '}';
    }
}
