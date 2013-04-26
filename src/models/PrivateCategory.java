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

    public PrivateCategory(String headline, int colorCode, Category parentPrivateCategory, Account account) {
        this.headline = headline;
        this.colorCode = colorCode;
        this.parentPrivateCategory = parentPrivateCategory;
        this.account = account;
    }

    public PrivateCategory() {
    }

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int colorCode;

    @Column(name = "colorCode")
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

    private String headline;

    @Column(name = "headline")
    @Basic
    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateCategory that = (PrivateCategory) o;

        if (colorCode != that.colorCode) return false;
        if (id != that.id) return false;
        if (headline != null ? !headline.equals(that.headline) : that.headline != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + colorCode;
        result = 31 * result + (headline != null ? headline.hashCode() : 0);
        return result;
    }
}
