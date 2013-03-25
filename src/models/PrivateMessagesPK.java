package models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
public class PrivateMessagesPK implements Serializable {
    private int senderUserId;

    @Id
    @Column(name = "senderUserId")
    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    private int recieverUserId;

    @Id
    @Column(name = "recieverUserId")
    public int getRecieverUserId() {
        return recieverUserId;
    }

    public void setRecieverUserId(int recieverUserId) {
        this.recieverUserId = recieverUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateMessagesPK that = (PrivateMessagesPK) o;

        if (recieverUserId != that.recieverUserId) return false;
        if (senderUserId != that.senderUserId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = senderUserId;
        result = 31 * result + recieverUserId;
        return result;
    }
}
