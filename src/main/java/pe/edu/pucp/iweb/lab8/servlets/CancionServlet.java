package pe.edu.pucp.iweb.lab8.servlets;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import pe.edu.pucp.iweb.lab8.beans.Cancion;
import pe.edu.pucp.iweb.lab8.beans.Genero;
import pe.edu.pucp.iweb.lab8.dao.CancionDao;
import pe.edu.pucp.iweb.lab8.dao.GeneroDao;

@WebServlet(name = "CancionServlet", urlPatterns = {"/canciones"})
public class CancionServlet extends HttpServlet {

    private final CancionDao cancionDao = new CancionDao();
    private final GeneroDao generoDao = new GeneroDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "nuevo":
                nuevo(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "crear":
                crear(request, response);
                break;
            case "actualizar":
                actualizar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/canciones");
                break;
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Cancion> lista = cancionDao.listar();

        int sumaSegundos = 0;
        for (Cancion cancion : lista) {
            sumaSegundos = sumaSegundos + cancion.getDuracionSeg();
        }
        Cancion total = new Cancion();
        total.setDuracionSeg(sumaSegundos);

        request.setAttribute("lista", lista);
        request.setAttribute("totalCanciones", lista.size());
        request.setAttribute("duracionTotal", total.getDuracionFormateada());

        request.getRequestDispatcher("listar.jsp").forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("generos", generoDao.listar());
        request.setAttribute("accion", "crear");
        request.setAttribute("id", "");
        request.setAttribute("titulo", "");
        request.setAttribute("artista", "");
        request.setAttribute("duracion", "");
        request.setAttribute("anio", "");
        request.setAttribute("idgenero", "");

        request.getRequestDispatcher("form.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = convertirAEntero(request.getParameter("id"));
        Cancion cancion = null;
        if (id > 0) {
            cancion = cancionDao.buscarPorId(id);
        }

        // Si el id no es un número o la canción no existe, se regresa al listado
        if (cancion == null) {
            response.sendRedirect(request.getContextPath() + "/canciones");
            return;
        }

        request.setAttribute("generos", generoDao.listar());
        request.setAttribute("accion", "actualizar");
        request.setAttribute("id", String.valueOf(cancion.getIdCancion()));
        request.setAttribute("titulo", cancion.getTitulo());
        request.setAttribute("artista", cancion.getArtista());
        request.setAttribute("duracion", String.valueOf(cancion.getDuracionSeg()));
        request.setAttribute("anio", String.valueOf(cancion.getAnio()));
        request.setAttribute("idgenero", String.valueOf(cancion.getGenero().getIdGenero()));

        request.getRequestDispatcher("form.jsp").forward(request, response);
    }

    private void crear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        procesarFormulario(request, response, "crear");
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        procesarFormulario(request, response, "actualizar");
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = convertirAEntero(request.getParameter("id"));
        if (id > 0) {
            cancionDao.eliminar(id);
        }
        response.sendRedirect(request.getContextPath() + "/canciones");
    }

    /**
     * Valida los datos del formulario
     * Si hay algún error se vuelve a mostrar el formulario con el mensaje y sin perder lo que el usuario escribió */

    private void procesarFormulario(HttpServletRequest request, HttpServletResponse response,
                                    String accion) throws ServletException, IOException {

        String titulo = limpiar(request.getParameter("titulo"));
        String artista = limpiar(request.getParameter("artista"));
        String duracionTexto = limpiar(request.getParameter("duracion"));
        String anioTexto = limpiar(request.getParameter("anio"));
        String generoTexto = limpiar(request.getParameter("idgenero"));
        String idTexto = limpiar(request.getParameter("id"));

        int duracion = convertirAEntero(duracionTexto);
        int anio = convertirAEntero(anioTexto);
        int idGenero = convertirAEntero(generoTexto);
        int idCancion = convertirAEntero(idTexto);

        // Validaciones
        String error = null;
        if (titulo.isEmpty()) {
            error = "El título no puede estar vacío.";
        } else if (artista.isEmpty()) {
            error = "El artista no puede estar vacío.";
        } else if (duracion <= 0) {
            error = "La duración debe ser un número mayor que 0.";
        } else if (anio < 1900 || anio > 2026) {
            error = "El año debe estar entre 1900 y 2026.";
        } else if (idGenero <= 0) {
            error = "Debe seleccionar un género.";
        } else if (accion.equals("actualizar") && idCancion <= 0) {
            error = "La canción que intenta actualizar no es válida.";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("generos", generoDao.listar());
            request.setAttribute("accion", accion);
            request.setAttribute("id", idTexto);
            request.setAttribute("titulo", titulo);
            request.setAttribute("artista", artista);
            request.setAttribute("duracion", duracionTexto);
            request.setAttribute("anio", anioTexto);
            request.setAttribute("idgenero", generoTexto);
            request.getRequestDispatcher("form.jsp").forward(request, response);
            return;
        }

        Genero genero = new Genero();
        genero.setIdGenero(idGenero);

        Cancion cancion = new Cancion();
        cancion.setTitulo(titulo);
        cancion.setArtista(artista);
        cancion.setDuracionSeg(duracion);
        cancion.setAnio(anio);
        cancion.setGenero(genero);

        if (accion.equals("actualizar")) {
            cancion.setIdCancion(idCancion);
            cancionDao.actualizar(cancion);
        } else {
            cancionDao.crear(cancion);
        }

        response.sendRedirect(request.getContextPath() + "/canciones");
    }

    /** Convierte un texto a número; si no se puede, retorna -1. */
    private int convertirAEntero(String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private String limpiar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim();
    }
}
