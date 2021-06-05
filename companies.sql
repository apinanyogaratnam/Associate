-- create companies table
CREATE TABLE companies(
  	name VARCHAR(50) NOT NULL,
  	network_list LONGTEXT,
  	followers_list LONGTEXT,
);

-- insert a company into table
INSERT INTO users (
    name,
    network_list,
    followers_list
)
VALUES ("McDonald's", "{}", "{}");

-- insert a company into table
INSERT INTO users (
    name,
    network_list,
    followers_list
)
VALUES ("Tim Hortons", "{}", "{}");

SELECT * FROM companies;