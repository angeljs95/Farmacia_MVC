
package Dao;

import Entitys.Categories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/*
  @author AngelJs
 */
public class CategoriesDao {
    //iniciamos la conexion
    ConnectionMySQL connectionDB= new ConnectionMySQL();
    Connection connection;
    PreparedStatement pst;
    ResultSet result;
    
    //----------Registrar Categoria------------
    public boolean registerCategory ( Categories category){
        String query = "INSERT INTO categories (name, created, updated) VALUES(?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
         try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error registering category "+ e);
            return false;
        }
    }
         
         //--------------- Listar categorias------------------------
         
         public List listCategories(String value){
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";
        try{
            connection = connectionDB.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = connection.prepareStatement(query);
                result = pst.executeQuery();
            } else {
                pst = connection.prepareStatement(query_search_category);
                result = pst.executeQuery();
            }
            
            while(result.next()){
                Categories category = new Categories();
                category.setId(result.getInt("id"));
                category.setName(result.getString("name"));
                list_categories.add(category);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_categories;
    }
         
        // --------------Modificar Categoria------------------
        
         public boolean updateCategory(Categories category){
              String query = "UPDATE categories SET name = ?, updated = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId());
            pst.execute();
            return true; 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error modifying category data");
            return false;
        }
   }
         
          //----------------Eliminar categoría--------------------
    public boolean deleteCategory(int id){
        String query = "DELETE FROM categories WHERE id = " + id;
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "You cannot delete a category that is related to another table");
            return false;
        }
    } 
        
        
         
         
         
         
         
         
         
         
         
    }
    
    
    
    
    

