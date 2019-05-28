/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raguiarperez
 */
public class Metodos {
        //Método de conexión a la base de datos, especificando la url

    /**
     *
     * @return
     */
    public static Connection conectar() {
        String url = "jdbc:sqlite:Rally.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
        public static ResultSet getTabla(String consulta) throws SQLException{
        Connection conn=conectar();
        Statement st;
        ResultSet datos= null;
        try{
            st=conn.createStatement();
            datos=st.executeQuery(consulta);
        }catch(Exception e){
            System.out.println(e.toString());
        }
       return datos;
    }
    

    //Método de creación de la tabla Rally

    /**
     *
     */
    public static void crearTablaCompetición() {
        String sql1 = "DROP TABLE IF EXISTS rally;\n";
        String sql2 = "CREATE TABLE IF NOT EXISTS rally (\n"
                + "Dorsal integer PRIMARY KEY,\n"
                + "Piloto text NOT NULL,\n"
                + "Vehiculo text NOT NULL,\n"
                + "Categoria integer,\n"
                + "FOREIGN KEY (Categoria)\n" 
                + "REFERENCES Categoria(id)\n"
                + ");";    
        try (Connection conn = conectar();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql1);
            stmt.execute(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Método para insertar filas en la tabla Rally

    /**
     *
     * @param Dorsal
     * @param Piloto
     * @param Vehiculo
     * @param curso
     */
    public static void insertarCompetidor(int Dorsal, String Piloto, String Vehiculo, int Categoria) {
        String sql = "INSERT INTO rally VALUES(?,?,?,?)";
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Dorsal);
            pstmt.setString(2, Piloto);
            pstmt.setString(3, Vehiculo);
            pstmt.setInt(4, Categoria);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Competidor registrado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al introducir los datos");
        }
    }
//    public static ArrayList<Object>resultaTabla(){
//        String sql="SELECT Dorsal,Piloto,Vehiculo,Categoria from rally";
//        try (Connection conn = conectar();
//        Statement stmt = conn.createStatement();
//        ResultSet rs  = stmt.executeQuery(sql)) {
//            int Dorsal, Categoria;
//            String Piloto, Vehiculo;
//            ArrayList<Object> comp = new ArrayList<>();
//            comp.clear();
//            while (rs.next()) {
//                comp.add(rs.getInt("Dorsal") +  "," + 
//                                   rs.getString("Piloto") + "," +
//                                   rs.getString("Vehiculo") + "," +
//                                   rs.getInt("Categoria"));
//            }
//            return comp;
//           
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//    public static void actualizarTablaCompetidores(JTable a){
//        DefaultTableModel model =(DefaultTableModel) a.getModel();
//        Object O[]=null;
//        model.setRowCount(0);
//        ArrayList<Object> comp = new ArrayList<>();
//        comp=resultaTabla();
//        for (int i = 0; i < comp.size(); i++) {
//            model.addRow(O);
//            Object getO=comp.get(i);
//            model.setValueAt(getO., i, i);
//            
//        }
////        
//    }
//    
    
    
    //Método para modificar el nombre y nota de una fila de la tabla rally,
    //especificando el dorsal

    /**
     *
     * @param nome
     * @param nota
     * @param referencia
     */
    public static void modificarCompetidor(String Piloto, String Vehiculo, int Dorsal) {
        String sql = "UPDATE rally SET Piloto = ? , "
                + "Vehiculo = ? "
                + "WHERE Dorsal = ?";
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Piloto);
            pstmt.setString(2, Vehiculo);
            pstmt.setInt(3, Dorsal);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Competidor modificado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Método para modificar el piloto, vehiculo y categoria de una fila de la tabla rally,
    //especificando el id

    /**
     *
     * @param Piloto
     * @param Vehiculo
     * @param Categoria
     * @param Dorsal
     */
    public static void modificarCategoriaCompetidor(String Piloto, String Vehiculo, int Categoria, int Dorsal) {
        String sql = "UPDATE rally SET Piloto = ? , "
                + "Vehiculo = ? , "
                + "Categoria = ?"
                + "WHERE Dorsal = ?";
        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Piloto);
            pstmt.setString(2, Vehiculo);
            pstmt.setInt(3, Categoria);
            pstmt.setInt(4, Dorsal);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Competidor modificado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Método para borrar una fila de la tabla rally, especificando el id

    /**
     *
     * @param Dorsal
     */
    public static void borrarCompetidor(int Dorsal){
        String sql = "DELETE FROM rally WHERE Dorsal=?";
        try (Connection conn = conectar();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1, Dorsal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Método para buscar filas de la tabla rally,
    //especificando cualquiera de sus campos
    //Devuelve un ArrayList con todos los campos de las filas

    /**
     *
     * @param campo
     * @param valor
     * @return
     */
    public static ArrayList<String> consultaRally(String campo, Object valor){
        ArrayList<String> rally = new ArrayList<>();
        String sql = "SELECT Dorsal,Piloto,Vehiculo,Categoria"
        + " FROM rally WHERE " + campo + "=?";
        try (Connection conn = conectar();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setObject(1, valor);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                rally.add(rs.getInt("Dorsal") +  "," + 
                                   rs.getString("Piloto") + "," +
                                   rs.getString("Vehiculo") + "," +
                                   rs.getInt("Categoria"));
            }
            return rally;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return rally;
        }
    }
    
    //Método que devuelve un String con todos los campos de una fila,
    //especificando el id

    /**
     *
     * @param id
     * @return
     */
    public static String devolverCompetidor(int Dorsal){
        String sql = "SELECT Dorsal,Piloto,Vehiculo,Categoria"
        + " FROM rally WHERE Dorsal=?";
        String resultado = "";
        try (Connection conn = conectar();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1,Dorsal);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                resultado = (rs.getInt("Dorsal") +  "," + 
                                   rs.getString("Piloto") + "," +
                                   rs.getString("Vehiculo") + "," +
                                   rs.getInt("Categoria"));
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return resultado;
        }
    }
    
    //Método de creación de la tabla Categorias

    /**
     *
     */
    public static void crearTablaCategorias() {
        String sql1 = "DROP TABLE IF EXISTS Categoria;\n";
        String sql = "CREATE TABLE IF NOT EXISTS Categoria (\n"
                + "id integer PRIMARY KEY,\n"
                + "nombre text NOT NULL\n"
                + ");";
        try (Connection conn = conectar();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql1);
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Método para insertar las filas de la tabla categorias
    //La tabla no es modificable y viene con estas filas por defecto

    /**
     *
     */
    public static void insertarCategorias() {
        String sql1 = "INSERT INTO Categoria VALUES(1,'N5');";
        String sql2 = "INSERT INTO Categoria VALUES(2,'R5');";
        String sql3 = "INSERT INTO Categoria VALUES(3,'GRUPO B');";
        String sql4 = "INSERT INTO Categoria VALUES(4,'WRC');";
        try (Connection conn = conectar();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.execute(sql4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Método que devuelve el nombre de la categoria, especificando el id

    /**
     *
     * @param id
     * @return
     */
    public static String obtenerNombreCategoria(int id){
        String sql = "SELECT nombre FROM Categoria where id = ?;";
        String resultado = "";
        try (Connection conn = conectar();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                resultado = rs.getString("nombre");
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return resultado;
        }
    }
    
    public static int obtenerIdCategoria(String nombre){
        String sql = "SELECT id FROM Categoria where nombre = ?;";
        int resultado = 0;
        try (Connection conn = conectar();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                resultado = rs.getInt("id");
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return resultado;
        }
    }
    
    //Método que devuelve un String con todos los campos de una fila,
    //especificando el id

    /**
     *
     * @param id
     * @return
     */
    public static String devolverCategoria(int id){
        String sql = "SELECT id,nombre"
        + " FROM Categoria WHERE id=?";
        String resultado = "";
        try (Connection conn = conectar();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                resultado = (rs.getInt("id") +  "," + 
                                   rs.getString("nombre"));
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return resultado;
        }
    }

}


