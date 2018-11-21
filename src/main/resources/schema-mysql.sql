create table if not exists companies (
  id int(10) not null primary key auto_increment,
  name varchar(2048) not null,
  irrelevant bit(1) not null default 0
) engine=InnoDB default charset=utf8mb4 collate utf8mb4_general_ci;

create table if not exists extracts (
  id int(10) not null primary key auto_increment,
  decision_id int(10) not null,
  irrelevant bit(1) not null default 0,
  lang varchar(2) not null,
  sentences longtext,
  relation int(1),
  one_company_id int(10) not null,
  two_company_id int(10) not null,
  key extract_k_decision_id(decision_id),
  constraint extract_fk_company_one foreign key(one_company_id) references companies(id),
  constraint extract_fk_company_two foreign key(two_company_id) references companies(id)
) engine=InnoDB default charset=utf8mb4 collate utf8mb4_general_ci;
