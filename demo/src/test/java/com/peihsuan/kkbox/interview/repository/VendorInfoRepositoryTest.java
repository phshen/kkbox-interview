package com.peihsuan.kkbox.interview.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.peihsuan.kkbox.interview.model.ContactInfo;
import com.peihsuan.kkbox.interview.model.VendorInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VendorInfoRepositoryTest {
	private Logger logger = LogManager.getLogger(VendorInfoRepositoryTest.class.getName());
	@Autowired
	private VendorInfoRepository vendorInfoRepository;

	@Test
	public void testFindAll() {
		logger.info("find all");
		List<VendorInfo> vendorInfo = vendorInfoRepository.findAll();
	    assertNotNull(vendorInfo);
//	    System.out.println(vendorInfo.get(0).getContacts().get(0));
	    assertEquals(vendorInfo.size(), 2);
	}
	
	@Test
	public void testSaveVendorInfo() {
		try {
			int vendorBefore = vendorInfoRepository.getVendorCount();
			int contactBefore = vendorInfoRepository.findContacts(99999999).size();
			assertEquals(contactBefore, 0);
			VendorInfo vendorInfo = new VendorInfo(99999999, "test_name", "test", "test", "test");
			vendorInfoRepository.saveVendorInfo(vendorInfo);
			List<ContactInfo> contacts = new ArrayList<>();
			contacts.add(new ContactInfo("contact1", "yoyo", "1234", "eee@eee.com"));
			contacts.add(new ContactInfo("contact2", "yoyo", "5678", "vvv@eee.com"));
			int[] batchInsert = vendorInfoRepository.saveContacts(contacts, 99999999, "test_name");
			assertEquals(batchInsert.length, contacts.size());
			int vendorAfter = vendorInfoRepository.getVendorCount();
			int contactAfter = vendorInfoRepository.findContacts(12345678).size();
			assertEquals(vendorAfter - vendorBefore, 1);
			assertEquals(contactAfter - contactBefore, contacts.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateVendorInfo() {
		try {
			VendorInfo vendor = vendorInfoRepository.find(12345678);
			assertEquals(vendor.getApplicant(), "John Doe");
			vendor.setApplicant("testChange");
			assertTrue(vendorInfoRepository.updateVendorInfo(vendor) == 1);
			vendor = vendorInfoRepository.find(12345678);
			assertEquals(vendor.getApplicant(), "testChange");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateContactInfo() {
		try {
			List<ContactInfo> contacts = vendorInfoRepository.findContacts(12345678);
			assertEquals(contacts.get(0).getContactPerson(), "John A");
			assertEquals(contacts.get(1).getContactPerson(), "John B");
			contacts.get(0).setContactPerson("contact1");
			contacts.get(1).setContactPerson("contact2");

			int[] update = vendorInfoRepository.updateContacts(contacts);
			assertEquals(update.length, contacts.size());

			contacts = vendorInfoRepository.findContacts(12345678);
			assertEquals(contacts.get(0).getContactPerson(), "contact1");
			assertEquals(contacts.get(1).getContactPerson(), "contact2");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteVendorInfo() {
		try {
			assertEquals(vendorInfoRepository.getVendorCount(), 2);
			assertEquals(vendorInfoRepository.findContacts(12345678).size(), 2);
			int deleteCount = vendorInfoRepository.deleteVendorInfo(12345678);
			assertEquals(deleteCount, 1);
			assertEquals(vendorInfoRepository.getVendorCount(), 1);
			assertEquals(vendorInfoRepository.findContacts(12345678).size(), 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteContactInfo() {
		try {
			assertEquals(vendorInfoRepository.findContacts(12345678).size(), 2);
			List<ContactInfo> toDelete = new ArrayList<>();
			toDelete.add(vendorInfoRepository.findContacts(12345678).get(1));
			int[] deleteCount = vendorInfoRepository.deleteContacts(toDelete);
			assertEquals(deleteCount.length, 1);
			assertEquals(vendorInfoRepository.findContacts(12345678).size(), 1);
			assertEquals(vendorInfoRepository.findContacts(12345678).get(0).getId(), Integer.valueOf(1));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
