USE expensebook;

CREATE TABLE expenses (
  id INTEGER NOT NULL AUTO_INCREMENT,
  description VARCHAR(100) NOT NULL,
  value DECIMAL(11,2) NOT NULL,
  paid_at DATE NOT NULL,
  PRIMARY KEY (id)
);
