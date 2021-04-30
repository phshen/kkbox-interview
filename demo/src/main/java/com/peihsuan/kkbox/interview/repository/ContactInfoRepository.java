package com.peihsuan.kkbox.interview.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.peihsuan.kkbox.interview.entity.ContactInfo;
import com.peihsuan.kkbox.interview.entity.ContactInfoMapper;

@Repository
public class ContactInfoRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ContactInfoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ContactInfo> findAll() {
		String sqlQuery = "select company_id, company_name, contact_person, title, contact_number, email from contact_info";

		return jdbcTemplate.query(sqlQuery, new ContactInfoMapper());
	}

}
