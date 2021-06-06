-- create users table
CREATE TABLE users(
  	first_name VARCHAR(50) NOT NULL,
  	last_name VARCHAR(50) NOT NULL,
  	username VARCHAR(50) NOT NULL PRIMARY KEY,
  	friends LONGTEXT,
  	companies LONGTEXT
);

-- insert a user into table
INSERT INTO users (
    first_name,
    last_name,
    username,
    friends,
    companies
)
VALUES ('apinan', 'yogaratnam', 'apinanyogaratnam', '{}', '{}');

-- insert a user into table
INSERT INTO users (
    first_name,
    last_name,
    username,
    friends,
    companies
)
VALUES ('stewie', 'angel', 'stewietheangel', '{}', '{heisenborg}');

-- insert a user into table
INSERT INTO users (
    first_name,
    last_name,
    username,
    friends,
    companies
)
VALUES ('walter', 'white', 'heisenborg', '{apinanyogaratnam}', '{stewietheangel}');

-- update user info
UPDATE users set username="heisenborg" where name="vai9er"

-- show users table data
SELECT * FROM users;

