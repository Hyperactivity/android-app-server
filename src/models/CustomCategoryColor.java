package models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@javax.persistence.IdClass(models.CustomCategoryColorPK.class)
@Entity
public class CustomCategoryColor{
    private int accountId;
    private int categoryId;
    private int colorCode;
    private Account account;
    private Category category;

    public CustomCategoryColor(int accountId, int categoryId, int colorCode) {
        setAccountId(accountId);
        setCategoryId(categoryId);
        setColorCode(colorCode);
    }

    @Deprecated
    public CustomCategoryColor() {
    }

    @javax.persistence.Column(name = "accountId")
    @Id
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @javax.persistence.Column(name = "categoryId")
    @Id
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @javax.persistence.Column(name = "colorCode")
    @Basic
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomCategoryColor that = (CustomCategoryColor) o;

        if (accountId != that.accountId) return false;
        if (categoryId != that.categoryId) return false;
        if (colorCode != that.colorCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + categoryId;
        result = 31 * result + colorCode;
        return result;
    }

     @ManyToOne
     @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
     public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @OneToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
