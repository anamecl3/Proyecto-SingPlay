-- ----------CREACION DE BASE DE DATOS  DE SingPlay -------------------------
-- Intengrantes : 
-- Fecha Creacion : 04 / 06 / 2025
-- Nombre de Proyecto : Sing Play

 Drop database SingPlay;
CREATE DATABASE SingPlay;
Use SingPlay;

-- Tabla USUARIO
CREATE TABLE TB_USUARIO(
  ID_USUARIO INT AUTO_INCREMENT, 
  user VARCHAR(255) NOT NULL, 
  email VARCHAR(255) UNIQUE NOT NULL, 
  password VARCHAR(255) NOT NULL,
  fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP, 
  PRIMARY KEY (ID_USUARIO)
);



-- Tabla COMPOSITOR (corregido: NACIONALIDAD, IMAGEN_URL, ID_COMPOSITOR)
CREATE TABLE TB_COMPOSITOR(
  ID_COMPOSITOR INT AUTO_INCREMENT, 
  NOMBRE VARCHAR(100) NOT NULL, 
  NACIONALIDAD VARCHAR(50), 
  DESCRIPCION TEXT, 
  IMAGEN_URL VARCHAR(255),
  PRIMARY KEY(ID_COMPOSITOR)
);

-- Tabla CANTANTE
CREATE TABLE TB_CANTANTE(
  ID_CANTANTE INT AUTO_INCREMENT, 
  NOMBRE VARCHAR(100) NOT NULL, 
  GENERO VARCHAR(50),
  DESCRIPCION TEXT,
  IMAGEN_URL VARCHAR(255),
  PRIMARY KEY (ID_CANTANTE)
);

-- Tabla ALBUM
CREATE TABLE TB_ALBUM (
  ID_ALBUM INT AUTO_INCREMENT, 
  TITULO VARCHAR(100) NOT NULL, 
  FECHA_LANZAMIENTO DATE, 
  IMAGEN_URL VARCHAR(255),
  ID_CANTANTE INT, 
  PRIMARY KEY(ID_ALBUM),
  FOREIGN KEY (ID_CANTANTE) REFERENCES TB_CANTANTE(ID_CANTANTE)
);

-- Tabla CANCION
CREATE TABLE TB_CANCION(
  ID_CANCION INT AUTO_INCREMENT, 
  TITULO VARCHAR(100) NOT NULL, 
  DURACION TIME, 
  GENERO VARCHAR(50),
  URL_AUDIO VARCHAR(255),
  ID_ALBUM INT, 
  ID_COMPOSITOR INT, 
  PRIMARY KEY (ID_CANCION),
  FOREIGN KEY (ID_ALBUM) REFERENCES TB_ALBUM(ID_ALBUM),
  FOREIGN KEY (ID_COMPOSITOR) REFERENCES TB_COMPOSITOR(ID_COMPOSITOR)
);

-- Tabla COMENTARIO
CREATE TABLE TB_COMENTARIO (
  ID_COMENTARIO INT AUTO_INCREMENT,
  CONTENIDO TEXT NOT NULL, 
  FECHA DATETIME DEFAULT CURRENT_TIMESTAMP, 
  ID_USUARIO INT, 
  ID_CANCION INT, 
  PRIMARY KEY(ID_COMENTARIO),
  FOREIGN KEY(ID_USUARIO) REFERENCES TB_USUARIO(ID_USUARIO), 
  FOREIGN KEY(ID_CANCION) REFERENCES TB_CANCION(ID_CANCION)
);



-- manuel / maria / carlos / miguel
INSERT INTO TB_USUARIO ( user , email , password , fechaRegistro ) VALUES
('Maria', 'Maria132@gmail.com', '$2a$10$PE03mj5ZJmcyZcVpVl9/5ezRdWiqYnjt1mVxUojqtVB12b9IfZN/2' , '2025-06-01 10:30:00'),
('Manuel', 'Manuel123@hgmail.com', '$2a$10$VAkgWrQTeUiu929mF8aFtOPXS.wGUyC4VCKJ2gI/QhQquCz0MLytO' , '2025-06-03 14:15:00'),
('Carlos', 'Carlos123@gmail.com', '$2a$10$.lqPhwtQjyfCN9e5jbx6LeeZW8Xxbt4LL7UMoZJ4CcCDFMllRHgHi' , '2025-06-10 08:00:00'),
('Miguel', 'Miguel@gmail.com', '$2a$10$oRmHaZhwNql0p6R.CDUQluVCnctxvXSnx1XZ5p9aP4bOQ5eo8wyo2' , '2025-06-15 16:45:00');

INSERT INTO TB_COMPOSITOR (NOMBRE, NACIONALIDAD, DESCRIPCION, IMAGEN_URL) VALUES
('Gregory Hein', 'Estadounidense', 'Compositor de Miley', 'https://example.com/img/greg.jpg'),
('Tyler Johnson', 'Estadounidense', 'Colaborador frecuente de Harry Styles', 'https://example.com/img/tyler.jpg'),
('Jack Antonoff', 'Estadounidense', 'Colaborador de Taylor Swift', 'https://example.com/img/antonoff.jpg'),
('Tainy', 'Puertorriqueño', 'Productor de Bad Bunny', 'https://example.com/img/tainy.jpg'),
('Bizarrap', 'Argentino', 'DJ y productor', 'https://example.com/img/bizarrap.jpg'),
('Rauw Alejandro', 'Puertorriqueño', 'Cantante y compositor', 'https://example.com/img/rauw.jpg'),
('Pedro Tovar', 'Mexicano', 'Compositor del género regional', 'https://example.com/img/pedro.jpg'),
('Doja Cat', 'Estadounidense', 'Rapper y cantante', 'https://example.com/img/doja.jpg'),
('Keityn', 'Colombiano', 'Compositor de éxitos latinos', 'https://example.com/img/keityn.jpg'),
('Mark Ronson', 'Británico', 'Productor de renombre', 'https://example.com/img/ronson.jpg');



INSERT INTO TB_CANTANTE (NOMBRE, GENERO, DESCRIPCION, IMAGEN_URL) VALUES
('Miley Cyrus', 'Pop', 'Cantante estadounidense de pop y rock', 'https://example.com/img/miley.jpg'),
('Harry Styles', 'Pop', 'Cantante británico, exintegrante de One Direction', 'https://example.com/img/harry.jpg'),
('Taylor Swift', 'Pop', 'Cantautora estadounidense, ícono del pop y country', 'https://example.com/img/taylor.jpg'),
('Bad Bunny', 'Reguetón', 'Cantante puertorriqueño de trap latino y reguetón', 'https://example.com/img/bad.jpg'),
('Shakira', 'Latin Pop', 'Cantante colombiana reconocida mundialmente', 'https://example.com/img/shakira.jpg'),
('Rauw Alejandro', 'Urbano', 'Cantante y bailarín puertorriqueño', 'https://example.com/img/rauw.jpg'),
('Peso Pluma', 'Regional Mexicano', 'Cantante mexicano del nuevo regional', 'https://example.com/img/peso.jpg'),
('Doja Cat', 'Rap', 'Rapper y cantante pop contemporánea', 'https://example.com/img/doja.jpg'),
('Dua Lipa', 'Pop', 'Cantante británica de éxito global', 'https://example.com/img/dua.jpg'),
('Bizarrap', 'Electrónica', 'Productor argentino famoso por sus sessions', 'https://example.com/img/bizarrap.jpg');


INSERT INTO TB_ALBUM (TITULO, FECHA_LANZAMIENTO, IMAGEN_URL, ID_CANTANTE) VALUES
('Endless Summer Vacation', '2023-03-10', 'https://example.com/img/flowers_album.jpg', 1),
('Harry\'s House', '2022-05-20', 'https://example.com/img/harry_album.jpg', 2),
('Midnights', '2022-10-21', 'https://example.com/img/midnights_album.jpg', 3),
('Un Verano Sin Ti', '2022-05-06', 'https://example.com/img/un_verano.jpg', 4),
('BZRP Sessions', '2023-01-11', 'https://example.com/img/bzrp.jpg', 5),
('Saturno', '2022-11-11', 'https://example.com/img/saturno.jpg', 6),
('GENESIS', '2023-06-23', 'https://example.com/img/genesis.jpg', 7),
('Scarlet', '2023-09-22', 'https://example.com/img/scarlet.jpg', 8),
('Copa Vacía', '2023-06-29', 'https://example.com/img/copavacia.jpg', 5),
('Barbie The Album', '2023-07-21', 'https://example.com/img/barbie.jpg', 9);



INSERT INTO TB_CANCION (TITULO, DURACION, GENERO, URL_AUDIO, ID_ALBUM, ID_COMPOSITOR) VALUES
('Flowers', '00:03:20', 'Pop', 'https://example.com/audio/flowers.mp3', 1, 1),
('As It Was', '00:02:47', 'Pop', 'https://example.com/audio/as_it_was.mp3', 2, 2),
('Anti-Hero', '00:03:20', 'Pop', 'https://example.com/audio/antihero.mp3', 3, 3),
('Tití Me Preguntó', '00:04:03', 'Reguetón', 'https://example.com/audio/titi.mp3', 4, 4),
('Shakira: BZRP Music Sessions #53', '00:03:33', 'Latin Pop', 'https://example.com/audio/bzrp_shakira.mp3', 5, 5),
('La Jumpa', '00:03:45', 'Trap', 'https://example.com/audio/lajumpa.mp3', 6, 6),
('Ella Baila Sola', '00:03:20', 'Regional Mexicano', 'https://example.com/audio/ella.mp3', 7, 7),
('Paint The Town Red', '00:03:51', 'Rap', 'https://example.com/audio/paint.mp3', 8, 8),
('Copa Vacía', '00:03:15', 'Latin Pop', 'https://example.com/audio/copa.mp3', 9, 9),
('Dance The Night', '00:02:58', 'Pop', 'https://example.com/audio/dance.mp3', 10, 10);


INSERT INTO TB_COMENTARIO (CONTENIDO, ID_USUARIO, ID_CANCION) VALUES
('Esta canción me hace sentir tan libre. ¡Me encanta!', 1, 1),
('Harry nunca decepciona. Esta canción es un hit.', 2, 2),
('Taylor escribió exactamente lo que siento. Wow.', 3, 3),
('Bad Bunny lo hizo de nuevo. Temazo.', 4, 4),
('Shakira + Bizarrap = fuego total 🔥🔥', 1 , 5),
('La Jumpa está durísima. Me prende siempre.', 2 , 6),
('Ella Baila Sola está en repeat todo el día.', 3, 7),
('Doja Cat cambió el juego con esta canción.', 4 , 8),
('Shakira suena tan poderosa aquí. Épica.', 3 , 9),
('No puedo dejar de bailar con esta canción 💃', 1 , 10);


ALTER TABLE TB_USUARIO ADD COLUMN activo BOOLEAN DEFAULT TRUE;
ALTER TABLE TB_COMPOSITOR ADD COLUMN activo BOOLEAN DEFAULT TRUE;
ALTER TABLE TB_CANTANTE ADD COLUMN activo BOOLEAN DEFAULT TRUE;
ALTER TABLE TB_ALBUM ADD COLUMN activo BOOLEAN DEFAULT TRUE;
ALTER TABLE TB_CANCION ADD COLUMN activo BOOLEAN DEFAULT TRUE;

SELECT * FROM TB_USUARIO;
SELECT * FROM TB_COMPOSITOR;
SELECT * FROM TB_CANTANTE ;
SELECT * FROM TB_ALBUM;
SELECT * FROM TB_CANCION;
SELECT * FROM TB_COMENTARIO;


