package youbook.model;

public class WishList {
    private int wishlistId;
    private Books book;
    private User user;

    public WishList(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
