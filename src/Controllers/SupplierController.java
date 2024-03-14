package Controllers;

import static Dao.EmployeesDao.rol_user;
import Dao.SuppliersDao;
import Entitys.DynamicComboBox;
import Entitys.Suppliers;
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
public class SupplierController implements  ActionListener, MouseListener, KeyListener {
    
    private SystemView view;
    private Suppliers supplier;
    private SuppliersDao supplierDao;
    String rol= rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public SupplierController(SystemView view, Suppliers supplier, SuppliersDao supplierDao) {
        this.view = view;
        this.supplier = supplier;
        this.supplierDao = supplierDao;
        this.view.btn_supplier_register.addActionListener(this);
        this.view.btn_supplier_update.addActionListener(this);
        this.view.btn_supplier_remove.addActionListener(this);
        this.view.btn_supplier_cancel.addActionListener(this);
        this.view.jTable_supplier.addMouseListener(this);
        this.view.jLabel_Supplier.addMouseListener(this);
        this.view.txt_supplier_search.addKeyListener(this);
        getSupplierName();
    }
     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btn_supplier_register) {
            if (view.txt_supplier_name.getText().equals("")
                    || view.txt_supplier_description.getText().equals("")
                    || view.txt_supplier_addres.getText().equals("")
                    || view.txt_supplier_phone.getText().equals("")
                    || view.txt_supplier_email.getText().equals("")
                    || view.cmb_supplier_city.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(null, "All fields are required");
            } else {
                //Realizar inserción
                supplier.setName(view.txt_supplier_name.getText().trim());
                supplier.setDescription(view.txt_supplier_description.getText().trim());
                supplier.setAddress(view.txt_supplier_addres.getText().trim());
                supplier.setPhone(view.txt_supplier_phone.getText().trim());
                supplier.setEmail(view.txt_supplier_email.getText().trim());
                supplier.setCity(view.cmb_supplier_city.getSelectedItem().toString());

                if (supplierDao.registerSupplier(supplier)) {
                    cleanTable();
                    cleanFields();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Successfully registered supplier");
                } else {
                    JOptionPane.showMessageDialog(null, "An error occurred while registering the supplier");
                }
            }
        } else if (e.getSource() == view.btn_supplier_update) {
            if (view.txt_supplier_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Select a row to continue");
            } else {
                if (view.txt_supplier_name.getText().equals("")
                        || view.txt_supplier_addres.getText().equals("")
                        || view.txt_supplier_phone.getText().equals("")
                        || view.txt_supplier_email.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "All fields are required");
                } else {
                    supplier.setName(view.txt_supplier_name.getText().trim());
                    supplier.setDescription(view.txt_supplier_description.getText().trim());
                    supplier.setAddress(view.txt_supplier_addres.getText().trim());
                    supplier.setPhone(view.txt_supplier_phone.getText().trim());
                    supplier.setEmail(view.txt_supplier_email.getText().trim());
                    supplier.setCity(view.cmb_supplier_city.getSelectedItem().toString());
                    supplier.setId(Integer.parseInt(view.txt_supplier_id.getText()));

                    if (supplierDao.updateSupplier(supplier)) {
                        //Limpiar tabla
                        cleanTable();
                        //Limpiar campos
                        cleanFields();
                        //Listar proveedor
                        listAllSuppliers();
                        view.btn_supplier_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Supplier data modified successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "An error occurred while modifying the supplier data");
                    }
                }
            }
        } else if (e.getSource() == view.btn_supplier_remove) {
            int row = view.jTable_supplier.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un proveedor para eliminar");
            } else {
                int id = Integer.parseInt(view.jTable_supplier.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Do you really want to delete this provider?");
                if (question == 0 && supplierDao.deleteSupplier(id) != false) {
                    //Limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar proveedores
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado con éxito");
                }
            }
        } else if (e.getSource() == view.btn_supplier_cancel) {
            cleanFields();
            view.btn_supplier_register.setEnabled(true);
        }
    }

    //Listar proveedores
    public void listAllSuppliers() {
        if (rol.equals("Administrador")) {
            List<Suppliers> list = supplierDao.listSuppliers(view.txt_supplier_search.getText());
            model = (DefaultTableModel) view.jTable_supplier.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getPhone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }
             view.jTable_supplier.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() ==  view.jTable_supplier) {
            int row =  view.jTable_supplier.rowAtPoint(e.getPoint());
            view.txt_supplier_id.setText( view.jTable_supplier.getValueAt(row, 0).toString());
            view.txt_supplier_name.setText( view.jTable_supplier.getValueAt(row, 1).toString());
            view.txt_supplier_description.setText( view.jTable_supplier.getValueAt(row, 2).toString());
            view.txt_supplier_addres.setText( view.jTable_supplier.getValueAt(row, 3).toString());
            view.txt_supplier_phone.setText( view.jTable_supplier.getValueAt(row, 4).toString());
            view.txt_supplier_email.setText( view.jTable_supplier.getValueAt(row, 5).toString());
            view.cmb_supplier_city.setSelectedItem( view.jTable_supplier.getValueAt(row, 6).toString());
            //Deshabilitar botones
            view.btn_supplier_register.setEnabled(false);
            view.txt_supplier_id.setEditable(false);

        } else if (e.getSource() == view.jLabel_Supplier) {
            if (rol.equals("Administrador")) {
                view.jTabbed_Products.setSelectedIndex(5);
                //Limpiar tabla
                cleanTable();
                //Limpiar campos
                cleanFields();
                //Listar proveedores
                listAllSuppliers();
            } else {
                view.jTabbed_Products.setEnabledAt(5, false);
                view.jLabel_Supplier.setEnabled(false);
                JOptionPane.showMessageDialog(null, "You do not have administrator privileges to access this view");
            }
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
        if (e.getSource() == view.txt_supplier_search) {
            //Limpiar tabla
            cleanTable();
            //Listar proveedor
            listAllSuppliers();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        view.txt_supplier_id.setText("");
        view.txt_supplier_id.setEditable(true);
        view.txt_supplier_name.setText("");
        view.txt_supplier_description.setText("");
        view.txt_supplier_addres.setText("");
        view.txt_supplier_phone.setText("");
        view.txt_supplier_email.setText("");
        view.cmb_supplier_city.setSelectedIndex(0);
    }

    //Método para mostrar el nombre del proveedor
    public void getSupplierName() {
        List<Suppliers> list = supplierDao.listSuppliers(view.txt_supplier_search.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            view.cmb_purchase_supplier.addItem(new DynamicComboBox(id,name));
        }
    }
}
