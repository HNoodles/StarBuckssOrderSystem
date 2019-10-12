package fudan.se.lab4.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaymentInfo implements Serializable {
    private static final long serialVersionUID = -5743364759168621824L;
    private double price;
    private double discount;
    private double discountPrice;
    private List<String> goodsInfoList = new ArrayList<>();
    private List<String> msgs;

    public PaymentInfo(double price, double discount, double discountPrice, List<String> msgs) {
        this.price = price;
        this.discount = discount;
        this.discountPrice = discountPrice;
        this.msgs = msgs;
    }

    public PaymentInfo() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }

    public void addGoodsInfo(String info) {
        goodsInfoList.add(info);
    }

//    public List<String> getGoodsInfoList() {
//        return goodsInfoList;
//    }

    public String getGoodsInfoString() {
        StringBuilder s = new StringBuilder();
        for (String info : goodsInfoList) {
            s.append(info).append("\n");
        }
        if (goodsInfoList.size() > 0)
            s.deleteCharAt(s.length() - 1); // delete the last char '\n'
        return s.toString();
    }
}
