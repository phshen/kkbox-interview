CREATE TABLE vendor_info (
  id bigint(20) NOT NULL PRIMARY KEY,
  name varchar(100) NOT NULL,
  applicant varchar(100) not null,
  owner varchar(100) not null,
  address varchar(1000) not null,
  tel varchar(100),
  fax varchar(100),
  remark varchar(100),
  update_time date default sysdate
);

CREATE TABLE contact_info (
  id int auto_increment PRIMARY KEY,
  company_id bigint(20) not null,
  company_name varchar(100),
  contact_person varchar(100),
  title varchar(100),
  contact_number varchar(100),
  email varchar(100),
  FOREIGN KEY (company_id)
    REFERENCES vendor_info(id)
    ON DELETE CASCADE
);
