DROP DATABASE IF EXISTS ecommerce_eu;
CREATE DATABASE ecommerce_eu CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE ecommerce_eu;

-- Base tables
CREATE TABLE tipo_usuario (
  id_tipo_usuario INT AUTO_INCREMENT PRIMARY KEY,
  descripcion VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE tipo_persona (
  id_tipo_persona INT AUTO_INCREMENT PRIMARY KEY,
  descripcion VARCHAR(30) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE categoria (
  id_categoria INT AUTO_INCREMENT PRIMARY KEY,
  descripcion VARCHAR(100) NOT NULL,
  estado TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- Usuario
CREATE TABLE usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  mail VARCHAR(100) NOT NULL UNIQUE,
  pass VARCHAR(100) NOT NULL,

  id_tipo_usuario INT NOT NULL,
  id_tipo_persona INT NULL,

  dni VARCHAR(20) NULL,
  nombre VARCHAR(50) NULL,
  apellido VARCHAR(50) NULL,

  razon_social VARCHAR(100) NULL,
  cuit VARCHAR(20) NULL,

  direccion VARCHAR(100) NULL,
  ciudad VARCHAR(50) NULL,
  codigo_postal INT NULL,
  telefono VARCHAR(20) NULL,

  estado TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT fk_usuario_tipo_usuario
    FOREIGN KEY (id_tipo_usuario) REFERENCES tipo_usuario(id_tipo_usuario),

  CONSTRAINT fk_usuario_tipo_persona
    FOREIGN KEY (id_tipo_persona) REFERENCES tipo_persona(id_tipo_persona)
) ENGINE=InnoDB;

-- Producto
CREATE TABLE producto (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(50) NOT NULL UNIQUE,
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(1000) NOT NULL,

  precio_original DECIMAL(10,2) NOT NULL,
  precio_reducido DECIMAL(10,2) NOT NULL,
  fecha_vencimiento DATE NOT NULL,

  stock INT NOT NULL,
  unidad_venta VARCHAR(20) NOT NULL,

  id_categoria INT NOT NULL,
  id_vendedor INT NOT NULL,

  estado TINYINT(1) NOT NULL DEFAULT 1,

  CONSTRAINT chk_precio CHECK (precio_reducido < precio_original),

  CONSTRAINT fk_producto_categoria
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),

  CONSTRAINT fk_producto_vendedor
    FOREIGN KEY (id_vendedor) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- Imagen
CREATE TABLE imagen (
  id_imagen INT AUTO_INCREMENT PRIMARY KEY,
  id_producto INT NOT NULL,
  imagen_url VARCHAR(1000) NOT NULL,

  CONSTRAINT fk_imagen_producto
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE=InnoDB;

-- Carrito
CREATE TABLE carrito (
  id_carrito INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL UNIQUE,
  id_vendedor INT NOT NULL,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_carrito_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),

  CONSTRAINT fk_carrito_vendedor
    FOREIGN KEY (id_vendedor) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- Carrito item
CREATE TABLE carrito_item (
  id_carrito INT NOT NULL,
  id_producto INT NOT NULL,
  cantidad INT NOT NULL,

  PRIMARY KEY (id_carrito, id_producto),

  CONSTRAINT fk_carrito_item_carrito
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito),

  CONSTRAINT fk_carrito_item_producto
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE=InnoDB;

-- Pedido
CREATE TABLE pedido (
  id_pedido INT AUTO_INCREMENT PRIMARY KEY,

  id_usuario INT NOT NULL,
  id_vendedor INT NOT NULL,

  fecha_pedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  metodo_entrega INT NOT NULL,
  fecha_entrega DATE NOT NULL,

  precio_total DECIMAL(10,2) NOT NULL,

  metodo_pago INT NOT NULL,
  estado_pago INT NOT NULL,
  estado_pedido INT NOT NULL,

  CONSTRAINT fk_pedido_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),

  CONSTRAINT fk_pedido_vendedor
    FOREIGN KEY (id_vendedor) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- Pedido item
CREATE TABLE pedido_item (
  id_pedido INT NOT NULL,
  id_producto INT NOT NULL,
  cantidad INT NOT NULL,
  precio DECIMAL(10,2) NOT NULL,

  PRIMARY KEY (id_pedido, id_producto),

  CONSTRAINT fk_pedido_item_pedido
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),

  CONSTRAINT fk_pedido_item_producto
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE=InnoDB;

-- Datos mínimos para probar
INSERT INTO tipo_usuario (descripcion) VALUES ('ADMIN'), ('CLIENTE'), ('COMERCIO');
INSERT INTO tipo_persona (descripcion) VALUES ('FISICA'), ('JURIDICA');