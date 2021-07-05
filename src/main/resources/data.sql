CREATE TABLE clip_user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_name VARCHAR(250) NOT NULL
);

CREATE TABLE card_data (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  card_type VARCHAR(250) NOT NULL,
  card_number VARCHAR(250) NOT NULL,
  exp_month VARCHAR(250) DEFAULT NULL,
  exp_year VARCHAR(250) DEFAULT NULL
);

CREATE TABLE payload (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  amount DECIMAL NOT NULL,
  clip_user_id INT NOT NULL,
  card_data_id INT NOT NULL,
  date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  flag_disbursement INT NOT NULL,
  CONSTRAINT clip_user_id FOREIGN KEY (clip_user_id) REFERENCES clip_user(id),
  CONSTRAINT card_data_id FOREIGN KEY (card_data_id) REFERENCES card_data(id)
);

CREATE TABLE disbursement (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  amount DECIMAL NOT NULL,
  FK_clip_user_id INT NOT NULL,
  date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT FK_clip_user_id FOREIGN KEY (FK_clip_user_id) REFERENCES clip_user(id)
);