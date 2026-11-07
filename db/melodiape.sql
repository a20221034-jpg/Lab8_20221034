DROP DATABASE IF EXISTS melodiape;
CREATE DATABASE melodiape;
USE melodiape;

CREATE TABLE genero (
  idgenero INT PRIMARY KEY,
  nombre VARCHAR(45) NOT NULL
);

CREATE TABLE cancion (
  idcancion INT PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(80) NOT NULL,
  artista VARCHAR(60) NOT NULL,
  duracion_seg INT NOT NULL,
  anio INT NOT NULL,
  idgenero INT NOT NULL,
  FOREIGN KEY (idgenero) REFERENCES genero(idgenero)
);

INSERT INTO genero VALUES
(1,'Rock'),(2,'Pop'),(3,'Salsa'),(4,'Reggaeton');

INSERT INTO cancion (titulo,artista,duracion_seg,anio,idgenero) VALUES
('Bohemian Rhapsody','Queen',354,1975,1),
('Hotel California','Eagles',391,1976,1),
('Smells Like Teen Spirit','Nirvana',301,1991,1),
('Billie Jean','Michael Jackson',294,1982,2),
('Blinding Lights','The Weeknd',200,2019,2),
('Vivir Mi Vida','Marc Anthony',252,2013,3),
('Pedro Navaja','Rubén Blades',427,1978,3),
('Despacito','Luis Fonsi',229,2017,4);
