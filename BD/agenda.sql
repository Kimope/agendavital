CREATE TABLE usuarios(
nick VARCHAR(50) NOT NULL PRIMARY KEY,
nombre VARCHAR(100),
apellido VARCHAR(200),
contrasena VARCHAR(15),
fecha_nac TEXT);

CREATE TABLE documentos(
id_documento INT PRIMARY KEY NOT NULL,
ruta_doc TEXT);

CREATE TABLE etiquetas(
id_etiqueta INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
nombre VARCHAR(100));

CREATE TABLE noticias(
id_noticia INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
titulo TEXT,
fecha TEXT,
categoria VARCHAR(100)
);

CREATE TABLE preguntas(
id_pregunta INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
pregunta TEXT,
id_noticia INT,
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia));

CREATE TABLE momentos(
id_momento INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
fecha TEXT,
descripcion TEXT,
id_documento INT,
id_noticia  INT,
FOREIGN KEY(id_documento) REFERENCES documentos(id_documento),
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia));

CREATE TABLE momentos_noticias_etiquetas(
id_momento_noticia_etiqueta INT PRIMARY KEY AUTO_INCREMENT,
id_momento INT,
id_noticia INT,
id_etiqueta INT,
FOREIGN KEY(id_momento) REFERENCES momentos(id_momento),
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia),
FOREIGN KEY(id_etiqueta) REFERENCES etiquetas(id_etiqueta));
