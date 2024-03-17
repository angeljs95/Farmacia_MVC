package Dao;

/**
 *
 * @author AngelJs
 */
import Entitys.Sales;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SalesDao {

    // iniciamos la conexion
    ConnectionMySQL connectionDb = new ConnectionMySQL();
    Connection connection;
    PreparedStatement pst;
    ResultSet result;

    //---------------Registrar venta-----------------
    public boolean registerSale(int customer_id, int employee_id, double total) {
        String query = "INSERT INTO sales (customer_id, employee_id, total, sale_date)"
                + "VALUES(?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            connection = connectionDb.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, customer_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, dateTime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting sales");
            return false;
        }
    }

    //-------------Registramos el detalle de venta-------------
    public boolean registerSaleDetails(int sale_quantity, double sale_price, double sale_subtotal, int product_id, int sale_id) {

        String query = "INSERT INTO sale_details (sale_quantity, sale_price, sale_subtotal"
                + ", product_id, sale_id) VALUES(?,?,?,?,?)";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            connection = connectionDb.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, sale_quantity);
            pst.setDouble(2, sale_price);
            pst.setDouble(3, sale_subtotal);
            pst.setInt(4, product_id);
            pst.setInt(5, sale_id);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error registering sales details " + e);
            return false;
        }
    }

    //----------------Obtener id de la venta----------------
    public int saleId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM sales";
        try {
            connection = connectionDb.getConnection();
            pst = connection.prepareStatement(query);
            result = pst.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    //-----------Listar ventas----------------
    public List listAllSales() {
        List<Sales> listSales = new ArrayList();
        String query = "SELECT sa.id, cu.full_name AS customer , em.full_name AS employee , sa.total, sa.sale_date FROM sales sa, customers cu,"
                + " employees em WHERE sa.customer_id = cu.id and sa.employee_id= em.id order by sa.id asc";
        try {
            connection = connectionDb.getConnection();
            pst = connection.prepareStatement(query);
            result = pst.executeQuery();

            while (result.next()) {
                Sales sale = new Sales();
                sale.setId(result.getInt("id"));
                sale.setSale_customer_name(result.getString("customer"));
                sale.setSale_employee_name(result.getString("employee"));
                sale.setTotal(result.getDouble("total"));
                sale.setSale_date(result.getString("sale_date"));
                listSales.add(sale);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return listSales;
    }

    //--------------Listar venta para imprimir factura------------------
    public List listSaleDetails(int id) {
        List<Sales> saleDetails = new ArrayList();
        String query = "SELECT s.id AS invoice, c.full_name AS customer, e.full_name AS employee, s.total, s.sale_date FROM sales s INNER JOIN customers c ON"
                + " s.customer_id = c.id INNER JOIN employees e ON s.employee_id = e.id WHERE s.id = ? ORDER BY s.id ASC ";
        //ORDER BY s.id ASC
        try {
            connection = connectionDb.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            result = pst.executeQuery();
             
            while (result.next()) {
                Sales sale = new Sales();
               // sale.setId(result.getInt("invoice"));
                sale.setSale_customer_name(result.getString("customer"));
                sale.setSale_employee_name(result.getString("employee"));
                sale.setTotal(result.getDouble("total"));
                sale.setSale_date(result.getString("sale_date"));
                saleDetails.add(sale);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "hola "+e.getMessage());
        }
        return saleDetails;
    }

}