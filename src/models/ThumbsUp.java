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
public class ThumbsUp {
    private transient int replyId;
    private transient int accountId;
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

    @ManyToOne
    @JoinColumn(name = "replyId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    @ManyToOne
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
}
