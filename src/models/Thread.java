package models;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
@Entity
public class Thread implements Externalizable {
    static final long serialVersionUID = 9L;
    private int id;
    private String headLine;
    private String text;
    private List<Reply> replies;
    private Account account;
    private Category parentCategory;
    private int accountId;
    private Timestamp time;
    private int parentCategoryId;

    public Thread(Category parentCategory, Account account, String threadName, String threadText, Timestamp currentTime) {
        setParentCategory(parentCategory);
        setAccount(account);
        setHeadLine(threadName);
        setText(threadText);
        setTime(currentTime);
    }

    @Deprecated
    public Thread() {

    }

    @Column(name = "parentCategoryId", insertable = false, updatable = false)
    @Basic
    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    @Column(name = "text")
    @Basic
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToMany(mappedBy = "parentThread")
    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    @ManyToOne()
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "parentCategoryId", referencedColumnName = "id", nullable = false)
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Column(name = "accountId", updatable = false, insertable = false)
    @Basic
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Column(name = "time")
    @Basic
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thread thread = (Thread) o;

        if (accountId != thread.accountId) return false;
        if (id != thread.id) return false;
        if (parentCategoryId != thread.parentCategoryId) return false;
        if (headLine != null ? !headLine.equals(thread.headLine) : thread.headLine != null) return false;
        if (text != null ? !text.equals(thread.text) : thread.text != null) return false;
        if (time != null ? !time.equals(thread.time) : thread.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentCategoryId;
        result = 31 * result + accountId;
        result = 31 * result + id;
        result = 31 * result + (headLine != null ? headLine.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * @param out the stream to write the object to
     * @throws java.io.IOException Includes any I/O exceptions that may occur
     * @serialData Overriding methods should use this tag to describe
     * the data layout of this Externalizable object.
     * List the sequence of element types and, if possible,
     * relate the element to a public/protected field and/or
     * method of this Externalizable class.
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(headLine);
        out.writeUTF(text);
        out.writeObject(account);
        out.writeObject(parentCategory);
        out.writeObject(time);
    }

    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * @param in the stream to read data from in order to restore the object
     * @throws java.io.IOException    if I/O errors occur
     * @throws ClassNotFoundException If the class for an object being
     *                                restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
