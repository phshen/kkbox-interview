package com.peihsuan.kkbox.interview.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContactInfoMapper implements RowMapper<ContactInfo> {
	public ContactInfo mapRowShort(ResultSet rs, int rowNum) throws SQLException {
		ContactInfo contactInfo = new ContactInfo(/*rs.getInt("company_id"), rs.getString("company_name"), */rs.getString("contact_person"),
				rs.getString("title"), rs.getString("contact_number"), rs.getString("email"));
		return contactInfo;
	}

	@Override
	public ContactInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		ContactInfo contactInfo = new ContactInfo(rs.getInt("id"), rs.getLong("company_id"),
				rs.getString("company_name"), rs.getString("contact_person"), rs.getString("title"),
				rs.getString("contact_number"), rs.getString("email"));
		return contactInfo;
	}

}
