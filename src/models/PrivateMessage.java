package models;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@IdClass(PrivateMessagePK.class)
@Entity
public class PrivateMessage implements Externalizable {
    static final long serialVersionUID = 7L;
    private int senderAccountId;
    private int recieverAccountId;
    private String text;
    private Account senderAccount;
    private Account recieverAccount;
    private Timestamp time;

    public PrivateMessage(int senderAccountId, int recieverAccountId, String text, Timestamp time) {
        setSenderAccountId(senderAccountId);
        setRecieverAccountId(recieverAccountId);
        setText(text);
        setTime(time);
    }

    @Deprecated
    public PrivateMessage() {
    }

    @Column(name = "senderAccountId")
    @Id
    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    @Column(name = "recieverAccountId")
    @Id
    public int getRecieverAccountId() {
        return recieverAccountId;
    }

    public void setRecieverAccountId(int recieverAccountId) {
        this.recieverAccountId = recieverAccountId;
    }

    @Column(name = "text")
    @Basic
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    @JoinColumn(name = "senderAccountId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    @ManyToOne
    @JoinColumn(name = "recieverAccountId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Account getRecieverAccount() {
        return recieverAccount;
    }

    public void setRecieverAccount(Account recieverAccount) {
        this.recieverAccount = recieverAccount;
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

        PrivateMessage that = (PrivateMessage) o;

        if (recieverAccountId != that.recieverAccountId) return false;
        if (senderAccountId != that.senderAccountId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = senderAccountId;
        result = 31 * result + recieverAccountId;
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
        out.writeObject(senderAccount);
        out.writeObject(recieverAccount);
        out.writeObject(text);
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
