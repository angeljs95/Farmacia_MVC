package Controllers;

import static Dao.EmployeesDao.id_user;
import static Dao.EmployeesDao.rol_user;
import Dao.ProductsDao;
import Dao.PurchasesDao;
import Entitys.DynamicComboBox;
import Entitys.Products;
import Entitys.Purchases;
import Views.Print;
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
public class PurchaseController implements  ActionListener, MouseListener, KeyListener {
    
    private SystemView view;
    private Purchases purchase;
    private PurchasesDao purchaseDao;
   
    private int getIdSupplier=0;
    private int item=0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp;
     //Instanciamos los productos
    Products product = new Products();
    ProductsDao productDao = new ProductsDao();
    String rol = rol_user;

    public PurchaseController(SystemView view, Purchases purchase, PurchasesDao purchaseDao) {
        this.view = view;
        this.purchase = purchase;
        this.purchaseDao = purchaseDao;
        this.view.btn_add_product_Tobuy.addActionListener(this);
        this.view.btn_confirm_purchase.addActionListener(this);
        this.view.btn_remove_purchase.addActionListener(this);
        this.view.btn_new_purchase.addActionListener(this);
        this.view.jtable_purchase.addMouseListener(this);
        this.view.txt_purchase_code.addKeyListener(this);
        this.view.txt_purchase_price.addKeyListener(this);
        this.view.jLabel_Reports.addMouseListener(this);
        this.view.jLabel_Purchase.addMouseListener(this);
        this.view.jTable_reports_purchase.addMouseListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    
          if (e.getSource() == view.btn_add_product_Tobuy) {
            DynamicComboBox supplier_cmb = (DynamicComboBox) view.cmb_purchase_supplier.getSelectedItem();
            int supplier_id = supplier_cmb.getId();

            if (getIdSupplier == 0) {
                getIdSupplier = supplier_id;
            } else {
                if (getIdSupplier != supplier_id) {
                    JOptionPane.showMessageDialog(null, "You cannot make the same purchase from multiple suppliers");
                } else {
                    int amount = Integer.parseInt(view.txt_purchase_product_amount.getText());
                    String product_name = view.txt_purchase_product_name.getText();
                    double price = Double.parseDouble(view.txt_purchase_price.getText());
                    int purchase_id = Integer.parseInt(view.txt_purchase_id.getText());
                    String supplierName = view.cmb_purchase_supplier.getSelectedItem().toString();

                    if (amount > 0) {
                        temp = (DefaultTableModel) view.jtable_purchase.getModel();
                        for (int i = 0; i <view.jtable_purchase.getRowCount(); i++) {
                            if (view.jtable_purchase.getValueAt(i, 1).equals(view.txt_purchase_product_name.getText())) {
                                JOptionPane.showMessageDialog(null, "The product is already registered in the purchasing table");
                                return;
                            }
                        }
                         ArrayList list = new ArrayList();
                        item = 1;
                        list.add(item);
                        list.add(purchase_id);
                        list.add(product_name);
                        list.add(amount);
                        list.add(price);
                        list.add(amount * price);
                        list.add(supplierName);

                        Object[] obj = new Object[6];
                        obj[0] = list.get(1);
                        obj[1] = list.get(2);
                        obj[2] = list.get(3);
                        obj[3] = list.get(4);
                        obj[4] = list.get(5);
                        obj[5] = list.get(6);
                        temp.addRow(obj);
                        view.jtable_purchase.setModel(temp);
                        cleanFieldsPurchases();
                        view.cmb_purchase_supplier.setEditable(false);
                        view.txt_purchase_code.requestFocus();
                        calculatePurchase();
                    }
                }
            }
        } else if (e.getSource() == view.btn_confirm_purchase) {
            insertPurchase();
        } else if (e.getSource() == view.btn_remove_purchase) {
            model = (DefaultTableModel) view.jtable_purchase.getModel();
            model.removeRow(view.jtable_purchase.getSelectedRow());
            calculatePurchase();
            view.txt_purchase_code.requestFocus();
        } else if (e.getSource() == view.btn_new_purchase) {
            cleanTableTemp();
            cleanFieldsPurchases();
        }
    
    }
    
     private void insertPurchase() {
        double total = Double.parseDouble(view.txt_purchase_total.getText());
        int employee_id = id_user;

        if (purchaseDao.registerPurchase(getIdSupplier, employee_id, total)) {
            int purchase_id = purchaseDao.purchaseId();
            for (int i = 0; i < view.jtable_purchase.getRowCount(); i++) {
                int product_id = Integer.parseInt(view.jtable_purchase.getValueAt(i, 0).toString());
                int purchase_amount = Integer.parseInt(view.jtable_purchase.getValueAt(i, 2).toString());
                double purchase_price = Double.parseDouble(view.jtable_purchase.getValueAt(i, 3).toString());
                double purchase_subtotal = purchase_price * purchase_amount;

                //Registrar detalles de la compra
                purchaseDao.registerPurchaseDetail(purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id);

                //Traer la cantidad de productos
                product = productDao.searchId(product_id);
                int amount = product.getProductQuantity() + purchase_amount;

                productDao.updateStockQuery(amount, product_id);
            }
           cleanTableTemp();
            cleanFieldsPurchases();
            JOptionPane.showMessageDialog(null, "Purchase successfully generated");
            //Print print = new Print(purchase_id);
            Print print= new Print(purchase_id);
            print.setVisible(true);
        }
    }
     
      //Método para listar las compras realizadas
    public void listAllPurchases() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Purchases> list = purchaseDao.listAllPurchases();
            model = (DefaultTableModel) view.jTable_reports_purchase.getModel();
            Object[] row = new Object[4];
            //Recorrer con ciclo for
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getSupplier_name_product();
                row[2] = list.get(i).getTotal();
                row[3] = list.get(i).getCreated();
                model.addRow(row);
            }
            view.jTable_reports_purchase.setModel(model);
        }
    }

    

    @Override
    public void mouseClicked(MouseEvent e) {
      if (e.getSource() == view.jLabel_Purchase) {
            if (rol.equals("Administrador")) {
                view.jTabbed_Products.setSelectedIndex(1);
                cleanTable();
            } else {
                view.jTabbed_Products.setEnabledAt(1, false);
                view.jLabel_Purchase.setEnabled(false);
                JOptionPane.showMessageDialog(null, "You do not have administrator privileges to enter this view");
            }
        } else if (e.getSource() == view.jLabel_Reports) {
            view.jTabbed_Products.setSelectedIndex(8);
            cleanTable();
            cleanFieldsPurchases();
            listAllPurchases();
        } }

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
     if (e.getSource() == view.txt_purchase_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (view.txt_purchase_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter the code of the product to buy");
                } else {
                    int id = Integer.parseInt(view.txt_purchase_code.getText());
                    product = productDao.searchCode(id);
                    view.txt_purchase_product_name.setText(product.getName());
                    view.txt_purchase_id.setText("" + product.getId());
                    view.txt_purchase_product_amount.requestFocus();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
   
        if (e.getSource() == view.txt_purchase_price) {
            int quantity;
            double price = 0.0;

            if (view.txt_purchase_product_amount.getText().equals("")) {
                quantity = 1;
                view.txt_purchase_price.setText("" + price);
            } else {
                quantity = Integer.parseInt(view.txt_purchase_product_amount.getText());
                price = Double.parseDouble(view.txt_purchase_price.getText());
                view.txt_purchase_subtotal.setText("" + quantity * price);
            }
        }
    }
    //Limpiar campos
    public void cleanFieldsPurchases() {
        view.txt_purchase_product_name.setText("");
        view.txt_purchase_price.setText("");
        view.txt_purchase_product_amount.setText("");
        view.txt_purchase_code.setText("");
        view.txt_purchase_subtotal.setText("");
        view.txt_purchase_id.setText("");
        view.txt_purchase_total.setText("");
    }

    //Calcular total a pagar
    public void calculatePurchase() {
        double total = 0.00;
        int numRow = view.jtable_purchase.getRowCount();

        for (int i = 0; i < numRow; i++) {
            //Pasar el indice de la columna que se sumará
            total = total + Double.parseDouble(String.valueOf(view.jtable_purchase.getValueAt(i, 4)));
        }
        view.txt_purchase_total.setText("" + total);
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
