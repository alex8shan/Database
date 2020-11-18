package youbook.model;


public class Subject {
    private String subjectMatter;
    private Book book;

    public Subject(String subjectMatter, Book book) {
        this.subjectMatter = subjectMatter;
        this.book = book;
    }

    public String getSubjectMatter() {
        return subjectMatter;
    }

    public void setSubjectMatter(String subjectMatter) {
        this.subjectMatter = subjectMatter;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
