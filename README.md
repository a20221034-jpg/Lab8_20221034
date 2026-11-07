# Laboratorio 8 – Proyecto base (TEL131)

Proyecto base del Octavo Laboratorio de Ingeniería Web para Telecomunicaciones.
Complete los `TODO` según el enunciado. **No cambie los nombres de paquetes, clases ni métodos.**

## Requisitos

- JDK 17 o superior
- Tomcat 10.1 o superior (Jakarta Servlet 6)
- MySQL 8

## Pasos iniciales

1. Ejecute `db/melodiape.sql` en MySQL.
2. En `BaseDao.java`, cambie la contraseña (`PASS`) por la de su MySQL.
3. Abra el proyecto en su IDE como proyecto Maven y despliéguelo en Tomcat
   con el *context path* `/lab8-iweb`.
4. Ingrese a `http://localhost:8080/lab8-iweb/canciones`.

El archivo `.gitignore` **no** está incluido: créelo usted (Ejercicio 1).

## Estructura

```
db/melodiape.sql                      script de la base de datos
src/main/java/pe/edu/pucp/iweb/lab8/
  beans/     Genero (completo), Cancion
  dao/       BaseDao, GeneroDao, CancionDao
  servlets/  CancionServlet            /canciones
src/main/webapp/
  listar.jsp / form.jsp / confirmar.jsp
  css/estilos.css
  index.jsp                            redirige a /canciones
```

## URLs del servlet

| Método | URL | Qué hace |
|--------|-----|----------|
| GET  | `/canciones`                       | listado |
| GET  | `/canciones?q=texto`               | listado filtrado |
| GET  | `/canciones?action=nuevo`          | formulario vacío |
| GET  | `/canciones?action=editar&id=3`    | formulario cargado |
| GET  | `/canciones?action=confirmar&id=3` | confirmar eliminación |
| POST | `action=crear`                     | inserta |
| POST | `action=actualizar`                | actualiza |
| POST | `action=eliminar`                  | elimina |

| TODO | Ejercicio |
|------|-----------|
| 2a   | BaseDao abstracta y herencia de los DAO |
| 2b   | Bean Cancion |
| 2c / 2d | Consultas de GeneroDao y CancionDao |
| 3    | Listado, crear, editar, combobox y CSS |
| 4    | Eliminar, buscar, mensajes y errores |
