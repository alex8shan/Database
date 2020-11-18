package youbook.model;

public class Book {
	int bookId;
	String title;
	String publisherName;
	String publicationYear;
	
	/**
	 * create constructor for books with following parameters
	 * @param bookId
	 * @param title
	 * @param publisherName
	 * @param publicationYear
	 */
	public Book(int bookId, String title,
	            String publisherName, String publicationYear) {
		this.bookId = bookId;
		this.title = title;
		this.publisherName = publisherName;
		this.publicationYear = publicationYear;
	}

	public Book(String title, String publisherName, String publicationYear) {
		this.title = title;
		this.publisherName = publisherName;
		this.publicationYear = publicationYear;
	}

	public Book(int bookId) {
		this.bookId = bookId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	@Override
	public String toString() {
		return "Book{" +
				"bookId=" + bookId +
				", title='" + title + '\'' +
				", publisherName='" + publisherName + '\'' +
				", publicationYear='" + publicationYear + '\'' +
				'}';
	}
}
