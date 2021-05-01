CREATE TABLE `vendor_info` (
  id bigint(20) NOT NULL,
  name varchar(100) NOT NULL,
  applicant varchar(100) not null,
  owner varchar(100) not null,
  address varchar(1000) not null,
  tel varchar(100),
  fax varchar(100),
  remark varchar(100),
  update_time date default sysdate,
  CONSTRAINT pk_vendor PRIMARY KEY (id)
);

CREATE TABLE `contact_info` (
  company_id bigint(20) not null,
  company_name varchar(100),
  contact_person varchar(100),
  title varchar(100),
  contact_number varchar(100),
  email varchar(100),
  CONSTRAINT fk_conatct_vendor_id FOREIGN KEY (company_id) REFERENCES vendor_info(id)
);
