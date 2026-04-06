# Guía 2: Práctica Spring Web

## 1. Introducción y Objetivos
El objetivo de esta práctica es familiarizarse con el **flujo de datos** en una aplicación Spring Boot, implementando la capa de **Servicios** y la capa de **Controladores**.

Trabajaremos con persistencia en memoria (listas) para entender cómo estructurar un proyecto bajo el patrón de diseño multicapa.

---

## 2. Estructura del Proyecto
El proyecto base ya cuenta con los siguientes componentes:

* **Modelos (Entities):** `UserEntity`, `ProductEntity` y `SaleEntity`.
* **Repositorios:** Clases que gestionan las listas en memoria.
* **Servicios Base:** `UserService` y `ProductService` (implementaciones básicas para referencia).

---

## 3. Completar la Lógica de Negocio (`SaleService`)
Antes de crear el controlador de ventas, debemos definir las reglas de negocio. Se debe crear la clase `SaleService` y desarrollar los siguientes métodos:

1.  **Listar ventas:** Retornar todas las ventas registradas.
2.  **Registrar Venta:**
    * Recibir un ID de producto, un ID de cliente y la cantidad.
    * Validar que el producto y el cliente existan usando sus respectivos servicios.
    * Construir la `SaleEntity` (puedes usar el `.builder()` de Lombok).
    * Persistir la venta a través del `SaleRepository`.
3.  **Eliminar Venta:** Validar existencia antes de proceder.
4.  **Actualizar Venta:** Validar existencia antes de proceder.

---

## 4. Exponer la API (Controllers)
Deberán implementar tres controladores: `UserController`, `ProductController` y `SaleController`. Cada uno debe manejar las operaciones CRUD utilizando las anotaciones de **Spring Web**.

### Requerimientos Técnicos
* **Anotaciones de Clase:** `@RestController` y `@RequestMapping`.
* **Inyección de Dependencias:** Inyectar los servicios correspondientes **mediante constructor** (evitar el uso de `@Autowired` sobre atributos).

### Endpoints a Implementar

| Método | Endpoint | Acción |
| :--- | :--- | :--- |
| **GET** | `/api/[recurso]` | Obtener todos los elementos. |
| **GET** | `/api/[recurso]/{id}` | Obtener un elemento por su ID. |
| **POST** | `/api/[recurso]` | Crear un nuevo recurso. |
| **PUT** | `/api/[recurso]/{id}` | Actualizar un recurso existente (sobreescritura). |
| **DELETE** | `/api/[recurso]/{id}` | Eliminar un recurso. |

---

## 5. Desafío: Gestión de Respuestas y Estados HTTP
Para una API profesional, no basta con devolver datos; debemos informar el estado de la operación utilizando códigos de estado adecuados:

* **200 OK:** Para búsquedas y actualizaciones exitosas.
* **201 Created:** Al crear un nuevo recurso.
* **204 No Content:** Al eliminar un recurso con éxito.
* **404 Not Found:** Si el ID solicitado no existe en la lista.

> [!TIP]
> Utilicen la clase `ResponseEntity<T>` para envolver sus retornos en los métodos del controlador y personalizar el código de estado.

---

## 6. Pruebas y Entrega
Una vez completado el código, deberán probar los endpoints utilizando herramientas como:
* **Postman** o **Insomnia**.
* Extensión **REST Client** de VS Code.
* **Thunder Client**.
