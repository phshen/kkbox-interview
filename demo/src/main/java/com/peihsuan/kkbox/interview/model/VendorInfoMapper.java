package com.peihsuan.kkbox.interview.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

public class VendorInfoMapper implements RowMapper<VendorInfo> {
	public VendorInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		VendorInfo vendorInfo = new VendorInfo(rs.getInt("id"), rs.getString("name"), rs.getString("applicant"),
				rs.getString("owner"), rs.getString("address"), rs.getString("tel"), rs.getString("fax"),
				rs.getString("remark"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("update_time")));
		return vendorInfo;
	}
}
