# POST /api/productos - Producto v1

## Objetivo
Documentar la primera versión funcional del endpoint de alta de producto para el RF2 (gestión de productos), dejando claro cómo probarlo y qué requisitos mínimos necesita la base de datos.

---

## Endpoint

**Método:** `POST`  
**URL:** `/api/productos`

> Nota: actualmente también existe compatibilidad con `/productos`, pero el endpoint que tomamos como oficial para documentación es `/api/productos`.

---

## Descripción

Este endpoint permite registrar un nuevo producto publicado por un comercio.

En esta versión v1, el alta ya funciona de forma real contra base de datos y responde correctamente con `201 Created` cuando el registro se realiza con éxito.

---

## Request JSON de ejemplo

```json
{
  "codigo": "PROD001",
  "nombre": "Medialunas del día",
  "descripcion": "Docena de medialunas horneadas en el día",
  "precioOriginal": 3500,
  "precioReducido": 2200,
  "fechaVencimiento": "2026-04-05",
  "stock": 8,
  "unidadVenta": "docena",
  "categoriaId": 1,
  "vendedorId": 1
}

Campos esperados
Campo	                 Tipo	Obligatorio	Descripción
codigo                	string	Sí	        Código único del producto
nombre	                string	Sí	        Nombre del producto
descripcion	            string	Sí	        Descripción del producto
precioOriginal	        number	Sí	        Precio original
precioReducido	        number	Sí	        Precio reducido
fechaVencimiento	    string	Sí	        Fecha en formato YYYY-MM-DD
stock	                number	Sí	        Cantidad disponible
unidadVenta         	string	Sí	        Unidad de venta (unidad, docena, kg, etc.)
categoriaId	            number	Sí	        ID de categoría existente
vendedorId	            number	Sí	        ID de vendedor/usuario existente

Respuesta exitosa esperada
Status: 201 Created

{
  "id": 4,
  "codigo": "PROD001",
  "nombre": "Medialunas del día",
  "descripcion": "Docena de medialunas horneadas en el día",
  "precioOriginal": 3500,
  "precioReducido": 2200,
  "fechaVencimiento": "2026-04-05",
  "stock": 8,
  "unidadVenta": "docena",
  "estado": true,
  "categoriaId": 1,
  "vendedorId": 1
}

Reglas de negocio consideradas en esta versión
El producto se registra asociado a una categoría existente.
El producto se registra asociado a un vendedor existente.
El producto queda con estado = true al crearse.
Para el catálogo, la regla definida en Producto v1 sigue siendo:
activo
stock mayor a 0
no vencido
precio reducido menor al original

Datos mínimos requeridos en base de datos para probarlo

Para que el endpoint funcione en Postman, la base debe tener previamente:
- al menos una categoría cargada en categoria
- al menos un usuario/vendedor cargado en usuario
Si esos registros no existen, el alta falla por restricción de clave foránea (id_categoria o id_vendedor).

Ejemplo de categoría mínima de prueba:
INSERT INTO categoria (descripcion, estado)
VALUES ('Panadería', true);

Ejemplo de verificación de usuarios:
SELECT id_usuario, mail, id_tipo_usuario, estado
FROM usuario;

Observación importante
En el RF2 se menciona también el campo Imagen, pero en esta versión v1 del endpoint el alta del producto todavía no persiste imagen directamente en Producto, ya que esa parte se encuentra separada en la entidad/tabla correspondiente.
Por lo tanto, esta versión cierra primero el alta base del producto y deja la integración de imágenes para un paso posterior.

Estado actual
Esta versión fue probada correctamente en Postman con respuesta 201 Created, dejando operativo el alta de producto v1 en backend.
