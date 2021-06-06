-- create companies table
CREATE TABLE companies(
  	name VARCHAR(50) NOT NULL PRIMARY KEY,
  	network_list LONGTEXT,
  	followers_list LONGTEXT
);

-- insert a company into table
INSERT INTO companies (
    name,
    network_list,
    followers_list
)
VALUES ("McDonald's", "{}", "{}");

-- insert a company into table
INSERT INTO companies (
    name,
    network_list,
    followers_list
)
VALUES ("Tim Hortons", "{}", "{}");

-- update company info
UPDATE companies set name="McDonald" where name="MdDonald's"

SELECT * FROM companies;