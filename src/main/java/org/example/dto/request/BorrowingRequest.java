package org.example.dto.request;

public class BorrowingRequest {
    private int borrowingId;
    private int bookId;
    private int memberId;
    public BorrowingRequest(int bookId, int memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
    }
    public BorrowingRequest() {}
    public int getBookId() {
        return bookId;
    }
    public int getMemberId() {
        return memberId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public int getBorrowingId() {
        return borrowingId;
    }
    public void setBorrowingId(int borrowingId) {
        this.borrowingId = borrowingId;
    }
}
