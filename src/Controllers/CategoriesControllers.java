package Controllers;

import Dao.CategoriesDao;
import static Dao.EmployeesDao.rol_user;
import Entitys.Categories;
import Entitys.DynamicComboBox;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author AngelJs
 */
public class CategoriesControllers implements ActionListener, MouseListener, KeyListener {

    private Categories category;
    private CategoriesDao categoryDao;
    private SystemView view;
    private String rol=rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public CategoriesControllers(Categories category, CategoriesDao categoryDao, SystemView view) {
        this.category = category;
        this.categoryDao = categoryDao;
        this.view = view;
        //Boton registrar categoria
        this.view.btn_categories_register.addActionListener(this);
        //boton modificar categoria
        this.view.btn_categories_update.addActionListener(this);
        //boton eliminar categoria
        this.view.btn_categories_remove1.addActionListener(this);
       // escucha caja de texto
       this.view.jTable_categories.addMouseListener(this);
       this.view.txt_categories_search.addKeyListener(this);
       this.view.jLabel_Category.addMouseListener(this);
       getCategoryName();
        AutoCompleteDecorator.decorate(view.cmb_product_category);

   
       
    }
    // metodo para obtener el nombre de la categoria
  
         public void getCategoryName(){
        List<Categories> list = categoryDao.listCategories(view.txt_categories_search.getText());
        for(int i = 0; i< list.size(); i++){
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            view.cmb_product_category.addItem(new DynamicComboBox(id, name));
       }
    }
         
      //Listar categorías
    public void listAllCategories() {
        if (rol.equals("Administrador")) {
            List<Categories> list = categoryDao.listCategories(view.txt_categories_search.getText());
            model = (DefaultTableModel) view.jTable_categories.getModel();
            Object[] row = new Object[2];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                model.addRow(row);
            }
            view.jTable_categories.setModel(model);
        }
    }
         
        public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        view.txt_categories_id.setText("");
        view.txt_categories_name.setText("");
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // funcion boton register
    if(e.getSource()== view.btn_categories_register){
        if(view.txt_categories_name.getText().equals("")){
            JOptionPane.showMessageDialog(null, " All fields are required");
        } else{
            category.setName(view.txt_categories_name.getText().trim());
             if(categoryDao.registerCategory(category)){
            cleanTable();
            cleanFields();
            listAllCategories();
            JOptionPane.showMessageDialog(null,"The category has been successfully registered");
        } else{
            JOptionPane.showMessageDialog(null, "An error occurred while registering the category");
        }
         }
       
    } // --------Funcion boton update------------
    else if( e.getSource()== view.btn_categories_update){
        if(view.txt_categories_id.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Select a row to continue");
        } else{
            if(view.txt_categories_id.getText().equals("") || view.txt_categories_name.getText().equals("")){
                JOptionPane.showMessageDialog(null,"All fields are required");
            } else{
                category.setId(Integer.parseInt(view.txt_categories_id.getText()));
                category.setName(view.txt_categories_name.getText().trim());
             
            if(categoryDao.updateCategory(category)){
                cleanTable();
                  cleanFields();
                  view.btn_categories_register.setEnabled(true);
                  listAllCategories();
           }
        }
        } 
    }else if (e.getSource() == view.btn_categories_remove1) {
            int row = view.jTable_categories.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "You must select a category to delete");
            } else {
                int id = Integer.parseInt(view.jTable_categories.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Do you really want to delete this category?");

                if (question == 0 && categoryDao.deleteCategory(id) != false) {
                    cleanTable();
                    cleanFields();
                    view.btn_categories_register.setEnabled(true);
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Category successfully removed");
                }
            }
        }
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
   
         if (e.getSource() == view.jTable_categories) {
            int row = view.jTable_categories.rowAtPoint(e.getPoint());
            view.txt_categories_id.setText(view.jTable_categories.getValueAt(row, 0).toString());
            view.txt_categories_name.setText(view.jTable_categories.getValueAt(row, 1).toString());
            view.btn_categories_register.setEnabled(false);
        }else if(e.getSource() == view.jLabel_Category){
            if(rol.equals("Administrador")){
                view.jTabbed_Products.setSelectedIndex(7);
                cleanTable();
                //cleanFields();
                listAllCategories();
            }/*else{
                view.jTabbed_Products.setEnabledAt(7, false);
                view.jLabel_categories.setEnabled(false);
                JOptionPane.showMessageDialog(null, "You do not have administrator privileges to access this view");
            }*/
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
            if (e.getSource() == view.txt_categories_search) {
            //Limpiar tabla
            cleanTable();
            //Listar categorías
            listAllCategories();
     }
    
    
    
    
}
}
