package pe.edu.pucp.iweb.lab8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pe.edu.pucp.iweb.lab8.beans.Cancion;
import pe.edu.pucp.iweb.lab8.beans.Genero;

public class CancionDao extends BaseDao {

    public ArrayList<Cancion> listar() {
        ArrayList<Cancion> lista = new ArrayList<>();
        String sql = "SELECT c.idcancion, c.titulo, c.artista, c.duracion_seg, c.anio, "
                + "g.idgenero, g.nombre "
                + "FROM cancion c INNER JOIN genero g ON c.idgenero = g.idgenero "
                + "ORDER BY c.titulo";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(this.crearCancionDesdeResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Cancion buscarPorId(int idCancion) {
        Cancion cancion = null;
        String sql = "SELECT c.idcancion, c.titulo, c.artista, c.duracion_seg, c.anio, "
                + "g.idgenero, g.nombre "
                + "FROM cancion c INNER JOIN genero g ON c.idgenero = g.idgenero "
                + "WHERE c.idcancion = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCancion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cancion = this.crearCancionDesdeResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cancion;
    }

    public void crear(Cancion cancion) {
        String sql = "INSERT INTO cancion (titulo, artista, duracion_seg, anio, idgenero) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cancion.getTitulo());
            pstmt.setString(2, cancion.getArtista());
            pstmt.setInt(3, cancion.getDuracionSeg());
            pstmt.setInt(4, cancion.getAnio());
            pstmt.setInt(5, cancion.getGenero().getIdGenero());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Cancion cancion) {
        String sql = "UPDATE cancion SET titulo = ?, artista = ?, duracion_seg = ?, "
                + "anio = ?, idgenero = ? WHERE idcancion = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cancion.getTitulo());
            pstmt.setString(2, cancion.getArtista());
            pstmt.setInt(3, cancion.getDuracionSeg());
            pstmt.setInt(4, cancion.getAnio());
            pstmt.setInt(5, cancion.getGenero().getIdGenero());
            pstmt.setInt(6, cancion.getIdCancion());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idCancion) {
        String sql = "DELETE FROM cancion WHERE idcancion = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCancion);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cancion crearCancionDesdeResultSet(ResultSet rs) throws SQLException {
        Genero genero = new Genero();
        genero.setIdGenero(rs.getInt("idgenero"));
        genero.setNombre(rs.getString("nombre"));

        Cancion cancion = new Cancion();
        cancion.setIdCancion(rs.getInt("idcancion"));
        cancion.setTitulo(rs.getString("titulo"));
        cancion.setArtista(rs.getString("artista"));
        cancion.setDuracionSeg(rs.getInt("duracion_seg"));
        cancion.setAnio(rs.getInt("anio"));
        cancion.setGenero(genero);

        return cancion;
    }
}
