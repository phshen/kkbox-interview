package com.peihsuan.kkbox.interview.model;

import java.util.List;

public class VendorInfo {
	private long id;
	private String name;
	private String applicant;
	private String owner;
	private String address;
	private String tel;
	private String fax;
	private String remark;
	private String updateTime;
	
	private List<ContactInfo> contacts;

	public VendorInfo() {
	};
	public VendorInfo(long id, String name, String applicant, String owner, String address, String tel, String fax,
			String remark, String updateTime) {
		this.id = id;
		this.name = name;
		this.applicant = applicant;
		this.owner = owner;
		this.address = address;
		this.tel = tel;
		this.fax = fax;
		this.remark = remark;
		this.updateTime = updateTime;
	}

	public VendorInfo(long id, String name, String applicant, String owner, String address) {
		this.id = id;
		this.name = name;
		this.applicant = applicant;
		this.owner = owner;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<ContactInfo> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactInfo> contacts) {
		this.contacts = contacts;
	}

}
