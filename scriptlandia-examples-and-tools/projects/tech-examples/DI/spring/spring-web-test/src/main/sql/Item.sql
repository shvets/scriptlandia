CREATE TABLE item (
  id INTEGER NOT NULL PRIMARY KEY,
  name varchar(255),
  price decimal(15,2),
  expiration_date date
);

INSERT INTO item (id, name, price) values(1, 'name1', 5.5);
INSERT INTO item (id, name, price) values(2, 'name2', 6.5);
INSERT INTO item (id, name, price) values(3, 'name3', 7.5);

