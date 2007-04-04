CREATE TABLE addresses (
  id int(11) NOT NULL auto_increment,
  person_id int(11),
  address varchar(255),
  city varchar(255),
  state varchar(255),
  zip int(9),
  PRIMARY KEY (id)
);
