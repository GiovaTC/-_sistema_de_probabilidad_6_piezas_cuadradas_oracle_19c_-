package dao;

import conexion.ConexionOracle;
import modelo.Lanzamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LanzamientoDAO {

    public void guardar(Lanzamiento l) {

        String sql = """
                INSERT INTO LANZAMIENTO_PIEZAS
                (
                PIEZA1,
                PIEZA2,
                PIEZA3,
                PIEZA4,
                PIEZA5,
                PIEZA6,
                CARAS_A,
                CARAS_B,
                PROBABILIDAD
                )
                VALUES
                (?,?,?,?,?,?,?,?,?)
                """;

        try(
                Connection cn =
                        ConexionOracle.conectar();

                PreparedStatement ps =
                        cn.prepareStatement(sql)
        ) {

            ps.setInt(1,l.getPieza1());
            ps.setInt(2,l.getPieza2());
            ps.setInt(3,l.getPieza3());
            ps.setInt(4, l.getPieza4());
            ps.setInt(5, l.getPieza5());
            ps.setInt(6, l.getPieza6());

            ps.setInt(7, l.getCarasA());
            ps.setInt(8, l.getCarasB());

            ps.setDouble(9, l.getProbabilidad());

            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
