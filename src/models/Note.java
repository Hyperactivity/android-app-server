package models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class Note {
    private int id;
    private String headLine;
    private String text;
    private Account account;
    private PrivateCategory parentPrivateCategory;

    public Note(String headLine, String text, Account account, PrivateCategory parentPrivateCategory) {
        setHeadLine(headLine);
        setText(text);
        setAccount(account);
        setParentPrivateCategory(parentPrivateCategory);
    }

    @Deprecated
    public Note() {
    }

    @javax.persistence.Column(name = "id")
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    @javax.persistence.Column(name = "text")
    @Basic
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "parentPrivateCategoryId", referencedColumnName = "id", nullable = false)
    public PrivateCategory getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public void setParentPrivateCategory(PrivateCategory parentPrivateCategory) {
        this.parentPrivateCategory = parentPrivateCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != note.id) return false;
        if (headLine != null ? !headLine.equals(note.headLine) : note.headLine != null) return false;
        if (text != null ? !text.equals(note.text) : note.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (headLine != null ? headLine.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
