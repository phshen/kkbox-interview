package com.peihsuan.kkbox.interview.model;

public class ContactInfo {
	private Integer id;
	private Long companyId;
	private String companyName;
	private String contactPerson;
	private String title;
	private String contactNumber;
	private String email;

	public ContactInfo() {
	};
	public ContactInfo(String contactPerson, String title, String contactNumber,
			String email) {
		this.contactPerson = contactPerson;
		this.title = title;
		this.contactNumber = contactNumber;
		this.email = email;
	}

	// public ContactInfo(Long companyId, String companyName, String
	// contactPerson, String title,
	// String contactNumber, String email) {
	// this.companyId = companyId;
	// this.companyName = companyName;
	// this.contactPerson = contactPerson;
	// this.title = title;
	// this.contactNumber = contactNumber;
	// this.email = email;
	// }

	public ContactInfo(Integer id, Long companyId, String companyName, String contactPerson, String title,
			String contactNumber, String email) {
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.contactPerson = contactPerson;
		this.title = title;
		this.contactNumber = contactNumber;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
