package models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class PrivateCategory implements Serializable {
    static final long serialVersionUID = 6L;

    private int id;

    public PrivateCategory(int id, int colorCode, Category parentPrivateCategory, Account account) {
        this.id = id;
        this.colorCode = colorCode;
        this.parentPrivateCategory = parentPrivateCategory;
        this.account = account;
    }

    public PrivateCategory() {
    }

    @javax.persistence.Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int colorCode;

    @javax.persistence.Column(name = "colorCode")
    @Basic
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    private Category parentPrivateCategory;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parentPrivateCategoryId", referencedColumnName = "id", nullable = false)
    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public void setParentPrivateCategory(Category parentPrivateCategory) {
        this.parentPrivateCategory = parentPrivateCategory;
    }

    private Account account;

    @ManyToOne(fetch=FetchType.LAZY)
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

        PrivateCategory that = (PrivateCategory) o;

        if (colorCode != that.colorCode) return false;
        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + colorCode;
        return result;
    }
}
