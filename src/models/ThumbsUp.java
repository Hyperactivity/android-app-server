package models;

import javax.persistence.*;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@javax.persistence.IdClass(models.ThumbsUpPK.class)
@Entity
public class ThumbsUp implements Externalizable {
    static final long serialVersionUID = 10L;
    private int replyId;
    private int accountId;
    private Reply reply;
    private Account account;

    public ThumbsUp(int replyId, int accountId) {
        this.replyId = replyId;
        this.accountId = accountId;
    }

    @Deprecated
    public ThumbsUp() {
    }

    @javax.persistence.Column(name = "replyId")
    @Id
    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    @javax.persistence.Column(name = "accountId")
    @Id
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replyId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
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

        ThumbsUp thumbsUp = (ThumbsUp) o;

        if (accountId != thumbsUp.accountId) return false;
        if (replyId != thumbsUp.replyId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = replyId;
        result = 31 * result + accountId;
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
        out.writeObject(reply);
        out.writeObject(account);
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
