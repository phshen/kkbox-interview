INSERT INTO vendor_info (id, name, applicant, owner, address, tel, fax, remark, update_time)
VALUES (1234567, 'John Doe', 'John Doe','John Doe','xxxx xxxxxxx','03-12345678','03-12345678','test1', sysdate);
INSERT INTO vendor_info (id, name, applicant, owner, address, tel, fax, remark, update_time)
VALUES (2468001, 'Mary Jackson', 'Mary Jackson', 'Mary Jackson','xxxx xxxxxxx','03-12345678','03-12345678','test2', sysdate);

INSERT INTO contact_info (company_id, company_name, contact_person, title, contact_number, email)
VALUES (1234567, 'John Doe', 'John A','manager','03-12345678','aaa@xx.com');
INSERT INTO contact_info (company_id, company_name, contact_person, title, contact_number, email)
VALUES (1234567, 'John Doe', 'John B','pm','03-12345679','aaa@yy.com');
INSERT INTO contact_info (company_id, company_name, contact_person, title, contact_number, email)
VALUES (2468001, 'Mary Jackson', 'Mary A', 'engineer','03-12345678','bbb@yy.com');