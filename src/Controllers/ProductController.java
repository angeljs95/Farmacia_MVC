
package Controllers;

import static Dao.EmployeesDao.rol_user;
import Dao.ProductsDao;
import Entitys.DynamicComboBox;
import Entitys.Products;
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
public class ProductController implements  ActionListener, MouseListener, KeyListener {
    
        private SystemView view;
        private Products product;
        private ProductsDao productDao;
        String rol= rol_user;
        DefaultTableModel model= new DefaultTableModel();

    public ProductController(SystemView view, Products product, ProductsDao productDao) {
        this.view = view;
        this.product = product;
        this.productDao = productDao;
        this.view.btn_product_register.addActionListener(this);
        this.view.btn_product_cancel.addActionListener(this);
        this.view.btn_product_update.addActionListener(this);
        this.view.btn_product_delete.addActionListener(this);
        this.view.jTable_product.addMouseListener(this);
        this.view.jLabel_Product.addMouseListener(this);
        this.view.txt_product_search.addKeyListener(this);
        
    }
    
     //Listar productos
    public void listAllProducts() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Products> list = productDao.listProducts(view.txt_product_search.getText());
            model = (DefaultTableModel) view.jTable_product.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCode();
                row[2] = list.get(i).getName();
                row[3] = list.get(i).getDescription();
                row[4] = list.get(i).getUnitPrice();
                row[5] = list.get(i).getProductQuantity();
                row[6] = list.get(i).getCategoryName();
                model.addRow(row);
            }
             view.jTable_product.setModel(model);

            if (rol.equals("Auxiliar")) {
                view.btn_product_register.setEnabled(false);
                view.btn_product_update.setEnabled(false);
                view.btn_product_delete.setEnabled(false);
                view.btn_product_cancel.setEnabled(false);
                view.txt_product_codigo.setEnabled(false);
                view.txt_product_description.setEnabled(false);
                view.txt_product_id.setEditable(false);
                view.txt_product_name.setEditable(false);
                view.txt_product_price.setEditable(false);

            }
        }
    }
        
        

    


        @Override
      public void actionPerformed(ActionEvent e) {
          //registrar producto
        if (e.getSource() == view.btn_product_register) {
            if (view.txt_product_codigo.getText().equals("")
                    || view.txt_product_description.getText().equals("")
                    || view.txt_product_name.getText().equals("")
                    || view.txt_product_price.getText().equals("")
                    || view.cmb_product_category.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null,"Are field are required");
} else{
                product.setCode(Integer.parseInt(view.txt_product_codigo.getText()));
                product.setName(view.txt_product_name.getText());
                product.setDescription(view.txt_product_description.getText());
                product.setUnitPrice(Double.parseDouble(view.txt_product_price.getText()));
                DynamicComboBox categoryId= (DynamicComboBox) view.cmb_product_category.getSelectedItem();
                product.setCategoryId(categoryId.getId());
                if(productDao.registerProduct(product)){
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Product successfully registered");
                } else{
                    JOptionPane.showMessageDialog(null, "error when registering product");
                }
             }
        } else if  (e.getSource() == view.btn_product_update) {
            if (view.txt_product_codigo.getText().equals("")
                    || view.txt_product_name.getText().equals("")
                    || view.txt_product_description.getText().equals("")
                    || view.txt_product_price.getText().equals("")
                    || view.cmb_product_category.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(null, "All fields are requires");
            } else {
                product.setCode(Integer.parseInt(view.txt_product_codigo.getText()));
                product.setName(view.txt_product_name.getText().trim());
                product.setDescription(view.txt_product_description.getText().trim());
                product.setUnitPrice(Double.parseDouble(view.txt_product_price.getText()));
                //Obtener el id de la categoría
                DynamicComboBox categoryId = (DynamicComboBox) view.cmb_product_category.getSelectedItem();
                product.setCategoryId(categoryId.getId());
                //Pasar id al método
                product.setId(Integer.parseInt(view.txt_product_id.getText()));

                if (productDao.updateProduct(product)) {
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Product data successfully modified");
                } else {
                    JOptionPane.showMessageDialog(null, "An error occurred while modifying product data");
                }
            }
        } else if (e.getSource() == view.btn_product_delete) {

            int row = view.jTable_product.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "You must select a product to delete");
            } else {
                int id = Integer.parseInt(view.jTable_product.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Do you actually want to remove this product??");

                if (question == 0 && productDao.deleteProduct(id) != false) {
                    cleanTable();
                    cleanFields();
                    view.btn_product_register.setEnabled(true);
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Product successfully removed");
                }
            }
        }else if(e.getSource() == view.btn_product_cancel){
           cleanFields();
           view.btn_product_register.setEnabled(true);
        }
    }

        @Override
        public void mouseClicked(MouseEvent e) {
               if (e.getSource() == view.jTable_product) {
            int row = view.jTable_product.rowAtPoint(e.getPoint());
            view.txt_product_id.setText(view.jTable_product.getValueAt(row, 0).toString());
            product = productDao.searchProduct(Integer.parseInt(view.txt_product_id.getText()));
            view.txt_product_codigo.setText("" + product.getCode());
            view.txt_product_name.setText(product.getName());
            view.txt_product_description.setText(product.getDescription());
            view.txt_product_price.setText("" + product.getUnitPrice());
            view.cmb_product_category.setSelectedItem(new DynamicComboBox(product.getCategoryId(), product.getCategoryName()));
            //Dehhabilitar
            view.btn_product_register.setEnabled(false);
        }else if(e.getSource() == view.jLabel_Product){
            view.jTabbed_Products.setSelectedIndex(0);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar productos
            listAllProducts();
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
         }

        @Override
        public void keyReleased(KeyEvent e) {
             if (e.getSource() == view.txt_product_search) {
            cleanTable();
            listAllProducts();
        }
        }
        
          public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        view.txt_product_id.setText("");
        view.txt_product_codigo.setText("");
        view.txt_product_name.setText("");
        view.txt_product_description.setText("");
        view.txt_product_price.setText("");
    }
        

    }
