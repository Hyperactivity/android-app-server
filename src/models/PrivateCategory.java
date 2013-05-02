package models;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class PrivateCategory{
    static final long serialVersionUID = 6L;
    private int id;
    private int colorCode;
    private Category parentPrivateCategory;
    private Account account;
    private String headLine;
    private List<Note> notes;
    private List<LinkedThread> linkedThreads;

    public PrivateCategory(String headLine, int colorCode, Category parentPrivateCategory, Account account) {
        this.headLine = headLine;
        this.colorCode = colorCode;
        this.parentPrivateCategory = parentPrivateCategory;
        this.account = account;
    }

    public PrivateCategory() {
    }

    @Column(name = "id")
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "colorCode")
    @Basic
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    @ManyToOne
    @JoinColumn(name = "parentPrivateCategoryId", referencedColumnName = "id", nullable = false)
    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public void setParentPrivateCategory(Category parentPrivateCategory) {
        this.parentPrivateCategory = parentPrivateCategory;
    }

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    @OneToMany(mappedBy = "parentPrivateCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> replies) {
        this.notes = replies;
    }

    @OneToMany(mappedBy = "parentPrivateCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<LinkedThread> getLinkedThreads() {
        return linkedThreads;
    }

    public void setLinkedThreads(List<LinkedThread> linkedThreads) {
        this.linkedThreads = linkedThreads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateCategory that = (PrivateCategory) o;

        if (colorCode != that.colorCode) return false;
        if (id != that.id) return false;
        if (headLine != null ? !headLine.equals(that.headLine) : that.headLine != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + colorCode;
        result = 31 * result + (headLine != null ? headLine.hashCode() : 0);
        return result;
    }
}
