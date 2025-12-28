package quru.qa.model;

public class Prices {
    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCpd_promo_price() {
        return cpd_promo_price;
    }

    public void setCpd_promo_price(String cpd_promo_price) {
        this.cpd_promo_price = cpd_promo_price;
    }

    private String regular;
    private String discount;
    private String cpd_promo_price;
}
