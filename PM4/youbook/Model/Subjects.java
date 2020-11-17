


public class Subjects {
    private String subjectMatter;
    private Books book;

    public Subjects(String subjectMatter, Books book) {
        this.subjectMatter = subjectMatter;
        this.book = book;
    }

    public String getSubjectMatter() {
        return subjectMatter;
    }

    public void setSubjectMatter(String subjectMatter) {
        this.subjectMatter = subjectMatter;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}
