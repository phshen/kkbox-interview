package com.peihsuan.kkbox.interview.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.peihsuan.kkbox.interview.entity.VendorInfo;
import com.peihsuan.kkbox.interview.entity.VendorInfoMapper;

@Repository
public class VendorInfoRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public VendorInfoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<VendorInfo> findAll() {
		String sqlQuery = "select * from vendor_info";

		return jdbcTemplate.query(sqlQuery, new VendorInfoMapper());
	}
}
