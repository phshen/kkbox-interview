package com.peihsuan.kkbox.interview.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

	// private final ResultSetExtractor<List<VendorInfo>> resultSetExtractor =
	// JdbcTemplateMapperFactory.newInstance()
	// .addKeys("id") // the column name you expect the user id to be on
	// .newResultSetExtractor(VendorInfo.class);
//	private final ResultSetExtractorImpl<Pair<VendorInfo, List<ContactInfo>>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
//			.addKeys("id", "compid") // the column name you expect the user id to be on
//			.unorderedJoin()
//			.newResultSetExtractor(new TypeReference<Pair<VendorInfo, List<ContactInfo>>>(){});

	VendorInfoMapper vendorInfoMapper = new VendorInfoMapper();
	ContactInfoMapper contactInfoMapper = new ContactInfoMapper();
	private final ResultSetExtractor<List<VendorInfo>> resultSetExtractor = new ResultSetExtractor<List<VendorInfo>>() {
		public List<VendorInfo> extractData(ResultSet rs) {
			Map<Long, VendorInfo> vendorInfoMap = new HashMap<Long, VendorInfo>();
			try {
				while (rs.next()) {
					long id = rs.getInt("id");
					ContactInfo contact = contactInfoMapper.mapRow(rs, rs.getRow());
					if (!vendorInfoMap.containsKey(id)) {
						VendorInfo vendor = vendorInfoMapper.mapRow(rs, rs.getRow());
						vendor.setContacts(new ArrayList<ContactInfo>());
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
		String sqlQuery = "select v.*, c.contact_person, c.title, c.contact_number, c.email from vendor_info v left join contact_info c on v.id = c.company_id";

		return jdbcTemplate.query(sqlQuery, resultSetExtractor);
	}
}
