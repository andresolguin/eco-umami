# Producto v1

## Objetivo
Definir la estructura base del objeto `Producto` para alinear backend y Android en el RF2 (gestión de productos) y dejar preparada su futura visualización en catálogo.

---

## Nombre del objeto
`Producto`

---

## Campos

| Campo | Tipo | Obligatorio | Descripción |
|---|---|---:|---|
| id | number | No | Identificador único del producto |
| nombre | string | Sí | Nombre del producto |
| categoriaId | number | Sí | Identificador de la categoría del producto |
| imagenUrl | string | No | URL o referencia de imagen del producto |
| descripcion | string | Sí | Descripción del producto |
| precioOriginal | number | Sí | Precio original del producto |
| precioReducido | number | Sí | Precio reducido del producto |
| fechaVencimiento | string | Sí | Fecha de vencimiento en formato `YYYY-MM-DD` |
| stock | number | Sí | Cantidad disponible del producto |
| estado | boolean | Sí | Estado lógico del producto (`true` = activo, `false` = inactivo) |
| comercioId | number | Sí | Identificador del comercio/vendedor que publica el producto |

---

## Reglas básicas de validación

- `precioReducido` debe ser menor que `precioOriginal`.
- `stock` debe ser mayor o igual a 0.
- `fechaVencimiento` debe tener formato válido (`YYYY-MM-DD`).
- Los campos obligatorios deben estar completos.

---

## Regla de visibilidad en catálogo

Un producto será visible para el cliente en catálogo si cumple todas estas condiciones:

- `estado = true`
- `stock > 0`
- `fechaVencimiento` no está vencida
- `precioReducido < precioOriginal`

---

## Ejemplo JSON

```json
{
  "id": 1,
  "nombre": "Medialunas del día",
  "categoriaId": 2,
  "imagenUrl": "https://miapp.com/img/medialunas.jpg",
  "descripcion": "Docena de medialunas horneadas en el día",
  "precioOriginal": 3500,
  "precioReducido": 2200,
  "fechaVencimiento": "2026-04-05",
  "stock": 8,
  "estado": true,
  "comercioId": 4
}

