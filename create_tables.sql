-- create users table
CREATE TABLE users(
  	first_name VARCHAR(50) NOT NULL,
  	last_name VARCHAR(50) NOT NULL,
  	username VARCHAR(50) NOT NULL PRIMARY KEY,
  	friends LONGTEXT
);

-- insert a user into table
INSERT INTO users (
    first_name,
    last_name,
    username,
    friends
)
VALUES ('apinan', 'yogaratnam', 'apinanyogaratnam', '{}');

-- insert a user into table
INSERT INTO users (
    first_name,
    last_name,
    username,
    friends
)
VALUES ('stewie', 'angel', 'stewietheangel', '{"apinanyogaratnam"}');

-- show users table data
SELECT * FROM users;

