# GET /api/productos/activos - Catálogo v1

## Objetivo
Documentar la primera versión funcional del catálogo real para cliente, correspondiente a RF3 v1, donde la aplicación consulta y muestra productos disponibles publicados por los comercios.

Esta versión cubre únicamente el **listado real de productos disponibles**.  
No incluye todavía detalle de producto, filtros, búsqueda, ordenamiento ni ubicación.

---

## Endpoint

**Método:** `GET`  
**URL oficial:** `/api/productos/activos`

> Nota: si en el backend sigue existiendo compatibilidad con `/productos/activos`, se considera `/api/productos/activos` como endpoint oficial para la documentación v1.

---

## Descripción

Este endpoint devuelve el listado de productos que deben aparecer en el catálogo del cliente para esta primera versión.

La idea de esta v1 es permitir que Android pueda consumir productos reales desde backend y mostrarlos en `CatalogoClienteActivity`.

---

## Qué devuelve

Devuelve una lista de productos disponibles para catálogo.

En esta versión, aunque el backend pueda devolver más información según la entity, los campos relevantes para el catálogo cliente son:

- `id`
- `codigo`
- `nombre`
- `descripcion`
- `precioOriginal`
- `precioReducido`
- `fechaVencimiento`
- `stock`
- `unidadVenta`
- `estado`

---

## Regla de visibilidad para catálogo

Para que un producto sea visible en el catálogo cliente, debe cumplir estas condiciones:

- estar activo
- tener stock mayor a 0
- no estar vencido
- tener `precioReducido < precioOriginal`

---

## Ejemplo de respuesta JSON

```json
[
  {
    "id": 1,
    "codigo": "PAN001",
    "nombre": "Pan frances",
    "descripcion": "Pan francés del día anterior",
    "precioOriginal": 2000,
    "precioReducido": 1000,
    "fechaVencimiento": "2026-05-07",
    "stock": 40,
    "unidadVenta": "docena",
    "estado": true
  },
  {
    "id": 2,
    "codigo": "LAC001",
    "nombre": "Yogur",
    "descripcion": "Yogur natural próximos a vencer",
    "precioOriginal": 1500,
    "precioReducido": 900,
    "fechaVencimiento": "2026-05-10",
    "stock": 20,
    "unidadVenta": "unidad",
    "estado": true
  }
]
```

---

## Caso sin resultados

Si no hay productos visibles, la respuesta esperada del backend será una lista vacía:

```json
[]
```

En Android, para esta v1, el comportamiento esperado será mostrar un mensaje tipo:

`No se encontraron ofertas`

---

## Alcance de esta versión

Esta documentación corresponde únicamente a **RF3 v1**, es decir:

- consumo de productos reales desde backend
- listado real en catálogo cliente

Quedan para pasos posteriores de RF3:

- detalle de producto
- filtros por categoría / precio / comercio
- búsqueda
- ordenamiento
- ubicación / distancia aproximada

---

## Dependencias mínimas para probarlo

Para que este endpoint devuelva resultados visibles en catálogo, la base de datos debe tener:

- productos cargados
- productos activos
- productos con stock mayor a 0
- productos no vencidos

---

## Estado actual

Esta versión queda pensada como base común entre backend y Android para cerrar el primer paso real del catálogo cliente.
