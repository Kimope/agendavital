CREATE TABLE documentos(
id_documento INT PRIMARY KEY,
ruta_doc TEXT);
;
CREATE TABLE momentos_noticias_etiquetas(
id_momento_noticia_etiqueta INT PRIMARY KEY,
id_momento INT,
id_noticia INT,
id_etiqueta INT,
FOREIGN KEY(id_momento) REFERENCES momentos(id_momento),
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia),
FOREIGN KEY(id_etiqueta) REFERENCES etiquetas(id_etiqueta));
;
CREATE TABLE usuarios(
nick VARCHAR(50) PRIMARY KEY,
nombre VARCHAR(100),
apellido VARCHAR(200),
contrasena VARCHAR(15),
fecha_nac TEXT, ciudad varchar(50), correo varchar(70));
;
CREATE TABLE preguntas(
id_pregunta INT PRIMARY KEY,
pregunta TEXT,
id_noticia INT,
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia));
;
CREATE TABLE momentos(
id_momento INT PRIMARY KEY,
fecha TEXT,
descripcion TEXT,
id_documento INT,
id_noticia  INT,
FOREIGN KEY(id_documento) REFERENCES documentos(id_documento),
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia));
;
CREATE TABLE "noticias" (
    "id_noticia" INTEGER PRIMARY KEY AUTOINCREMENT,
    "titulo" TEXT,
    "link" TEXT,
    "cuerpo" TEXT,
    "categoria" TEXT,
    "fecha" TEXT
);
CREATE TABLE sqlite_sequence(name,seq);
CREATE TABLE "etiquetas" (
    "id_etiqueta" INTEGER PRIMARY KEY,
    "nombre" TEXT
);
