package com.peihsuan.kkbox.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peihsuan.kkbox.interview.entity.ContactInfo;
import com.peihsuan.kkbox.interview.entity.VendorInfo;
import com.peihsuan.kkbox.interview.service.VendorInfoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VendorInfoRestController {

	private final VendorInfoService vendorInfoService;

	@Autowired
	public VendorInfoRestController(VendorInfoService vendorInfoService) {
		this.vendorInfoService = vendorInfoService;
	}

	@GetMapping("/vendors")
	public List<VendorInfo> vendors() {
		return (List<VendorInfo>) vendorInfoService.getVendors();
	}

	@GetMapping("/contacts")
	public List<ContactInfo> contacts() {
		return (List<ContactInfo>) vendorInfoService.getContacts();
	}
}
