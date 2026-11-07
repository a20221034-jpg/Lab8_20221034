<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pe.edu.pucp.iweb.lab8.beans.Genero" %>
<jsp:useBean id="generos" type="java.util.ArrayList<pe.edu.pucp.iweb.lab8.beans.Genero>" scope="request"/>
<%
    String accion = (String) request.getAttribute("accion");
    String idGeneroSeleccionado = (String) request.getAttribute("idgenero");
    String error = (String) request.getAttribute("error");
    boolean esEdicion = "actualizar".equals(accion);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Canción</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<h1><%= esEdicion ? "Editar canción" : "Nueva canción" %></h1>

<% if (error != null) { %>
<p class="error"><%= error %></p>
<% } %>

<form method="post" action="<%=request.getContextPath()%>/canciones">
    <input type="hidden" name="action" value="<%= accion %>">
    <% if (esEdicion) { %>
    <input type="hidden" name="id" value="${id}">
    <% } %>

    <label>Título:</label>
    <input type="text" name="titulo" value="${titulo}"><br>

    <label>Artista:</label>
    <input type="text" name="artista" value="${artista}"><br>

    <label>Duración (segundos):</label>
    <input type="text" name="duracion" value="${duracion}"><br>

    <label>Año:</label>
    <input type="text" name="anio" value="${anio}"><br>

    <label for="idgenero">Género:</label>
    <select name="idgenero" id="idgenero">
        <% for (Genero genero : generos) {
               String valor = String.valueOf(genero.getIdGenero()); %>
        <option value="<%= valor %>" <%= valor.equals(idGeneroSeleccionado) ? "selected" : "" %>><%= genero.getNombre() %></option>
        <% } %>
    </select><br>

    <button type="submit">Guardar</button>
    <a href="<%=request.getContextPath()%>/canciones">Cancelar</a>
</form>
</body>
</html>
