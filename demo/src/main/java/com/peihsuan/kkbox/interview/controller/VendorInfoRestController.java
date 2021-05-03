package com.peihsuan.kkbox.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<ContactInfo> contactsById(@RequestParam("companyId") long companyId) {
		return vendorInfoService.getContacts(companyId);
	}

	@PostMapping("/saveVendor")
	public ResponseEntity<?> saveVendor(@RequestBody VendorInfo vendor) {
		try {
			vendorInfoService.saveVendor(vendor, vendor.getContacts());
		} catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Company ID already exists.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(vendorInfoService.getVendors());
	}

	// @PostMapping("/saveContacts")
	// public Integer saveContacts(@RequestBody List<ContactInfo> contacts) {
	// vendorInfoService.saveContacts(contacts);
	// return contacts.size();
	// }

	@PutMapping("/updateVendor")
	public List<VendorInfo> updateVendor(@RequestBody VendorInfo vendor) {
		vendorInfoService.updateVendor(vendor);
		return vendorInfoService.getVendors();
	}

	@PutMapping("/updateContacts")
	public List<VendorInfo> updateContacts(@RequestBody List<ContactInfo> contacts) {
		vendorInfoService.updateContacts(contacts);
		return vendorInfoService.getVendors();
	}

	@DeleteMapping("/deleteVendor/{vendorId}")
	public List<VendorInfo> deleteVendor(@PathVariable("vendorId") long vendorId) {
		vendorInfoService.deleteVendor(vendorId);
		return vendorInfoService.getVendors();
	}

	@PutMapping("/deleteContacts")
	public List<VendorInfo> deleteContacts(@RequestBody List<ContactInfo> contacts) {
		vendorInfoService.deleteContacts(contacts);
		return vendorInfoService.getVendors();
	}
}
