package Dao;

/**
 *
 * @author AngelJs
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    
    private String database_Name= "phamacy_db";
    private String user= "root" ;
    private String password="root";
    private String url= "jdbc:mysql://localhost:3306/" + database_Name;
    Connection connection= null;
    
    public Connection getConnection(){
        
        try{
            //Obtenemos el Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Ahora obtenemos la conexion
            connection= DriverManager.getConnection(url,user,password);
            
        } catch(ClassNotFoundException e){
             System.err.println("Ha ocurrido un ClassNotFoundException " + e.getMessage());
        }catch(SQLException e){
             System.err.println("Ha ocurrido un SQLException " + e.getMessage());
        }
        return connection;
        
    }
    
    
    
    
}
