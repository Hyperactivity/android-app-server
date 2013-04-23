package models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@javax.persistence.IdClass(models.PrivateMessagePK.class)
@Entity
public class PrivateMessage implements Serializable {
    static final long serialVersionUID = 7L;

    private int senderAccountId;

    public PrivateMessage(int senderAccountId, int recieverAccountId, String text) {
        this.senderAccountId = senderAccountId;
        this.recieverAccountId = recieverAccountId;
        this.text = text;
    }

    @Deprecated
    public PrivateMessage() {
    }

    @javax.persistence.Column(name = "senderAccountId")
    @Id
    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    private int recieverAccountId;

    @javax.persistence.Column(name = "recieverAccountId")
    @Id
    public int getRecieverAccountId() {
        return recieverAccountId;
    }

    public void setRecieverAccountId(int recieverAccountId) {
        this.recieverAccountId = recieverAccountId;
    }

    private String text;

    @javax.persistence.Column(name = "text")
    @Basic
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateMessage that = (PrivateMessage) o;

        if (recieverAccountId != that.recieverAccountId) return false;
        if (senderAccountId != that.senderAccountId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = senderAccountId;
        result = 31 * result + recieverAccountId;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
