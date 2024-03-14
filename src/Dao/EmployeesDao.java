
package Dao;

import Entitys.Employees;
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
public class EmployeesDao {
    
    // Iniciamos la conexion
    ConnectionMySQL connectionDB= new ConnectionMySQL();
    // 
    Connection connection;
    //El preparedStatement sirve para las consultas a la base de datos
    PreparedStatement pst;
    // y el resulSet obtiene los resultados de la consulta
    ResultSet result;
    
    //Variables para enviar datos entre interfaces
    public static int id_user = 0;
    public static String name_user = "";
    public static String userName_user = "";
    public static String address_user = "";
    public static String phone_user = "";
    public static String email_user = "";
    public static String rol_user = "";
    
    //-------------------Registro de empleados-------------------------------------
    public boolean registerEmployee(Employees employee){
        
        String query = "INSERT INTO employees(id, full_name, username, address, telephone, email, password, rol, created,"
                + "updated) VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getName());
            pst.setString(3, employee.getUserName());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getPhone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error registeryng employee" + e);
            return false;
        }
    }
    
     //----------------------------listar empleados
    
   public List listEmployees(String value){
       List<Employees> listEmployees= new ArrayList();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        //busca por id
         //  String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
        String query_search_employee = "SELECT * FROM employees WHERE full_name LIKE '%" + value + "%'";
        
        try{
            connection = connectionDB.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = connection.prepareStatement(query);
                result = pst.executeQuery();
            }else{
                pst = connection.prepareStatement(query_search_employee);
                result = pst.executeQuery();
            }
            
        while(result.next()){
            Employees employee = new Employees();
            employee.setId(result.getInt("id"));
            employee.setName(result.getString("full_name"));
            employee.setUserName(result.getString("username"));
            employee.setAddress(result.getString("address"));
            employee.setPhone(result.getString("telephone"));
            employee.setEmail(result.getString("email"));
            employee.setRol(result.getString("rol"));
            listEmployees.add(employee);
        }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listEmployees;
 }
    
    //----------------------Actualizar empleados------------------
   
   public boolean updateEmployee(Employees employee){
        String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ?"
                + "WHERE id = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1, employee.getName());
            pst.setString(2, employee.getUserName());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getPhone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, employee.getId());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error modifying employee date " + e);
            return false;
        }
  }
    
    //---------------------------eliminar empleado-------------
   public boolean deleteEmployee(int id){
       String query = "DELETE FROM employees WHERE id = " + id;
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "You cannot delete a employee that is related to another table");
            return false;
        }
   }
        
        //----------------------Login----------------------
       
       public Employees loginQuery(String user, String password){
        String query = "SELECT * FROM employees WHERE username = ?  AND password = ?";
        Employees employee = new Employees();
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            //Enviamos parámetros
            pst.setString(1, user);
            pst.setString(2, password);
            result = pst.executeQuery();
            
            if(result.next()){
                employee.setId(result.getInt("id"));
                id_user = employee.getId();
                employee.setName(result.getString("full_name"));
                name_user = employee.getName();
                employee.setUserName(result.getString("username"));
                userName_user = employee.getUserName();
                employee.setAddress(result.getString("address"));
                address_user = employee.getAddress();
                employee.setPhone(result.getString("telephone"));
                phone_user = employee.getPhone();
                employee.setEmail(result.getString("email"));
                email_user = employee.getEmail();
                employee.setRol(result.getString("rol"));
                rol_user = employee.getRol();
            }   
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error getting employee " + e);
        }
        return employee;
    }
       //------------------------Cambiar la contraseña -------------------------------------
       
       public boolean updateEmployeePassword(Employees employee){
        String query = "UPDATE employees SET password = ? WHERE username = '" + userName_user + "'";
        try{
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1, employee.getPassword());
            pst.executeUpdate();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error modifying password " + e);
            return false;
        }
    }
       
       
       
   }

        
