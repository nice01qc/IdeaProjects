package com.bjsxt.pojo;

import java.io.Serializable;

public class Admin implements Serializable {
    /** 用户id  admin_id **/
    private Short adminId;

    /** 用户名  user_name **/
    private String userName;

    /** email  email **/
    private String email;

    /** 密码  password **/
    private String password;

    /** 秘钥  ec_salt **/
    private String ecSalt;

    /** 添加时间  add_time **/
    private Integer addTime;

    /** 最后登录时间  last_login **/
    private Integer lastLogin;

    /** 最后登录ip  last_ip **/
    private String lastIp;

    /** lang_type  lang_type **/
    private String langType;

    /** agency_id  agency_id **/
    private Short agencyId;

    /** suppliers_id  suppliers_id **/
    private Short suppliersId;

    /** 角色id  role_id **/
    private Short roleId;

    /**   tableName: t_admin   **/
    private static final long serialVersionUID = 1L;

    /**   用户id  admin_id   **/
    public Short getAdminId() {
        return adminId;
    }

    /**   用户id  admin_id   **/
    public void setAdminId(Short adminId) {
        this.adminId = adminId;
    }

    /**   用户名  user_name   **/
    public String getUserName() {
        return userName;
    }

    /**   用户名  user_name   **/
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**   email  email   **/
    public String getEmail() {
        return email;
    }

    /**   email  email   **/
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**   密码  password   **/
    public String getPassword() {
        return password;
    }

    /**   密码  password   **/
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**   秘钥  ec_salt   **/
    public String getEcSalt() {
        return ecSalt;
    }

    /**   秘钥  ec_salt   **/
    public void setEcSalt(String ecSalt) {
        this.ecSalt = ecSalt == null ? null : ecSalt.trim();
    }

    /**   添加时间  add_time   **/
    public Integer getAddTime() {
        return addTime;
    }

    /**   添加时间  add_time   **/
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**   最后登录时间  last_login   **/
    public Integer getLastLogin() {
        return lastLogin;
    }

    /**   最后登录时间  last_login   **/
    public void setLastLogin(Integer lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**   最后登录ip  last_ip   **/
    public String getLastIp() {
        return lastIp;
    }

    /**   最后登录ip  last_ip   **/
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    /**   lang_type  lang_type   **/
    public String getLangType() {
        return langType;
    }

    /**   lang_type  lang_type   **/
    public void setLangType(String langType) {
        this.langType = langType == null ? null : langType.trim();
    }

    /**   agency_id  agency_id   **/
    public Short getAgencyId() {
        return agencyId;
    }

    /**   agency_id  agency_id   **/
    public void setAgencyId(Short agencyId) {
        this.agencyId = agencyId;
    }

    /**   suppliers_id  suppliers_id   **/
    public Short getSuppliersId() {
        return suppliersId;
    }

    /**   suppliers_id  suppliers_id   **/
    public void setSuppliersId(Short suppliersId) {
        this.suppliersId = suppliersId;
    }

    /**   角色id  role_id   **/
    public Short getRoleId() {
        return roleId;
    }

    /**   角色id  role_id   **/
    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", adminId=").append(adminId);
        sb.append(", userName=").append(userName);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", ecSalt=").append(ecSalt);
        sb.append(", addTime=").append(addTime);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", lastIp=").append(lastIp);
        sb.append(", langType=").append(langType);
        sb.append(", agencyId=").append(agencyId);
        sb.append(", suppliersId=").append(suppliersId);
        sb.append(", roleId=").append(roleId);
        sb.append("]");
        return sb.toString();
    }
}