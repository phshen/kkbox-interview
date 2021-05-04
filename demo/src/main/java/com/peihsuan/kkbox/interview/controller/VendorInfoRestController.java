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
import org.springframework.web.bind.annotation.RestController;

import com.peihsuan.kkbox.interview.dao.VendorInfoDao;
import com.peihsuan.kkbox.interview.model.VendorInfo;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VendorInfoRestController {

	private final VendorInfoDao vendorInfoDao;

	@Autowired
	public VendorInfoRestController(VendorInfoDao vendorInfoDao) {
		this.vendorInfoDao = vendorInfoDao;
	}

	@GetMapping("/vendors")
	public List<VendorInfo> vendors() {
		return vendorInfoDao.getVendors();
	}

	@PostMapping("/saveVendor")
	public ResponseEntity<?> saveVendor(@RequestBody VendorInfo vendor) {
		try {
			vendorInfoDao.saveVendor(vendor, vendor.getContacts());
		} catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Company ID already exists.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(vendorInfoDao.getVendors());
	}

	@PutMapping("/updateVendor")
	public List<VendorInfo> updateVendor(@RequestBody VendorInfo vendor) {
		vendorInfoDao.updateVendor(vendor);
		return vendorInfoDao.getVendors();
	}

	@DeleteMapping("/deleteVendor/{vendorId}")
	public List<VendorInfo> deleteVendor(@PathVariable("vendorId") long vendorId) {
		vendorInfoDao.deleteVendor(vendorId);
		return vendorInfoDao.getVendors();
	}

}
