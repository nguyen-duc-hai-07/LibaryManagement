package controller;

import config.DBConnectionPool;
import dao.BookDAO;
import dao.BorrowingDAO;
import dao.MemberDAO;
import dao.impl.BookDAOImpl;
import dao.impl.BorrowingDAOImpl;
import dao.impl.MemberDAOImpl;
import dto.BookResponse;
import dto.BorrowingDTO;
import dto.MemberResponse;
import model.Book;
import model.Member;
import service.BookService;
import service.BorrowingService;
import service.MemberService;
import service.impl.BookServiceImpl;
import service.impl.BorrowingServiceImpl;
import service.impl.MemberServiceImpl;

import java.util.*;

public class Main {
    private static final DBConnectionPool pool = DBConnectionPool.getInstance();
    private static final BookDAO bookDAO = new BookDAOImpl();
    private static final BorrowingDAO borrowingDAO = new BorrowingDAOImpl();
    private static final MemberDAO memberDAO = new MemberDAOImpl();

    private static final BookService bookService = new BookServiceImpl(bookDAO, pool);
    private static final BorrowingService borrowingService = new BorrowingServiceImpl(borrowingDAO, bookDAO, memberDAO, pool);
    private static final MemberService memberService = new MemberServiceImpl(memberDAO, pool);
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        while(true) {
            System.out.println("1. Thêm sách");
            System.out.println("2. Thêm thành viên");
            System.out.println("3. Mượn sách");
            System.out.println("4. Trả sách");
            System.out.println("5. Xem tất cả lượt mượn");
            System.out.println("6. Xem sách member đang mượn");
            System.out.println("7. Xem tất cả sách");
            System.out.println("8. Xem tất cả thành viên");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    AddBook();
                    break;
                case 2:
                    AddMember();
                    break;
                case 3:
                    BorrowBook();
                    break;
                case 4:
                    ReturnBook();
                    break;
                case 5:
                    ViewAllBorrow();
                    break;
                case 6:
                    ViewAllMemberBorrow();
                    break;
                case 7:
                    ViewAllBook();
                    break;
                case 8:
                    ViewAllMember();
                    break;
                default:
                    System.out.println("Chọn lại!");
                    break;
                case 0:
                    return;
            }
        }
    }

    static void AddBook() {
        try {
            System.out.print("Nhập tên sách: ");
            String title = sc.nextLine();

            System.out.print("Nhập tên tác giả: ");
            String author = sc.nextLine();

            System.out.print("Nhập số lượng: ");
            int quantity = sc.nextInt();
            sc.nextLine();

            Book book = new Book(title, author, quantity);
            bookService.addBook(book);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void AddMember() {
        try {
            System.out.print("Nhập tên: ");
            String name = sc.nextLine();

            System.out.print("Nhập email: ");
            String email = sc.nextLine();

            System.out.print("Nhập số điện thoại: ");
            String phone = sc.nextLine();

            Member member = new Member(name, email, phone);
            memberService.addMember(member);

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void BorrowBook() {
        try {
            List<BookResponse> books = bookService.findAllBooks();
            for(BookResponse book : books) {
                System.out.println(book);
            }
            System.out.print("Nhập book id: ");
            int bookId = sc.nextInt();

            List<MemberResponse> members = memberService.findAllMembers();
            for(MemberResponse member : members) {
                System.out.println(member);
            }
            System.out.print("Nhập member id: ");
            int memberId = sc.nextInt();

            borrowingService.borrowBook(bookId, memberId);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void ReturnBook() {
        try {
            System.out.print("Nhập borrowing id: ");
            int borrowingId = sc.nextInt();
            borrowingService.returnBook(borrowingId);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void ViewAllBorrow() {
        try {
            List<BorrowingDTO> borrows = borrowingService.findAllBorrowings();

            for(BorrowingDTO borrow : borrows) {
                System.out.println(borrow);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
    static void ViewAllMember() {
        try {
            List<MemberResponse> members = memberService.findAllMembers();

            for(MemberResponse member : members) {
                System.out.println(member);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void ViewAllMemberBorrow() {
        try {
            List<MemberResponse> members = memberService.findAllMembers();
            for(MemberResponse member : members) {
                System.out.println(member);
            }
            System.out.print("Nhập member id: ");
            int memberId = sc.nextInt();

            List<BorrowingDTO> borrows = borrowingService.findAllBorrowingsByMemberId(memberId);

            for(BorrowingDTO borrow : borrows) {
                System.out.println(borrow);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void ViewAllBook() {
        try {
            List<BookResponse> books = bookService.findAllBooks();

            for(BookResponse book : books) {
                System.out.println(book);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
