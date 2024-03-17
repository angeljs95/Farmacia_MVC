package Controllers;

import Dao.CustomersDao;
import static Dao.EmployeesDao.id_user;
import static Dao.EmployeesDao.rol_user;
import Dao.ProductsDao;
import Dao.SalesDao;
import Entitys.Customers;
import Entitys.Products;
import Entitys.Sales;
import Views.PrintSale;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AngelJs
 */
public class SaleController implements ActionListener, MouseListener, KeyListener {

    private SystemView view;
    private Sales sales;
    private SalesDao salesDao;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp;

    Products product = new Products();
    ProductsDao productDao = new ProductsDao();
    String rol = rol_user;

    Customers customer = new Customers();
    CustomersDao customerDao = new CustomersDao();

    int getIdCustomer = 0;

    public SaleController(SystemView view, Sales sales, SalesDao salesDao) {
        this.view = view;
        this.sales = sales;
        this.salesDao = salesDao;
        this.view.btn_add_product_sale.addActionListener(this);
        this.view.btn_confirm_sale.addActionListener(this);
        this.view.btn_new_sale.addActionListener(this);
        this.view.btn_remove_sale.addActionListener(this);
        this.view.jLabel_Sales.addMouseListener(this);
        this.view.jtable_sales.addMouseListener(this);
        this.view.txt_sale_code.addKeyListener(this);
        this.view.txt_sale_amount.addKeyListener(this);
        this.view.txt_sales_customerDni.addKeyListener(this);
        this.view.jTable_reports_sale.addMouseListener(this);
        this.view.jLabel_Reports.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btn_add_product_sale) {

            int amount = Integer.parseInt(view.txt_sale_amount.getText());
            String product_name = view.txt_sale_product_name.getText();
            double price = Double.parseDouble(view.txt_sale_product_Price.getText());
            int sale_id = Integer.parseInt(view.txt_sales_productId.getText());
            String customerName = view.txt_sale_customerName.getText();
            getIdCustomer = Integer.parseInt(view.txt_sales_customerDni.getText());
            int stock = Integer.parseInt(view.txt_sale_stock.getText());

            if (stock >= amount) {
                item = item + 1;
                temp = (DefaultTableModel) view.jtable_sales.getModel();
                for (int i = 0; i < view.jtable_sales.getRowCount(); i++) {
                    if (view.jtable_sales.getValueAt(i, 1).equals(view.txt_sale_product_name.getText())) {
                        JOptionPane.showMessageDialog(null, "The product is already registered in the purchasing table");
                        return;
                    }
                }
                ArrayList list = new ArrayList();
                list.add(item);
                list.add(sale_id);
                list.add(product_name);
                list.add(amount);
                list.add(price);
                list.add(amount * price);
                list.add(customerName);

                Object[] obj = new Object[6];
                obj[0] = (list.get(1));
                obj[1] = (list.get(2));
                obj[2] = (list.get(3));
                obj[3] = (list.get(4));
                obj[4] = (list.get(5));
                obj[5] = (list.get(6));
                temp.addRow(obj);
                view.jtable_sales.setModel(temp);
                calculateSale();
                cleanFieldsSales();
                view.txt_sale_code.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Stock not available");
                 
            }

        } else if (e.getSource() == view.btn_confirm_sale) {
            insertSale();
        } else if (e.getSource() == view.btn_remove_sale) {
            model = (DefaultTableModel) view.jtable_sales.getModel();
            model.removeRow(view.jtable_sales.getSelectedRow());
            calculateSale();
        } else if (e.getSource() == view.btn_new_sale) {
            cleanTableTemp();
            cleanFieldsSalesAll();
        }
    }

    private void insertSale() {
        double total = Double.parseDouble(view.txt_sale_totalToPay.getText());
        int employee_id =id_user;
        getIdCustomer= Integer.parseInt(view.txt_sales_customerDni.getText());

        if (salesDao.registerSale(getIdCustomer, employee_id, total)) {
           // Products product= new Products();
           // ProductsDao productDao = new ProductsDao();
            int sale_id = salesDao.saleId();
            
            for (int i = 0; i < view.jtable_sales.getRowCount(); i++) {
                int product_id = Integer.parseInt(view.jtable_sales.getValueAt(i, 0).toString());
                int sale_amount = Integer.parseInt(view.jtable_sales.getValueAt(i, 2).toString());
                 double sale_price = Double.parseDouble(view.jtable_sales.getValueAt(i, 3).toString());
                 double sale_subTotal = sale_price* sale_amount;
                // Registar los detalles de venta
                salesDao.registerSaleDetails(sale_amount, sale_price, sale_subTotal, product_id, sale_id);
                //trar la cantidad de productos a comprar para descontar el stock
                product = productDao.searchId(product_id);
                int stock = product.getProductQuantity() - sale_amount;
                productDao.updateStockQuery(stock, product_id);
            }
            JOptionPane.showMessageDialog(null, "Sale successfully generated");
            cleanTableTemp();
            cleanFieldsSalesAll();
            
            PrintSale print = new PrintSale(sale_id);
            print.setVisible(true);
        }
    }

    //Método para listar las ventas realizadas
    public void listAllSales() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Sales> list = salesDao.listAllSales();
            model = (DefaultTableModel) view.jTable_reports_sale.getModel();
            Object[] row = new Object[5];
            //Recorrer con ciclo for
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getSale_customer_name();
                row[2] = list.get(i).getSale_employee_name();
                row[3] = list.get(i).getTotal();
                row[4] = list.get(i).getSale_date();
                model.addRow(row);
            }
            view.jTable_reports_sale.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == view.jLabel_Sales) {
          view.jTabbed_Products.setSelectedIndex(2);
            cleanTable();
           } else if (e.getSource() == view.jLabel_Reports) {
               if(rol.equals("Administrador")){
                view.jTabbed_Products.setSelectedIndex(8);
                cleanTable();
                cleanFieldsSalesAll();
                listAllSales();}
            } else {
                view.jTabbed_Products.setEnabledAt(8, false);
                view.jLabel_Reports.setEnabled(false);
                JOptionPane.showMessageDialog(null, "You do not have administrator privileges to enter this view");
            }
        }
    

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
        if (e.getSource() == view.txt_sale_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.txt_sale_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter the code of the product to sale");
                } else {
                    int code = Integer.parseInt(view.txt_sale_code.getText());
                    product = productDao.searchCode(code);
                    if (product.getName() != null) {

                        view.txt_sale_product_name.setText(product.getName());
                        view.txt_sales_productId.setText("" + product.getId());
                        view.txt_sale_stock.setText("" + product.getProductQuantity());
                        view.txt_sale_product_Price.setText("" + product.getUnitPrice());
                        view.txt_sale_amount.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, " Dont exist product");
                    }
                }
            }
        } else if (e.getSource() == view.txt_sales_customerDni) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.txt_sales_customerDni.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter the Customer to continue sale");
                } else {
                    int dni = Integer.parseInt(view.txt_sales_customerDni.getText());
                    customer = customerDao.getCustomerbyDocument(dni);
                    if (customer.getName() != null) {
                        view.txt_sale_customerName.setText("" + customer.getName());
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer dont exist");
                    }

                }
            }
        }

    }

    //Revisar codigo *************************
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == view.txt_sale_amount) {

            double price = Double.parseDouble(view.txt_sale_product_Price.getText());
            int amount;

            if (view.txt_sale_amount.getText().equals("")) {
                amount = 1;
                view.txt_sale_product_Price.setText("" + price);
            } else {
                price = Double.parseDouble(view.txt_sale_product_Price.getText());
                amount = Integer.parseInt(view.txt_sale_amount.getText());
                view.txt_sale_subtotal.setText("" + amount * price);

            }
        }

    }

    public void calculateSale() {
        double total = 0.0;
        int numRow = view.jtable_sales.getRowCount();
        for (int i = 0; i < numRow; i++) {
            total = total + Double.parseDouble(String.valueOf(view.jtable_sales.getValueAt(i, 4)));
        }
        view.txt_sale_totalToPay.setText("" + total);
    }

    public void cleanFieldsSales() {
        view.txt_sale_amount.setText("");
        view.txt_sale_code.setText("");
        view.txt_sales_productId.setText("");
        view.txt_sale_product_name.setText("");
        view.txt_sale_product_Price.setText("");
        view.txt_sale_subtotal.setText("");
      //  view.txt_sale_totalToPay.setText("");
        view.txt_sale_stock.setText("");
    }
    
    public void cleanFieldsSalesAll(){
           view.txt_sale_amount.setText("");
        view.txt_sale_code.setText("");
        view.txt_sales_productId.setText("");
         view.txt_sale_customerName.setText("");
        view.txt_sale_product_name.setText("");
         view.txt_sales_customerDni.setText("");
        view.txt_sale_product_Price.setText("");
       view.txt_sale_subtotal.setText("");
        view.txt_sale_totalToPay.setText("");
        view.txt_sale_stock.setText("");
        view.txt_sales_customerDni.setText("");
        view.txt_sale_customerName.setText("");
    }

    //Limpiar tabla temporal
    public void cleanTableTemp() {
        for (int i = 0; i < temp.getRowCount(); i++) {
            temp.removeRow(i);
            i = i - 1;
        }
    }
    //Limpiar tabla

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

}
