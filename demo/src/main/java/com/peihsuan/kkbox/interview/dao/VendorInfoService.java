package com.peihsuan.kkbox.interview.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.peihsuan.kkbox.interview.model.ContactInfo;
import com.peihsuan.kkbox.interview.model.VendorInfo;
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
		return vendorInfoRepository.findAll();
	}
	
	public List<ContactInfo> getContacts() {
		return contactInfoRepository.findAll();
	}
}
