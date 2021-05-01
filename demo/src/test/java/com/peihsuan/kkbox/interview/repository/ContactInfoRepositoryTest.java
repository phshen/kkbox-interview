package com.peihsuan.kkbox.interview.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.peihsuan.kkbox.interview.model.ContactInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactInfoRepositoryTest {

	@Autowired
	private ContactInfoRepository contactInfoRepository;

	@Test
	public void testFindAll() {
		List<ContactInfo> contactInfo = contactInfoRepository.findAll();
		assertNotNull(contactInfo);
		assertEquals(contactInfo.size(), 3);
	}

}
