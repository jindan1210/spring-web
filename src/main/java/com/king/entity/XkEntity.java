package com.king.entity;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by 金丹 on 2017/12/19.
 */
public class XkEntity {
    private String cardNo;
    private String customerName;
    private String type;
    private Date times;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(250);
        sb.append("=\"").append(customerName).append("\",");
        sb.append("=\"").append(cardNo).append("\",");
        String time1 = DateFormatUtils.format(times, "yyyy-MM-dd HH:mm:ss");
        sb.append("=\"").append(time1).append("\",");
        String tradType = null;
        switch (type) {
            case "MOBILEQG":
                tradType = "移动";
                break;
            case "UNICOMQG":
                tradType = "联通";
                break;
            case "TELECOMQG":
                tradType = "电信";
                break;
            default:
                break;
        }
        sb.append(tradType);
        return sb.toString();
    }
}
