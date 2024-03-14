package Controllers;

import Dao.CustomersDao;
import Entitys.Customers;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AngelJs
 */
public class CustomerController implements  ActionListener, MouseListener, KeyListener{
    
    private Customers customer;
    private CustomersDao customerDao;
    private SystemView view;
     DefaultTableModel model = new DefaultTableModel();

    public CustomerController(Customers customer, CustomersDao customerDao, SystemView view) {
        this.customer = customer;
        this.customerDao = customerDao;
        this.view = view;
        this.view.btn_customer_register.addActionListener(this);
         this.view.btn_customer_update.addActionListener(this);
          this.view.btn_customer_remove.addActionListener(this);
           this.view.btn_customer_cancel.addActionListener(this);
           this.view.txt_customer_search.addKeyListener(this);
           this.view.jTable_customer.addMouseListener(this);
           this.view.jLabel_Customer.addMouseListener(this);
   }
    
     //Listar clientes
    public void listAllCustomers() {
        List<Customers> list = customerDao.listCustomers(view.txt_customer_search.getText());
        model = (DefaultTableModel) view.jTable_customer.getModel();

        Object[] row = new Object[5];

        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getPhone();
            row[4] = list.get(i).getEmail();
            model.addRow(row);
        }
        view.jTable_customer.setModel(model);
    }
     
     

    @Override
    public void actionPerformed(ActionEvent e) {
  if (e.getSource() == view.btn_customer_register) {
            //Verifica si los campos estan vacios
            if (view.txt_customer_id.getText().equals("")
                    || view.txt_customer_name.getText().equals("")
                    || view.txt_customer_address.getText().equals("")
                    || view.txt_customer_phone.getText().equals("")
                    || view.txt_customer_email.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "All fields are required");
            } else {
                customer.setId(Integer.parseInt(view.txt_customer_id.getText().trim()));
                customer.setName(view.txt_customer_name.getText().trim());
                customer.setAddress(view.txt_customer_address.getText().trim());
                customer.setPhone(view.txt_customer_phone.getText().trim());
                customer.setEmail(view.txt_customer_email.getText().trim());

                if (customerDao.registerCustomer(customer)) {
                    cleanTable();
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Successfully registered custome");
                } else {
                    JOptionPane.showMessageDialog(null, "An error occurred while registering the customer");
                }
            }
        } else if (e.getSource() == view.btn_customer_update) {
            if (view.txt_customer_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "select a row to continue");
            } else {
                if (view.txt_customer_id.getText().equals("")
                        || view.txt_customer_name.getText().equals("")
                        || view.txt_customer_address.getText().equals("")
                        || view.txt_customer_phone.getText().equals("")
                        || view.txt_customer_email.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "All fields are required");
                } else {
                    customer.setId(Integer.parseInt(view.txt_customer_id.getText().trim()));
                    customer.setName(view.txt_customer_name.getText().trim());
                    customer.setAddress(view.txt_customer_address.getText().trim());
                    customer.setPhone(view.txt_customer_phone.getText().trim());
                    customer.setEmail(view.txt_customer_email.getText().trim());

                    if (customerDao.updateCustomer(customer)) {
                        cleanTable();
                        cleanFields();
                        listAllCustomers();
                        view.btn_customer_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Customer data successfully modified");
                    } else {
                        JOptionPane.showMessageDialog(null, "An error occurred while modifying customer data");
                    }
                }
            }
        } else if (e.getSource() == view.btn_customer_remove) {
            int row = view.jTable_customer.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "You must select a client to delete");
            } else {
                int id = Integer.parseInt(view.jTable_customer.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Do you really want to delete this customer?");

                if (question == 0 && customerDao.deleteCustomer(id) != false) {
                    cleanTable();
                    cleanFields();
                    view.btn_customer_register.setEnabled(true);
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Client successfully deleted");
                }
            }
        } else if (e.getSource() == view.btn_customer_cancel) {
            view.btn_customer_register.setEnabled(true);
            cleanFields();
        }
    
    
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == view.jTable_customer) {
            int row =view.jTable_customer.rowAtPoint(e.getPoint());
            view.txt_customer_id.setText(view.jTable_customer.getValueAt(row, 0).toString());
            view.txt_customer_name.setText(view.jTable_customer.getValueAt(row, 1).toString());
            view.txt_customer_address.setText(view.jTable_customer.getValueAt(row, 2).toString());
            view.txt_customer_phone.setText(view.jTable_customer.getValueAt(row, 3).toString());
            view.txt_customer_email.setText(view.jTable_customer.getValueAt(row, 4).toString());
            //Deshabilitar botones
            view.btn_customer_register.setEnabled(false);
            view.txt_customer_id.setEditable(false);
        } else if (e.getSource() == view.jLabel_Customer) {
             view.jTabbed_Products.setSelectedIndex(3);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar clientes
            listAllCustomers();
        }}

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
   }

    @Override
    public void keyTyped(KeyEvent e) {
   }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if (e.getSource() == view.txt_customer_search) {
            //Limpiar tabla
            cleanTable();
            //Listar clientes
            listAllCustomers();
        }}
    
      public void cleanFields() {
        view.txt_customer_id.setText("");
        view.txt_customer_id.setEditable(true);
        view.txt_customer_name.setText("");
        view.txt_customer_address.setText("");
        view.txt_customer_phone.setText("");
        view.txt_customer_email.setText("");
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    
}
}