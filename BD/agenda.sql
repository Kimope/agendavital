CREATE TABLE usuarios(
nick VARCHAR(50) PRIMARY KEY,
nombre VARCHAR(100),
apellido VARCHAR(200),
contrasena VARCHAR(15),
fecha_nac TEXT, ciudad varchar(50), correo varchar(70));
;
CREATE TABLE "noticias" (
    "id_noticia" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "titulo" TEXT,
    "link" TEXT,
    "fecha" TEXT,
    "categoria" VARCHAR(100),
    "cuerpo" TEXT
);
CREATE TABLE sqlite_sequence(name,seq);
CREATE TABLE momentos_noticias_etiquetas(
id_momento_noticia_etiqueta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
id_momento INT,
id_noticia INT,
id_etiqueta INT,
FOREIGN KEY(id_momento) REFERENCES momentos(id_momento),
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia),
FOREIGN KEY(id_etiqueta) REFERENCES etiquetas(id_etiqueta));
CREATE TABLE etiquetas(
id_etiqueta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
nombre VARCHAR(100));
CREATE TABLE preguntas(
id_pregunta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
pregunta TEXT,
id_noticia INT,
FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia));
CREATE TABLE momentos (
    "id_momento" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "fecha" TEXT,
    "descripcion" TEXT,
    "id_documento" INT,
    "id_noticia" INT,
    "color" TEXT
, "id_usuario" TEXT);
CREATE TABLE documentos(
id_documento INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
ruta_doc TEXT);
