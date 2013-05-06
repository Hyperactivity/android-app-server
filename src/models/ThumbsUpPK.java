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
public class ThumbsUpPK implements Serializable {
    private int replyId;
    private int accountId;

    public ThumbsUpPK(int replyId, int accountId) {
        setReplyId(replyId);
        setAccountId(accountId);
    }

    @Deprecated
    public ThumbsUpPK() {
    }

    @Id
    @Column(name = "replyId")
    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    @Id
    @Column(name = "accountId")
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThumbsUpPK that = (ThumbsUpPK) o;

        if (accountId != that.accountId) return false;
        if (replyId != that.replyId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = replyId;
        result = 31 * result + accountId;
        return result;
    }
}
