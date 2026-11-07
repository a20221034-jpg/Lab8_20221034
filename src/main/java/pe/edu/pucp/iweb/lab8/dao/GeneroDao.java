package pe.edu.pucp.iweb.lab8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pe.edu.pucp.iweb.lab8.beans.Genero;

public class GeneroDao extends BaseDao {

    public ArrayList<Genero> listar() {
        ArrayList<Genero> lista = new ArrayList<>();
        String sql = "SELECT idgenero, nombre FROM genero ORDER BY nombre";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Genero genero = new Genero();
                genero.setIdGenero(rs.getInt("idgenero"));
                genero.setNombre(rs.getString("nombre"));
                lista.add(genero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
