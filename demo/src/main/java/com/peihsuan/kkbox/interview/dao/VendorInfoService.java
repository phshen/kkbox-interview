package com.peihsuan.kkbox.interview.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peihsuan.kkbox.interview.model.ContactInfo;
import com.peihsuan.kkbox.interview.model.VendorInfo;
import com.peihsuan.kkbox.interview.repository.VendorInfoRepository;

@Service
public class VendorInfoService {
	private Logger logger = LogManager.getLogger(VendorInfoService.class.getName());

	private VendorInfoRepository vendorInfoRepository;

	@Autowired
	public VendorInfoService(VendorInfoRepository vendorInfoRepository) {
		this.vendorInfoRepository = vendorInfoRepository;
	}
	
	public List<VendorInfo> getVendors() {
		return vendorInfoRepository.findAll();
	}
	
	public void saveVendor(VendorInfo vendor, List<ContactInfo> contacts) {
		try {
			logger.info("save new vendor: {}, {}", vendor.getId(), vendor.getName());
			vendorInfoRepository.saveVendorInfo(vendor);
			vendorInfoRepository.saveContacts(contacts, vendor.getId(), vendor.getName());
		} catch (Exception e) {
			logger.error("Fail to save vendor.", e);
		}
	}

	public Boolean saveContacts(List<ContactInfo> contacts) {
		try {
			logger.info("number of new contacts: {}", contacts.size());
			int[] updateCount = vendorInfoRepository.saveContacts(contacts, contacts.get(0).getCompanyId(),
					contacts.get(0).getCompanyName());
			if (!updateCount.equals(contacts.size())) {
				throw new Exception("Update count not match.");
			}
		} catch (Exception e) {
			logger.error("Fail to save vendor.", e);
			return false;
		}
		return true;
	}

	public void updateVendor(VendorInfo vendor) {
		try {
			logger.info("update exists vendor: {}, {}", vendor.getId(), vendor.getName());
			vendorInfoRepository.updateVendorInfo(vendor);
		} catch (Exception e) {
			logger.error("Fail to update vendor.", e);
		}
	}

	public Boolean updateContacts(List<ContactInfo> contacts) {
		try {
			logger.info("number of updating exists contacts: {}", contacts.size());
			int[] updateCount = vendorInfoRepository.updateContacts(contacts);
			if (!updateCount.equals(contacts.size())) {
				throw new Exception("Update count not match.");
			}
		} catch (Exception e) {
			logger.error("Fail to save vendor.", e);
			return false;
		}
		return true;
	}

	public void deleteVendor(long vendorId) {
		try {
			logger.info("delete exists vendor: {}", vendorId);
			vendorInfoRepository.deleteVendorInfo(vendorId);
		} catch (Exception e) {
			logger.error("Fail to delete vendor.", e);
		}
	}

	public Boolean deleteContacts(List<ContactInfo> contacts) {
		try {
			logger.info("number of deleting exists contacts: {}", contacts.size());
			int[] deleteCount = vendorInfoRepository.deleteContacts(contacts);
			if (!deleteCount.equals(contacts.size())) {
				throw new Exception("Delete count not match.");
			}
		} catch (Exception e) {
			logger.error("Fail to delete vendor.", e);
			return false;
		}
		return true;
	}

	public List<ContactInfo> getContacts(long companyId) {
		return vendorInfoRepository.findContacts(companyId);
	}

}
