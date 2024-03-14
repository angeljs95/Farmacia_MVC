package Dao;

import Entitys.Suppliers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AngelJs
 */
public class SuppliersDao {
    //iniciamos la conexion
    ConnectionMySQL connectionDB= new ConnectionMySQL();
    Connection connection;
    PreparedStatement pst;
    ResultSet result;
    
    // ------------Registar proveedor---------------
    public boolean registerSupplier(Suppliers supplier){
        String query = "INSERT INTO suppliers (name, description, address, telephone, email, city, created, updated)"
                + "VALUES(?,?,?,?,?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getPhone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error registeryng supplier");
            return false;
        }
    }
    
    //-------------listar proveedores------------
    
     public List listSuppliers(String value){
        List<Suppliers> listSuppliers = new ArrayList();
        String query = "SELECT * FROM suppliers";
        String query_search_supplier = "SELECT * FROM suppliers WHERE name LIKE '%" + value + "%'";
        try{
           connection = connectionDB.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = connection.prepareStatement(query);
                result = pst.executeQuery();
            } else {
                pst = connection.prepareStatement(query_search_supplier);
                result = pst.executeQuery();
            } 
            
            while(result.next()){
                Suppliers supplier = new Suppliers();
                supplier.setId(result.getInt("id"));
                supplier.setName(result.getString("name"));
                supplier.setDescription(result.getString("description"));
                supplier.setAddress(result.getString("address"));
                supplier.setPhone(result.getString("telephone"));
                supplier.setEmail(result.getString("email"));
                supplier.setCity(result.getString("city"));
                listSuppliers.add(supplier);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return listSuppliers;
    }
     
     
    //-------------- modificar proveedor---------------
     
     public boolean updateSupplier(Suppliers supplier){
        String query = "UPDATE suppliers SET name = ?, description = ?, address = ?, telephone = ?,"
                + "email = ?, city = ?, updated = ? WHERE id = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getPhone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error modifying supplier data");
            return false;
        }
    }
     
    //-------------- eliminar proveedor--------------
      public boolean deleteSupplier(int id){
        String query = "DELETE FROM suppliers WHERE id = " + id;
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "You cannot delete a supplier that is related to another table");
            return false;
        }
    }
     
}
