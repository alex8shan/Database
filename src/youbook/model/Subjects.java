package youbook.model;


public class Subjects {
	
    private String subjectMatter;
    private Book book;
    
	// This constructor can be used for reading records from MySQL, where we have all fields
	// including the subjectMatter.
    public Subjects(String subjectMatter, Book book) {
        this.subjectMatter = subjectMatter;
        this.book = book;
    }
    
 // This constructor can be used for reading records from MySQL, where we only have 
 // the subjectMatter, such as a foreign key reference to subjectMatter.
 // Given subjectMatter, we can fetch the full Subjects record.
    public Subjects(String subjectMatter) {
    	this.subjectMatter = subjectMatter;
    }

    public String getSubjectMatter() {
        return this.subjectMatter;
    }

    public void setSubjectMatter(String subjectMatter) {
        this.subjectMatter = subjectMatter;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
