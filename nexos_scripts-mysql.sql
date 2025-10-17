CREATE TABLE person (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	firstname VARCHAR(128) NOT NULL,
  middlename VARCHAR(128),
  lastname1 VARCHAR(128) NOT NULL,
  lastname2 VARCHAR(128)
) ENGINE=InnoDB;

INSERT INTO person (firstname, middlename, lastname1, lastname2) VALUES
('Andres', null, 'Fernandez', null),
('Julian', null, 'Silva', null),
('Pedro', null, 'Merino', null),
('Marta', null, 'Estupiñan', null);


CREATE TABLE product_type (
	name VARCHAR(64) NOT NULL PRIMARY KEY,
	description VARCHAR(256)
) ENGINE=InnoDB;

INSERT INTO product_type (name, description) VALUES 
('SAVING_ACCOUNT', null),
('CREDIT_LINE', null);
 

CREATE TABLE product (
  id VARCHAR(6) PRIMARY KEY,
	person_id INT UNSIGNED NOT NULL,
	product_type VARCHAR(64) NOT NULL,
	FOREIGN KEY (person_id) REFERENCES person(id),
	FOREIGN KEY (product_type) REFERENCES product_type(name)
) ENGINE=InnoDB;

INSERT INTO product (id, person_id, product_type) VALUES
('101010', 1, 'SAVING_ACCOUNT'),
('202020', 2, 'SAVING_ACCOUNT'),
('303030', 3, 'CREDIT_LINE'),
('404040', 4,  'CREDIT_LINE');


CREATE TABLE card_type (
	name VARCHAR(64) NOT NULL PRIMARY KEY,
	description VARCHAR(256)
) ENGINE=InnoDB;

INSERT INTO card_type (name, description) VALUES 
('DEBIT', null),
('CREDIT', null);

CREATE TABLE card_status (
	status VARCHAR(16) PRIMARY KEY,
	description VARCHAR(256)
) ENGINE=InnoDB;

INSERT INTO card_status (status, description) VALUES 
('ACTIVE', 'ACTIVE'),
('INACTIVE', 'INACTIVE'),
('BLOCKED', 'BLOCKED');


CREATE TABLE card (
  number VARCHAR(16) PRIMARY KEY,
  product_id VARCHAR(6) NOT NULL,
  card_type VARCHAR(64) NOT NULL,
  issue_date DATE NOT NULL,
  expiration_date DATE NOT NULL,
  currency VARCHAR(8) NOT NULL, -- USD
  balance DECIMAL(20,6) NOT NULL,
  status VARCHAR(16) NOT NULL,
  FOREIGN KEY (product_id) REFERENCES product(id),
  FOREIGN KEY (card_type) REFERENCES card_type(name),
  FOREIGN KEY (status) REFERENCES card_status(status)
) ENGINE=InnoDB;


CREATE TABLE transaction_status (
	status VARCHAR(16) PRIMARY KEY,
	description VARCHAR(256)
) ENGINE=InnoDB;

INSERT INTO transaction_status (status, description) VALUES 
('APPROVED', 'APPROVED'),
('REFUSED', 'REFUSED'),
('CANCELED', 'CANCELED');

CREATE TABLE transaction (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	card_number VARCHAR(16) NOT NULL,
	price DECIMAL(20,6) NOT NULL,
	transaction_date DATETIME NOT NULL,
	status VARCHAR(16) NOT NULL,
	FOREIGN KEY (card_number) REFERENCES card(number),
	FOREIGN KEY (status) REFERENCES transaction_status(status)
) ENGINE=InnoDB;






