<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pe.edu.pucp.iweb.lab8.beans.Cancion" %>
<jsp:useBean id="lista" type="java.util.ArrayList<pe.edu.pucp.iweb.lab8.beans.Cancion>" scope="request"/>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de canciones</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<h1>Catálogo de canciones – MelodiaPE</h1>

<p><a href="<%=request.getContextPath()%>/canciones?action=nuevo">Nueva canción</a></p>

<table>
    <thead>
    <tr>
        <th>Título</th>
        <th>Artista</th>
        <th>Género</th>
        <th>Año</th>
        <th>Duración</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <% for (Cancion cancion : lista) { %>
    <tr>
        <td><%=cancion.getTitulo()%></td>
        <td><%=cancion.getArtista()%></td>
        <td><%=cancion.getGenero().getNombre()%></td>
        <td><%=cancion.getAnio()%></td>
        <td><%=cancion.getDuracionFormateada()%></td>
        <td>
            <a href="<%=request.getContextPath()%>/canciones?action=editar&id=<%=cancion.getIdCancion()%>">Editar</a>
            <form method="post" action="<%=request.getContextPath()%>/canciones">
                <input type="hidden" name="action" value="eliminar">
                <input type="hidden" name="id" value="<%=cancion.getIdCancion()%>">
                <button type="submit">Eliminar</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

<p>Total de canciones: ${totalCanciones}</p>
<p>Duración total: ${duracionTotal}</p>
</body>
</html>
