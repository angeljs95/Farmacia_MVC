package Dao;

import Entitys.Purchases;
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
public class PurchasesDao {
    
          //iniciamos la conexion
    ConnectionMySQL connectionDB= new ConnectionMySQL();
    Connection connection;
    PreparedStatement pst;
    ResultSet result;
  
        //----------Registrar compra---------------
    
     public boolean registerPurchase(int supplier_id, int employee_id, double total){
        String query = "INSERT INTO purchases (supplier_id, employee_id, total, created)"
                + "VALUES (?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, supplier_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, datetime);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error inserting purchase");
            return false;
        }
    }
     // -----------Detalles de compras--------------------
     
     public boolean registerPurchaseDetail(int purchase_id, double purchase_price, int purchase_amount, 
            double purchase_subtotal, int product_id){
    
        String query = "INSERT INTO purchase_details (purchase_id, purchase_price, purchase_amount"
                + ",purchase_subtotal, product_id) VALUES(?,?,?,?,?)";
       
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, purchase_id);
            pst.setDouble(2, purchase_price);
            pst.setInt(3, purchase_amount);
            pst.setDouble(4, purchase_subtotal);
            pst.setInt(5, product_id);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error registering purchase details " + e);
            return false;
        }
     }
        
           
    //----------------Obtener id de la compra----------------
    public int purchaseId(){
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            result = pst.executeQuery();
            if(result.next()){
                id = result.getInt("id");
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return id;
    }
   
    //----------listar todas las  compras--------------
    
      public List listAllPurchases(){
        List<Purchases> listAllPurchases = new ArrayList();
        //String query = "SELECT pu.*, su.name AS supplier_name FROM purchases pu, suppliers su "
         //       + "WHERE pu.supplier_id = su.id ORDER BY pu.id ASC";
         String query= "SELECT pu.id, su.name AS supplier_name, pu.total, pu.created FROM purchases pu, suppliers su"
                 + " WHERE pu.supplier_id = su.id ORDER BY pu.id ASC";
        
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            result = pst.executeQuery();
            
            while(result.next()){
                Purchases purchase = new Purchases();
                purchase.setId(result.getInt("id"));
                purchase.setSupplier_name_product(result.getString("supplier_name"));
                purchase.setTotal(result.getDouble("total"));
                purchase.setCreated(result.getString("created"));
                listAllPurchases.add(purchase);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return listAllPurchases;
    }
  //-----------------Listar compras para imprimir la factura---------------
      //Listar compras para imprimir factura
    public List listPurchaseDetail(int id){
        List<Purchases> listPurchases = new ArrayList();
        String query = "SELECT pu.created, pude.purchase_price, pude.purchase_amount, pude.purchase_subtotal, su.name AS supplier_name,\n" +
                        "pro.name AS product_name, em.full_name FROM purchases pu INNER JOIN purchase_details pude ON pu.id = pude.purchase_id\n" +
                        "INNER JOIN products pro ON pude.product_id = pro.id INNER JOIN suppliers su ON pu.supplier_id = su.id\n" +
                        "INNER JOIN employees em ON pu.employee_id = em.id WHERE pu.id = ?";
        
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            result = pst.executeQuery();
            
            while(result.next()){
                Purchases purchase = new Purchases();
                purchase.setProductName(result.getString("product_name"));
                purchase.setPurchaseAmount(result.getInt("purchase_amount"));
                purchase.setPurchasePrice(result.getDouble("purchase_price"));
                purchase.setPurchaseSubtotal(result.getDouble("purchase_subtotal"));
                purchase.setSupplier_name_product(result.getString("supplier_name"));
                purchase.setCreated(result.getString("created"));
                purchase.setPurcharser(result.getString("full_name"));
                listPurchases.add(purchase);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return listPurchases;
    }
    

}