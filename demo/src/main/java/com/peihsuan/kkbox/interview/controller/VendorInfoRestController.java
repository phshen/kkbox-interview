package com.peihsuan.kkbox.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peihsuan.kkbox.interview.dao.VendorInfoService;
import com.peihsuan.kkbox.interview.model.ContactInfo;
import com.peihsuan.kkbox.interview.model.VendorInfo;

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
		return vendorInfoService.getVendors();
	}

	@GetMapping("/contacts")
	public List<ContactInfo> contacts() {
		return vendorInfoService.getContacts();
	}
}
