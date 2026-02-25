CREATE DATABASE EcommerceEU;
USE EcommerceEU;

-------------------------------------------------
-- TABLA TIPO USUARIO
-------------------------------------------------
CREATE TABLE TipoUsuario (
    id_tipo_usuario INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL
);

-------------------------------------------------
-- TABLA TIPO PERSONA
-------------------------------------------------
CREATE TABLE TipoPersona (
    id_tipo_persona INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(30) NOT NULL
);

-------------------------------------------------
-- TABLA USUARIO
-------------------------------------------------
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(100) NOT NULL UNIQUE,
    pass VARCHAR(50) NOT NULL,

    id_tipo_usuario INT NOT NULL,
    id_tipo_persona INT NULL,  -- solo relevante para vendedores

    dni VARCHAR(20) NULL,
    nombre VARCHAR(50) NULL,
    apellido VARCHAR(50) NULL,

    razon_social VARCHAR(100) NULL,
    cuit VARCHAR(20) NULL,

    direccion VARCHAR(100) NULL,
    ciudad VARCHAR(50) NULL,
    codigo_postal INT NULL,
    telefono VARCHAR(20) NULL,

    estado BOOLEAN NOT NULL,

    FOREIGN KEY (id_tipo_usuario) REFERENCES TipoUsuario(id_tipo_usuario),
    FOREIGN KEY (id_tipo_persona) REFERENCES TipoPersona(id_tipo_persona)
);

-------------------------------------------------
-- TABLA CATEGORIA
-------------------------------------------------
CREATE TABLE Categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL
);

-------------------------------------------------
-- TABLA PRODUCTO (MULTI-VENDEDOR)
-------------------------------------------------
CREATE TABLE Producto (
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

    estado BOOLEAN NOT NULL,

    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
    FOREIGN KEY (id_vendedor) REFERENCES Usuario(id_usuario)
);

-------------------------------------------------
-- TABLA IMAGEN
-------------------------------------------------
CREATE TABLE Imagen (
    id_imagen INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    imagen_url VARCHAR(1000) NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

-------------------------------------------------
-- TABLA CARRITO
-------------------------------------------------
CREATE TABLE Carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL UNIQUE,
    id_vendedor INT NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_vendedor) REFERENCES Usuario(id_usuario)
);

-------------------------------------------------
-- TABLA ITEMS DEL CARRITO
-------------------------------------------------
CREATE TABLE Carrito_Item (
    id_carrito INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,

    PRIMARY KEY (id_carrito, id_producto),

    FOREIGN KEY (id_carrito) REFERENCES Carrito(id_carrito),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

-------------------------------------------------
-- TABLA PEDIDO
-------------------------------------------------
CREATE TABLE Pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,

    id_usuario INT NOT NULL,   -- comprador
    id_vendedor INT NOT NULL,  -- vendedor

    fecha_pedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    metodo_entrega INT NOT NULL,
    fecha_entrega DATE NOT NULL,

    precio_total DECIMAL(10,2) NOT NULL,

    metodo_pago INT NOT NULL,
    estado_pago INT NOT NULL,
    estado_pedido INT NOT NULL,

    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_vendedor) REFERENCES Usuario(id_usuario)
);

-------------------------------------------------
-- TABLA ITEMS DEL PEDIDO
-------------------------------------------------
CREATE TABLE Pedido_Item (
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,

    PRIMARY KEY (id_pedido, id_producto),

    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);