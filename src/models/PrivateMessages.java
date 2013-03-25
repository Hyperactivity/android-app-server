package models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@javax.persistence.IdClass(models.PrivateMessagesPK.class)
@Entity
public class PrivateMessages {
    private int senderUserId;

    @javax.persistence.Column(name = "senderUserId")
    @Id
    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    private int recieverUserId;

    @javax.persistence.Column(name = "recieverUserId")
    @Id
    public int getRecieverUserId() {
        return recieverUserId;
    }

    public void setRecieverUserId(int recieverUserId) {
        this.recieverUserId = recieverUserId;
    }

    private String messageText;

    @javax.persistence.Column(name = "messageText")
    @Basic
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateMessages that = (PrivateMessages) o;

        if (recieverUserId != that.recieverUserId) return false;
        if (senderUserId != that.senderUserId) return false;
        if (messageText != null ? !messageText.equals(that.messageText) : that.messageText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = senderUserId;
        result = 31 * result + recieverUserId;
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        return result;
    }
}
