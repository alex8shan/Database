

public class Authors {
	
	int authorId;
	String authorName;
	int bookId;
	
	/**
	 * constructor for authors class with following parameters
	 * @param authorId
	 * @param authorName
	 * @param bookId
	 */
	public Authors(int authorId, String authorName, int bookId) {
		super();
		this.authorId = authorId;
		this.authorName = authorName;
		this.bookId = bookId;
	}

	public Authors(String authorName, int bookId) {
		super();
		this.authorName = authorName;
		this.bookId = bookId;
	}

	public Authors(int authorId) {
		this.authorId = authorId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	

}
