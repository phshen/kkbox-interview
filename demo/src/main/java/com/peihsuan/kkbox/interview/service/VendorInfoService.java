package com.peihsuan.kkbox.interview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peihsuan.kkbox.interview.entity.ContactInfo;
import com.peihsuan.kkbox.interview.entity.VendorInfo;
import com.peihsuan.kkbox.interview.repository.ContactInfoRepository;
import com.peihsuan.kkbox.interview.repository.VendorInfoRepository;

@Service
public class VendorInfoService {
	@Autowired
	private VendorInfoRepository vendorInfoRepository;
	@Autowired
	private ContactInfoRepository contactInfoRepository;
	
//	public VendorInfoService(VendorInfoRepository vendorInfoRepository) {
//		this.vendorInfoRepository = vendorInfoRepository;
//	}
//	public VendorInfoService(ContactInfoRepository contactInfoRepository) {
//		this.contactInfoRepository = contactInfoRepository;
//	}
	
	public List<VendorInfo> getVendors() {
		return (List<VendorInfo>) vendorInfoRepository.findAll();
	}
	
	public List<ContactInfo> getContacts() {
		return (List<ContactInfo>) contactInfoRepository.findAll();
	}
}
