/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rubenpengim.dao;

import rubenpengim.model.Producto;
import java.sql.*;

/**
 *
 * @author user
 */
public class ProductoDAOImpl implements ProductoDAO {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/m3apirestrpg?useSSL=false";
    static final String DB_USR = "root";
    static final String DB_PWD = "";

    /**
     * Load the driver class
     */
    private void registerDriver() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            //System.out.println("ERROR: failed to load MySQL JDBC Driver");
            System.err.println("ERROR: failed to load MySQL JDBC Driver"); // imprime en la salida standar de error
            ex.printStackTrace();
        }
    }

    public void insert(Producto producto) {
        Connection conn = null;
        try {
            registerDriver();
            // abrir la conexión
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            Statement stmt = conn.createStatement();
            // enviar el comando insert
            stmt.executeUpdate("insert into producto values ("
                    + producto.getId() + ",'"
                    + producto.getName() + "',"
                    + producto.getPrice() + ");");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void update(Producto product) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            try ( PreparedStatement ps = conn.prepareStatement("update producto set name = ?, price = ? where id = ?")) {
                ps.setString(1, product.getName());
                ps.setDouble(2, product.getPrice());
                ps.setInt(3, product.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void delete(Integer id) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            try ( PreparedStatement ps = conn.prepareStatement("delete from producto where id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public Producto read(Integer id) {
        Connection conn = null;
        Producto prod = null;
        try {
            registerDriver();
            // abrir la conexión
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            // consulta select (selecciona el producto con id especificado)
            try ( PreparedStatement ps = conn.prepareStatement("select * from producto where id = ?")) {
                //indicar el id que buscamos
                ps.setInt(1, id);
                //ejecutar la consulta
                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // obtener cada una de las columnas y mapearlas a la clase Product
                        prod = new Producto(id, rs.getString("name"), rs.getDouble("price"));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return prod;
    }

}
