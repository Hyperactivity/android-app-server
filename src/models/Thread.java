package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
@Entity
public class Thread implements Serializable {

    public Thread(Category parentCategory, Account account, String threadName, String threadText)
    {
        setParentCategory(parentCategory);
        setAccount(account);
        setHeadLine(threadName);
        setText(threadText);
    }

    @Deprecated
    public Thread() {

    }

    private int parentCategoryId;


    @Column(name = "parentCategoryId", insertable=false, updatable=false)
    @Basic
    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }



    private int id;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String headLine;

    @Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    private String text;

    @Column(name = "text")
    @Basic
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private List<Reply> replies;

    @OneToMany(mappedBy = "parentThread", fetch=FetchType.LAZY)
    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    private Account account;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private Category parentCategory;

    @ManyToOne
    @JoinColumn(name = "parentCategoryId", referencedColumnName = "id", nullable = false)
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thread thread = (Thread) o;

        if (id != thread.id) return false;
        if (headLine != null ? !headLine.equals(thread.headLine) : thread.headLine != null) return false;
        if (text != null ? !text.equals(thread.text) : thread.text != null) return false;

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
