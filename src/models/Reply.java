package models;

import javax.persistence.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
@Entity
public class Reply implements Externalizable {
    static final long serialVersionUID = 8L;
    private Thread parentThread;
    private Account account;
    private Collection<ThumbsUp> thumbsUp;
    private int id;
    private Timestamp time;
    private String text;

    public Reply(Thread parentThread, Account account, String text, Timestamp currentTime) {
        setParentThread(parentThread);
        setAccount(account);
        setText(text);
        setTime(currentTime);
    }

    @Deprecated
    public Reply() {
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

    @javax.persistence.Column(name = "time")
    @Basic
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
    @JoinColumn(name = "threadId", referencedColumnName = "id", nullable = false)
    public Thread getParentThread() {
        return parentThread;
    }

    public void setParentThread(Thread parentThread) {
        this.parentThread = parentThread;
    }

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reply reply = (Reply) o;

        if (id != reply.id) return false;
        if (text != null ? !text.equals(reply.text) : reply.text != null) return false;
        if (time != null ? !time.equals(reply.time) : reply.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reply", cascade = CascadeType.REMOVE)
    public Collection<ThumbsUp> getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Collection<ThumbsUp> thumbsUp) {
        this.thumbsUp = thumbsUp;
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
        out.writeObject(parentThread);
        out.writeObject(account);
        out.writeInt(id);
        out.writeObject(time);
        out.writeObject(text);
        out.writeObject(new ArrayList<ThumbsUp>(thumbsUp));
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
