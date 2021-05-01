package com.peihsuan.kkbox.interview.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.peihsuan.kkbox.interview.model.VendorInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendorInfoRepositoryTest {
	@Autowired
	private VendorInfoRepository vendorInfoRepository;

	@Test
	public void testFindAll() {
		List<VendorInfo> vendorInfo = vendorInfoRepository.findAll();
	    assertNotNull(vendorInfo);
//	    System.out.println(vendorInfo.get(0).getContacts().get(0));
	    assertEquals(vendorInfo.size(), 2);
	}
	
	@Test
	public void testGetContact() {
		List<VendorInfo> vendorInfo = vendorInfoRepository.findAll();
	    assertNotNull(vendorInfo);
	    assertEquals(vendorInfo.size(), 2);
	}

}
