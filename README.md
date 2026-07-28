# Proyecto Final - 3°A POO/Tutoría

## Descripción del proyecto

_Agregar aquí una breve descripción de la API/proyecto final desarrollado durante el semestre (qué hace, principales funcionalidades, tecnologías usadas: lenguaje, framework, base de datos, etc.)._

## Integrantes y ramas de trabajo

Cada integrante del equipo trabajó en su propia rama del repositorio.

| Integrante | Rama            | Aportación principal                          |
|------------|------------------|------------------------------------------------|
| _Nombre 1_ | `nombre1`        | _Ej. Endpoint de login y validación de usuario_ |
| _Nombre 2_ | `nombre2`        | _Ej. Generación y validación del token JWT_     |
| _Nombre 3_ | `nombre3`        | _Ej. Protección de endpoints_                   |

## Tarea 3 - Autenticación mediante tokens (JWT)

### ¿Qué es un token JWT?

_Explicación breve: un JWT (JSON Web Token) es un estándar para transmitir información de forma segura entre dos partes como un objeto JSON firmado digitalmente. Se compone de tres partes: header, payload y signature._

### ¿Cómo funciona dentro de una API?

_Explicación breve: el cliente envía sus credenciales al endpoint de login, el servidor las valida y responde con un token JWT. En las siguientes peticiones, el cliente envía ese token (normalmente en el header `Authorization: Bearer <token>`) y el servidor lo valida antes de procesar la solicitud._

### Diferencia entre autenticación y autorización

_Explicación breve: la autenticación verifica quién es el usuario (login con usuario y contraseña); la autorización determina qué puede hacer ese usuario una vez autenticado (permisos, roles, acceso a recursos)._

### Implementación

La implementación incluye:

- Endpoint de inicio de sesión (`POST /login` o similar) que recibe las credenciales del usuario.
- Validación del usuario y la contraseña.
- Generación de un token JWT que contiene el identificador del usuario, su rol y una fecha de expiración.
- Protección de al menos dos endpoints de la API mediante verificación del token.

### Pruebas realizadas en Postman

Se documentaron los siguientes casos de prueba (ver capturas en el PDF de entrega):

1. Inicio de sesión correcto y generación del token.
2. Inicio de sesión con credenciales incorrectas.
3. Acceso a un endpoint sin enviar el token.
4. Acceso correcto utilizando un token válido.
5. Acceso utilizando un token alterado o vencido.

### Ventajas de utilizar tokens para proteger una API

_Conclusión breve: por ejemplo, no requiere mantener sesiones en el servidor (stateless), es escalable, permite incluir información del usuario (rol, permisos) directamente en el token, y facilita la integración con distintos clientes (web, móvil, etc.)._

## Tarea 4 - Control de versiones

### ¿Qué es el control de versiones?

_Explicación breve: es un sistema que permite llevar un registro de los cambios realizados en el código a lo largo del tiempo, facilitando el trabajo colaborativo y la posibilidad de volver a versiones anteriores si es necesario._

### Diferencia entre Git y GitHub

_Explicación breve: Git es el sistema de control de versiones (herramienta local que registra los cambios); GitHub es una plataforma en la nube que aloja repositorios Git y añade funcionalidades de colaboración como pull requests, issues y revisión de código._

### Ramas, commits y pull requests

_Explicación breve:_
- _Rama (branch): una línea de desarrollo independiente que permite trabajar en una funcionalidad sin afectar el código principal (main)._
- _Commit: un registro de los cambios realizados en el código, con un mensaje descriptivo._
- _Pull request: una solicitud para fusionar los cambios de una rama hacia otra (normalmente hacia main), permitiendo revisión antes de integrarlos._

### Aplicación al proyecto

- Repositorio de GitHub: https://github.com/Z3K-Erick/final
- Una rama por integrante (ver tabla de integrantes arriba).
- Al menos dos commits por integrante.
- Una pull request para integrar los cambios a `main`.

### Ventajas del control de versiones

_Conclusión breve: por ejemplo, permite trabajo colaborativo simultáneo sin sobrescribir el trabajo de otros, mantiene un historial de cambios, facilita revertir errores, y permite revisar el código antes de integrarlo (pull requests)._

## Cómo ejecutar el proyecto

```bash
git clone https://github.com/Z3K-Erick/final.git
cd final
# Instrucciones específicas de instalación y ejecución
```
