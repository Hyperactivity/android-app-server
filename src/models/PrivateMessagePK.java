package models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class PrivateMessagePK implements Serializable {
    private int senderAccountId;

    @Id
    @Column(name = "senderAccountId")
    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    private int recieverAccountId;

    @Id
    @Column(name = "recieverAccountId")
    public int getRecieverAccountId() {
        return recieverAccountId;
    }

    public void setRecieverAccountId(int recieverAccountId) {
        this.recieverAccountId = recieverAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateMessagePK that = (PrivateMessagePK) o;

        if (recieverAccountId != that.recieverAccountId) return false;
        if (senderAccountId != that.senderAccountId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = senderAccountId;
        result = 31 * result + recieverAccountId;
        return result;
    }
}
