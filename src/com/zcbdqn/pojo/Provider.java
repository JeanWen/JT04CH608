package com.zcbdqn.pojo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.Date;

public class Provider {
    private Integer id;
    @NotBlank(message = "供应商编码不能为空")
    private String proCode;
    @NotBlank(message = "供应商名字不能为空")
    private String proName;
    private String proDesc;
    @NotBlank(message = "联系人不能为空")
    private String proContact;
    @Pattern(regexp = "^1[3,5,6,7,8,9]\\d{9}$",message = "手机号码格式不正确")
    private String proPhone;
    private String proFax;
    private String proAddress;
    private Integer createdBy;
    private Integer modifyBy;
    private Date creationDate;
    private Date modifyDate;
    private String comPicPath;
    private String orgPicPath;

    public String getComPicPath() {
        return comPicPath;
    }

    public void setComPicPath(String comPicPath) {
        this.comPicPath = comPicPath;
    }

    public String getOrgPicPath() {
        return orgPicPath;
    }

    public void setOrgPicPath(String orgPicPath) {
        this.orgPicPath = orgPicPath;
    }

    public Provider() {
    }

    public Provider(Integer id, String proCode, String proName, String proDesc, String proContact, String proPhone, String proFax, String proAddress, Integer createdBy, Integer modifyBy, Date creationDate, Date modifyDate) {
        this.id = id;
        this.proCode = proCode;
        this.proName = proName;
        this.proDesc = proDesc;
        this.proContact = proContact;
        this.proPhone = proPhone;
        this.proFax = proFax;
        this.proAddress = proAddress;
        this.createdBy = createdBy;
        this.modifyBy = modifyBy;
        this.creationDate = creationDate;
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", proCode='" + proCode + '\'' +
                ", proName='" + proName + '\'' +
                ", proDesc='" + proDesc + '\'' +
                ", proContact='" + proContact + '\'' +
                ", proPhone='" + proPhone + '\'' +
                ", proFax='" + proFax + '\'' +
                ", proAddress='" + proAddress + '\'' +
                ", createdBy=" + createdBy +
                ", modifyBy=" + modifyBy +
                ", creationDate=" + creationDate +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public String getProContact() {
        return proContact;
    }

    public void setProContact(String proContact) {
        this.proContact = proContact;
    }

    public String getProPhone() {
        return proPhone;
    }

    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }

    public String getProFax() {
        return proFax;
    }

    public void setProFax(String proFax) {
        this.proFax = proFax;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
