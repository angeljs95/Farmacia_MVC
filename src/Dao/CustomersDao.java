package Dao;

import Entitys.Customers;
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
public class CustomersDao {

    //iniciamos la conexion
    ConnectionMySQL connectionDB = new ConnectionMySQL();
    Connection connection;
    PreparedStatement pst;
    ResultSet result;

    //------------------Registrar Cliente-----------
    public boolean registerCustomer(Customers customer) {
        String query = "INSERT INTO customers (id, full_name, address, telephone, email, created, updated) "
                + "VALUES (?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getPhone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error registering Customer");
            return false;
        }

    }

    //------------Listar Clientes------------------
    public List listCustomers(String value) {
        List<Customers> listCustomers = new ArrayList();
        String query = "SELECT * FROM customers";
        // buscar por id del cliente
        //   String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";
        String query_search_customer = "SELECT * FROM customers WHERE full_name LIKE '%" + value + "%'";
        try {
            connection = connectionDB.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = connection.prepareStatement(query);
                result = pst.executeQuery();
            } else {
                pst = connection.prepareStatement(query_search_customer);
                result = pst.executeQuery();
            }

            while (result.next()) {
                Customers customer = new Customers();
                customer.setId(result.getInt("id"));
                customer.setName(result.getString("full_name"));
                customer.setAddress(result.getString("address"));
                customer.setPhone(result.getString("telephone"));
                customer.setEmail(result.getString("email"));
                listCustomers.add(customer);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listCustomers;
    }

    //--------------Modificar clientes-------------------
    public boolean updateCustomer(Customers customer) {
        String query = "UPDATE customers SET full_name = ?, address = ?, telephone = ?, email = ?, updated = ? "
                + "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getPhone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error modifying customer data");
            return false;
        }
    }

    //----------Eliminar clientes----------------------
    public boolean deleteCustomer(int id) {
        String query = "DELETE FROM customers WHERE id = " + id;
        try {
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "You cannot delete a customer that is related to another table");
            return false;
        }
    }

    public Customers getCustomerbyDocument(int document) {

        String query = "SELECT * FROM customers WHERE id= ? ";
        Customers customer = new Customers();
        try {
            connection = connectionDB.getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1, document);
            result = pst.executeQuery();

            if (result.next()) {
                customer.setId(result.getInt("id"));
                customer.setName(result.getString("full_name"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error " + e.getMessage());
        }
        return customer;
    }

}
