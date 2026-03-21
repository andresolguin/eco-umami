# Menu v1 por rol

## Objetivo
Permitir que la app Android consulte qué menú mostrar según el rol del usuario.

## Endpoint
`GET /api/menu?rol={ROL}`

## Parámetro
- `rol`: puede ser `CLIENTE`, `COMERCIO` o `ADMIN`

## Ejemplo 1 - CLIENTE
### Request
`GET /api/menu?rol=CLIENTE`

### Response
```json
{
  "rol": "CLIENTE",
  "items": [
    "catalogo",
    "carrito",
    "mis_pedidos",
    "mi_perfil"
  ]
}

## Ejemplo 2 - COMERCIO
Request

GET /api/menu?rol=COMERCIO

Response
{
  "rol": "COMERCIO",
  "items": [
    "mis_productos",
    "publicar_producto",
    "pedidos_recibidos",
    "mi_perfil"
  ]
}

## Ejemplo 3 - ADMIN
Request

GET /api/menu?rol=ADMIN

Response
{
  "rol": "ADMIN",
  "items": [
    "usuarios",
    "comercios",
    "reportes",
    "configuracion"
  ]
}

## Flujo esperado en Android
1. El usuario inicia sesión.
2. Android obtiene el rol desde `tipoUsuario.descripcion` en la respuesta del login.
3. Android llama a `/api/menu?rol={ROL}`.
4. Android muestra las opciones devueltas en `items`.

Nota
Este endpoint devuelve una versión simple de menú por rol para facilitar la integración inicial con Android.