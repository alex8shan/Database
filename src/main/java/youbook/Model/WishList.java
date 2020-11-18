package youbook.Model;

public class WishList {
    private int wishlistId;
    private Book book;
    private Users users;

    public WishList(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users user) {
        this.users = user;
    }
}
