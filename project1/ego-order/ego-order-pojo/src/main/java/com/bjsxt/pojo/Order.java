package com.bjsxt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
    /** 订单id  order_id **/
    private Integer orderId;

    /** 订单编号  order_sn **/
    private String orderSn;

    /** 用户id  user_id **/
    private Integer userId;

    /** 订单状态  order_status **/
    private Byte orderStatus;

    /** 发货状态  shipping_status **/
    private Byte shippingStatus;

    /** 支付状态  pay_status **/
    private Byte payStatus;

    /** 收货人  consignee **/
    private String consignee;

    /** 国家  country **/
    private Integer country;

    /** 省份  province **/
    private Integer province;

    /** 城市  city **/
    private Integer city;

    /** 县区  district **/
    private Integer district;

    /** 乡镇  twon **/
    private Integer twon;

    /** 地址  address **/
    private String address;

    /** 邮政编码  zipcode **/
    private String zipcode;

    /** 手机  mobile **/
    private String mobile;

    /** 邮件  email **/
    private String email;

    /** 物流code  shipping_code **/
    private String shippingCode;

    /** 物流名称  shipping_name **/
    private String shippingName;

    /** 支付code  pay_code **/
    private String payCode;

    /** 支付方式名称  pay_name **/
    private String payName;

    /** 发票抬头  invoice_title **/
    private String invoiceTitle;

    /** 商品总价  goods_price **/
    private BigDecimal goodsPrice;

    /** 邮费  shipping_price **/
    private BigDecimal shippingPrice;

    /** 使用余额  user_money **/
    private BigDecimal userMoney;

    /** 优惠券抵扣  coupon_price **/
    private BigDecimal couponPrice;

    /** 使用积分  integral **/
    private Integer integral;

    /** 使用积分抵多少钱  integral_money **/
    private BigDecimal integralMoney;

    /** 应付款金额  order_amount **/
    private BigDecimal orderAmount;

    /** 订单总价  total_amount **/
    private BigDecimal totalAmount;

    /** 下单时间  add_time **/
    private Integer addTime;

    /** 最后新发货时间  shipping_time **/
    private Integer shippingTime;

    /** 收货确认时间  confirm_time **/
    private Integer confirmTime;

    /** 支付时间  pay_time **/
    private Integer payTime;

    /** 活动id  order_prom_id **/
    private Short orderPromId;

    /** 活动优惠金额  order_prom_amount **/
    private BigDecimal orderPromAmount;

    /** 价格调整  discount **/
    private BigDecimal discount;

    /** 用户备注  user_note **/
    private String userNote;

    /** 管理员备注  admin_note **/
    private String adminNote;

    /** 父单单号  parent_sn **/
    private String parentSn;

    /** 是否已分成0未分成1已分成  is_distribut **/
    private Byte isDistribut;

    /**   tableName: t_order   **/
    private static final long serialVersionUID = 1L;

    /**   订单id  order_id   **/
    public Integer getOrderId() {
        return orderId;
    }

    /**   订单id  order_id   **/
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**   订单编号  order_sn   **/
    public String getOrderSn() {
        return orderSn;
    }

    /**   订单编号  order_sn   **/
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    /**   用户id  user_id   **/
    public Integer getUserId() {
        return userId;
    }

    /**   用户id  user_id   **/
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**   订单状态  order_status   **/
    public Byte getOrderStatus() {
        return orderStatus;
    }

    /**   订单状态  order_status   **/
    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**   发货状态  shipping_status   **/
    public Byte getShippingStatus() {
        return shippingStatus;
    }

    /**   发货状态  shipping_status   **/
    public void setShippingStatus(Byte shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    /**   支付状态  pay_status   **/
    public Byte getPayStatus() {
        return payStatus;
    }

    /**   支付状态  pay_status   **/
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    /**   收货人  consignee   **/
    public String getConsignee() {
        return consignee;
    }

    /**   收货人  consignee   **/
    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    /**   国家  country   **/
    public Integer getCountry() {
        return country;
    }

    /**   国家  country   **/
    public void setCountry(Integer country) {
        this.country = country;
    }

    /**   省份  province   **/
    public Integer getProvince() {
        return province;
    }

    /**   省份  province   **/
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**   城市  city   **/
    public Integer getCity() {
        return city;
    }

    /**   城市  city   **/
    public void setCity(Integer city) {
        this.city = city;
    }

    /**   县区  district   **/
    public Integer getDistrict() {
        return district;
    }

    /**   县区  district   **/
    public void setDistrict(Integer district) {
        this.district = district;
    }

    /**   乡镇  twon   **/
    public Integer getTwon() {
        return twon;
    }

    /**   乡镇  twon   **/
    public void setTwon(Integer twon) {
        this.twon = twon;
    }

    /**   地址  address   **/
    public String getAddress() {
        return address;
    }

    /**   地址  address   **/
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**   邮政编码  zipcode   **/
    public String getZipcode() {
        return zipcode;
    }

    /**   邮政编码  zipcode   **/
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    /**   手机  mobile   **/
    public String getMobile() {
        return mobile;
    }

    /**   手机  mobile   **/
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**   邮件  email   **/
    public String getEmail() {
        return email;
    }

    /**   邮件  email   **/
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**   物流code  shipping_code   **/
    public String getShippingCode() {
        return shippingCode;
    }

    /**   物流code  shipping_code   **/
    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode == null ? null : shippingCode.trim();
    }

    /**   物流名称  shipping_name   **/
    public String getShippingName() {
        return shippingName;
    }

    /**   物流名称  shipping_name   **/
    public void setShippingName(String shippingName) {
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }

    /**   支付code  pay_code   **/
    public String getPayCode() {
        return payCode;
    }

    /**   支付code  pay_code   **/
    public void setPayCode(String payCode) {
        this.payCode = payCode == null ? null : payCode.trim();
    }

    /**   支付方式名称  pay_name   **/
    public String getPayName() {
        return payName;
    }

    /**   支付方式名称  pay_name   **/
    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    /**   发票抬头  invoice_title   **/
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**   发票抬头  invoice_title   **/
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    /**   商品总价  goods_price   **/
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**   商品总价  goods_price   **/
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**   邮费  shipping_price   **/
    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    /**   邮费  shipping_price   **/
    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    /**   使用余额  user_money   **/
    public BigDecimal getUserMoney() {
        return userMoney;
    }

    /**   使用余额  user_money   **/
    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

    /**   优惠券抵扣  coupon_price   **/
    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    /**   优惠券抵扣  coupon_price   **/
    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    /**   使用积分  integral   **/
    public Integer getIntegral() {
        return integral;
    }

    /**   使用积分  integral   **/
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**   使用积分抵多少钱  integral_money   **/
    public BigDecimal getIntegralMoney() {
        return integralMoney;
    }

    /**   使用积分抵多少钱  integral_money   **/
    public void setIntegralMoney(BigDecimal integralMoney) {
        this.integralMoney = integralMoney;
    }

    /**   应付款金额  order_amount   **/
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**   应付款金额  order_amount   **/
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**   订单总价  total_amount   **/
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**   订单总价  total_amount   **/
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**   下单时间  add_time   **/
    public Integer getAddTime() {
        return addTime;
    }

    /**   下单时间  add_time   **/
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**   最后新发货时间  shipping_time   **/
    public Integer getShippingTime() {
        return shippingTime;
    }

    /**   最后新发货时间  shipping_time   **/
    public void setShippingTime(Integer shippingTime) {
        this.shippingTime = shippingTime;
    }

    /**   收货确认时间  confirm_time   **/
    public Integer getConfirmTime() {
        return confirmTime;
    }

    /**   收货确认时间  confirm_time   **/
    public void setConfirmTime(Integer confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**   支付时间  pay_time   **/
    public Integer getPayTime() {
        return payTime;
    }

    /**   支付时间  pay_time   **/
    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    /**   活动id  order_prom_id   **/
    public Short getOrderPromId() {
        return orderPromId;
    }

    /**   活动id  order_prom_id   **/
    public void setOrderPromId(Short orderPromId) {
        this.orderPromId = orderPromId;
    }

    /**   活动优惠金额  order_prom_amount   **/
    public BigDecimal getOrderPromAmount() {
        return orderPromAmount;
    }

    /**   活动优惠金额  order_prom_amount   **/
    public void setOrderPromAmount(BigDecimal orderPromAmount) {
        this.orderPromAmount = orderPromAmount;
    }

    /**   价格调整  discount   **/
    public BigDecimal getDiscount() {
        return discount;
    }

    /**   价格调整  discount   **/
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**   用户备注  user_note   **/
    public String getUserNote() {
        return userNote;
    }

    /**   用户备注  user_note   **/
    public void setUserNote(String userNote) {
        this.userNote = userNote == null ? null : userNote.trim();
    }

    /**   管理员备注  admin_note   **/
    public String getAdminNote() {
        return adminNote;
    }

    /**   管理员备注  admin_note   **/
    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote == null ? null : adminNote.trim();
    }

    /**   父单单号  parent_sn   **/
    public String getParentSn() {
        return parentSn;
    }

    /**   父单单号  parent_sn   **/
    public void setParentSn(String parentSn) {
        this.parentSn = parentSn == null ? null : parentSn.trim();
    }

    /**   是否已分成0未分成1已分成  is_distribut   **/
    public Byte getIsDistribut() {
        return isDistribut;
    }

    /**   是否已分成0未分成1已分成  is_distribut   **/
    public void setIsDistribut(Byte isDistribut) {
        this.isDistribut = isDistribut;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", userId=").append(userId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", shippingStatus=").append(shippingStatus);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", consignee=").append(consignee);
        sb.append(", country=").append(country);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", twon=").append(twon);
        sb.append(", address=").append(address);
        sb.append(", zipcode=").append(zipcode);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", shippingCode=").append(shippingCode);
        sb.append(", shippingName=").append(shippingName);
        sb.append(", payCode=").append(payCode);
        sb.append(", payName=").append(payName);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", shippingPrice=").append(shippingPrice);
        sb.append(", userMoney=").append(userMoney);
        sb.append(", couponPrice=").append(couponPrice);
        sb.append(", integral=").append(integral);
        sb.append(", integralMoney=").append(integralMoney);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", addTime=").append(addTime);
        sb.append(", shippingTime=").append(shippingTime);
        sb.append(", confirmTime=").append(confirmTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", orderPromId=").append(orderPromId);
        sb.append(", orderPromAmount=").append(orderPromAmount);
        sb.append(", discount=").append(discount);
        sb.append(", userNote=").append(userNote);
        sb.append(", adminNote=").append(adminNote);
        sb.append(", parentSn=").append(parentSn);
        sb.append(", isDistribut=").append(isDistribut);
        sb.append("]");
        return sb.toString();
    }
}