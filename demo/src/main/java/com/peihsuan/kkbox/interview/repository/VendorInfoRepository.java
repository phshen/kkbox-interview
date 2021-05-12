package com.peihsuan.kkbox.interview.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.peihsuan.kkbox.interview.model.ContactInfo;
import com.peihsuan.kkbox.interview.model.ContactInfoMapper;
import com.peihsuan.kkbox.interview.model.VendorInfo;
import com.peihsuan.kkbox.interview.model.VendorInfoMapper;

@Repository
public class VendorInfoRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public VendorInfoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	VendorInfoMapper vendorInfoMapper = new VendorInfoMapper();
	ContactInfoMapper contactInfoMapper = new ContactInfoMapper();
	private final ResultSetExtractor<List<VendorInfo>> resultSetExtractor = new ResultSetExtractor<List<VendorInfo>>() {
		public List<VendorInfo> extractData(ResultSet rs) {
			Map<Long, VendorInfo> vendorInfoMap = new HashMap<Long, VendorInfo>();
			try {
				while (rs.next()) {
					long id = rs.getInt("id");
					ContactInfo contact = contactInfoMapper.mapRow(rs, rs.getRow());
					VendorInfo vendor = vendorInfoMapper.mapRow(rs, rs.getRow());
					vendor.setContacts(new ArrayList<ContactInfo>());
					if (!vendorInfoMap.containsKey(id)) {
						vendorInfoMap.put(id, vendor);
					}
					vendorInfoMap.get(id).getContacts().add(contact);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ArrayList<VendorInfo>(vendorInfoMap.values());
		}
	};

	public List<VendorInfo> findAll() {
		String sqlQuery = "select v.*, c.id as contact_id, c.company_id, c.company_name, c.contact_person, c.title, c.contact_number, c.email from vendor_info v left join contact_info c on v.id = c.company_id";

		return jdbcTemplate.query(sqlQuery, resultSetExtractor);
	}
	
	public List<ContactInfo> findContacts(long companyId) {
		String sqlQuery = "select c.id as contact_id, c.company_id, c.company_name, c.contact_person, c.title, c.contact_number, c.email from contact_info c where company_id = ?";

		return jdbcTemplate.query(sqlQuery, contactInfoMapper, companyId);
	}

	public VendorInfo find(long id) {
		String sqlQuery = "select * from vendor_info v where v.id = ?";
		return jdbcTemplate.queryForObject(sqlQuery, vendorInfoMapper, id);
	}

	public int getVendorCount() {
		String sqlQuery = "select count(*) from vendor_info";

		return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
	}

	public Integer saveVendorInfo(VendorInfo vendorInfo) {
		String sqlQuery = "INSERT INTO vendor_info (id, name, applicant, owner, address, tel, fax, remark) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sqlQuery, 
				vendorInfo.getId(), 
				vendorInfo.getName(), 
				vendorInfo.getApplicant(),
				vendorInfo.getOwner(),
				vendorInfo.getAddress(),
				vendorInfo.getTel(),
				vendorInfo.getFax(),
				vendorInfo.getRemark());
	}

	public int[] saveContacts(List<ContactInfo> contacts, long companyId, String companyName) {
		String sqlQuery = "INSERT INTO contact_info (company_id, company_name, contact_person, title, contact_number, email) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ContactInfo contact = contacts.get(i);
				ps.setLong(1, companyId);
				ps.setString(2, companyName);
				ps.setString(3, contact.getContactPerson());
				ps.setString(4, contact.getTitle());
				ps.setString(5, contact.getContactNumber());
				ps.setString(6, contact.getEmail());
			}

			@Override
			public int getBatchSize() {
				return contacts.size();
			}

		});
	}
	
	public Integer updateVendorInfo(VendorInfo vendorInfo) {
		String sqlQuery = "update vendor_info set applicant = ?, owner = ?, address = ?, tel = ?, fax = ?, remark = ?, update_time = ? where id = ? ";
		return jdbcTemplate.update(sqlQuery, 
				vendorInfo.getApplicant(),
				vendorInfo.getOwner(),
				vendorInfo.getAddress(),
				vendorInfo.getTel(),
				vendorInfo.getFax(),
				vendorInfo.getRemark(),
				new Date(),
				vendorInfo.getId());
	}

	public int[] updateContacts(List<ContactInfo> contacts) {
		String sqlQuery = "update contact_info set contact_person = ?, title = ?, contact_number = ?, email = ? "
				+ "where id = ? and company_id = ?";
		return jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ContactInfo contact = contacts.get(i);
				ps.setString(1, contact.getContactPerson());
				ps.setString(2, contact.getTitle());
				ps.setString(3, contact.getContactNumber());
				ps.setString(4, contact.getEmail());
				ps.setInt(5, contact.getId());
				ps.setLong(6, contact.getCompanyId());
			}

			@Override
			public int getBatchSize() {
				return contacts.size();
			}

		});
	}
	
	public Integer deleteVendorInfo(long id) {
		String sqlQuery = "delete from vendor_info v where v.id = ?";
		return jdbcTemplate.update(sqlQuery, id);
	}

	public Integer deleteContacts(long id) {
		String sqlQuery = "delete from contact_info where company_id = ?";
		return jdbcTemplate.update(sqlQuery, id);
	}

	public int[] deleteContacts(List<ContactInfo> contacts) {
		String sqlQuery = "delete from contact_info where company_id = ? and id = ?";
		return jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ContactInfo contact = contacts.get(i);
				ps.setLong(1, contact.getCompanyId());
				ps.setInt(2, contact.getId());
			}

			@Override
			public int getBatchSize() {
				return contacts.size();
			}

		});
	}
}
