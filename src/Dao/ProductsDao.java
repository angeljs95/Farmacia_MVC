
package Dao;

import Entitys.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AngelJs
 */
public class ProductsDao {
    
       //iniciamos la conexion
    ConnectionMySQL connectionDB= new ConnectionMySQL();
    Connection connection;
    PreparedStatement pst;
    ResultSet result;
    
    //----------Registrar productos---------------
    
    public boolean registerProduct(Products product){
        String query = "INSERT INTO products (code, name, description, unit_price, created, updated, category_id)"
                + "VALUES(?,?,?,?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnitPrice());
            pst.setTimestamp(5, datetime);
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategoryId());
           // pst.setInt(8, product.getCategoryName());
            pst.execute();
            return true;
                    
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error  registeryng products");
            return false;
        }
    }
    
    //----------listar productos--------------
    
     public List listProducts(String value){
        List<Products> listProducts = new ArrayList();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro, categories ca WHERE pro.category_id = ca.id";
        String query_search_product = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.name LIKE '%" + value + "%'";
        
        try{
            connection = connectionDB.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = connection.prepareStatement(query);
                result = pst.executeQuery();
            } else {
                pst = connection.prepareStatement(query_search_product);
                result = pst.executeQuery();
            }
            
            while(result.next()){
                Products product = new Products();
                product.setId(result.getInt("id"));
                product.setCode(result.getInt("code"));
                product.setName(result.getString("name"));
                product.setDescription(result.getString("description"));
                product.setUnitPrice(result.getDouble("unit_price"));
                product.setProductQuantity(result.getInt("product_quantity"));
                product.setCategoryName(result.getString("category_name"));
                listProducts.add(product);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listProducts;
    }
    //----------actualizar productos---------------
     
      public boolean updateProduct(Products product){
        String query = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, updated = ?, "
                + "category_id = ? WHERE id = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnitPrice());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getCategoryId());
            pst.setInt(7, product.getId());
            pst.execute();
            return true;
         }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error modifying product data");
            return false;
        }
    }
     
    //----------eliminar productos---------------
       public boolean deleteProduct(int id){
        String query = "DELETE FROM products WHERE id = " + id;
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "You cannot delete a product that is related to another table");
            return false;
        }
       }
        // ------------FILTROS DE BUSQUEDA---------------------
      //-------------------Buscar producto-----------------------
    public Products searchProduct(int id){
        String query = "SELECT pro.*, ca.name AS category_Name FROM products pro "
                + "INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?";
        Products product = new Products();
    
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            result = pst.executeQuery();
            
            if(result.next()){
                product.setId(result.getInt("id"));
                product.setCode(result.getInt("code"));
                product.setName(result.getString("name"));
                product.setDescription(result.getString("description"));
                product.setUnitPrice(result.getDouble("unit_price"));
                product.setCategoryId(result.getInt("category_id"));
                product.setCategoryName(result.getString("category_name"));
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
    //-----------------------Buscar producto por código-------------------
    public Products searchCode(int code){
        String query = "SELECT * FROM products pro WHERE pro.code = ?";
        Products product = new Products();
    
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, code);
            result = pst.executeQuery();
            
            if(result.next()){
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setUnitPrice(result.getDouble("unit_price"));
                product.setProductQuantity(result.getInt("product_quantity"));
                
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
    //---------------------Traer la cantidad de productos--------------------
    public Products searchId(int id){
        String query = "SELECT pro.product_quantity FROM products pro WHERE pro.id = ?";
        Products product = new Products();
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            result = pst.executeQuery();
            if(result.next()){
                product.setProductQuantity(result.getInt("product_quantity"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
    //----------------Actualizar stock------------------
    public boolean updateStockQuery(int amount, int product_id){
        String query = "UPDATE products SET product_quantity = ? WHERE id = ?";
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    }
  

