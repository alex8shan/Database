

public class WishList {
    private int wishlistId;
    private Books books;
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

    public Books getBook() {
        return books;
    }

    public void setBook(Books book) {
        this.books = book;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users user) {
        this.users = user;
    }
}
