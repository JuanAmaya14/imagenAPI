CREATE TABLE imagenes (
  id INT NOT NULL AUTO_INCREMENT,
  imagen LONGBLOB NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);