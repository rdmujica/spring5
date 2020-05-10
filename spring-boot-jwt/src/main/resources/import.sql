INSERT INTO clientes (id,nombre,apellido,email,create_at, foto) VALUES (1,'Rafael','Mujica','rafael@gmail.com','2020-04-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (2,'Daniel','López','daniel@gmail.com','2005-04-21','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (3,'Yulianny','Medina','yulianny@gmail.com','1986-10-07','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (4,'Diannery','Terán','dianneiry@gmail.com','2020-04-21','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (5,'Samuel','Mujica','samuel@gmail.com','2018-12-13','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (6,'Ana','Carmona','carmona@gmail.com','1018-10-11','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (7,'Casa','Papel','papel@gmail.com','2011-11-04','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (8,'Fedra','Angel','fedra@gmail.com','2000-12-31','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (9,'Esteban','Dante','esteban@gmail.com','2005-04-15','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (10,'Cris','Tabanos','tabanos@gmail.com','2020-05-01','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (11,'Rafael','Mujica','rafael@gmail.com','2020-04-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (12,'Daniel','López','daniel@gmail.com','2005-04-21','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (13,'Yulianny','Medina','yulianny@gmail.com','1986-10-07','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (14,'Diannery','Terán','dianneiry@gmail.com','2020-04-21','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (15,'Samuel','Mujica','samuel@gmail.com','2018-12-13','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (16,'Ana','Carmona','carmona@gmail.com','1018-10-11','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (17,'Casa','Papel','papel@gmail.com','2011-11-04','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (18,'Fedra','Angel','fedra@gmail.com','2000-12-31','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (19,'Esteban','Dante','esteban@gmail.com','2005-04-15','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) VALUES (20,'Cris','Tabanos','tabanos@gmail.com','2020-05-01','');

/*Tabla productos*/
INSERT INTO productos(nombre,precio,create_at) VALUES('Panasonic Pantalla LCD',259990,NOW());
INSERT INTO productos(nombre,precio,create_at) VALUES('Sony camara digital DSC-W320B',123490,NOW());
INSERT INTO productos(nombre,precio,create_at) VALUES('Apple iPod shuffle',1499990,NOW());
INSERT INTO productos(nombre,precio,create_at) VALUES('Sony Notebook Z110',37990,NOW());
INSERT INTO productos(nombre,precio,create_at) VALUES('Hewlett Packard Multifuncional F2280',69990,NOW());
INSERT INTO productos(nombre,precio,create_at) VALUES('Bianchi Bicicleta Aro 26',69990,NOW());
INSERT INTO productos(nombre,precio,create_at) VALUES('Mica Comoda 5 cajones',299990,NOW());

/*Tabla facturas*/
INSERT INTO facturas (id,descripcion,observacion,cliente_id,create_at) VALUES (1,'Factura de oficina',null,1,NOW());
INSERT INTO facturas (id,descripcion,observacion,cliente_id,create_at) VALUES (2,'Factura Bicicleta','El cliente pago pero aún no retira',1,NOW());

/*Tabla facturas_items*/
INSERT INTO facturas_items(cantidad,factura_id,producto_id) VALUES (1,1,1);
INSERT INTO facturas_items(cantidad,factura_id,producto_id) VALUES (2,1,4);
INSERT INTO facturas_items(cantidad,factura_id,producto_id) VALUES (1,1,5);
INSERT INTO facturas_items(cantidad,factura_id,producto_id) VALUES (1,1,7);

INSERT INTO facturas_items(cantidad,factura_id,producto_id) VALUES (3,2,6);

//CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(45) NOT NULL UNIQUE, password VARCHAR(60) NOT NULL,enabled TINYINT(1) NOT NULL DEFAULT 1 ); 
INSERT INTO users (username,password) VALUES('rafael','$2a$10$mOXrVmXNFP2GHjkgvWDAY.oHDAHysUZDwLZnbLicTmVTWgv0Gda2e');
INSERT INTO users (username,password) VALUES('admin','$2a$10$w.X0K4lmO7oL1RuOItOFmu1m2aZBFJXPBo2IUe/y9p69.CaGYSQI.'); 

//CREATE TABLE authorities (id INT PRIMARY KEY AUTO_INCREMENT, user_id INT  NOT NULL, authority VARCHAR(45) NOT NULL, UNIQUE(user_id,authority));
//alter table authorities add constraint fk_authorities_users foreign key (user_id) references users(id) ON DELETE CASCADE ON UPDATE CASCADE; 

INSERT INTO authorities (user_id,authority) VALUES(1,'ROLE_USER');
INSERT INTO authorities (user_id,authority) VALUES(2,'ROLE_USER');
INSERT INTO authorities (user_id,authority) VALUES(2,'ROLE_ADMIN');