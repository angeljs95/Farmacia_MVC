
package Controllers;

import Dao.EmployeesDao;
import static Dao.EmployeesDao.id_user;
import static Dao.EmployeesDao.rol_user;
import Entitys.Employees;
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
public class EmployeeController implements  ActionListener, MouseListener, KeyListener{
    
    private SystemView view;
    private Employees employee;
    private EmployeesDao employeeDao;
    String rol= rol_user;
    DefaultTableModel model= new DefaultTableModel();

    public EmployeeController(SystemView view, Employees employee, EmployeesDao employeeDao) {
        this.view = view;
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.view.btn_employee_register.addActionListener(this);
        this.view.btn_employee_update.addActionListener(this);
        this.view.btn_employee_remove.addActionListener(this);
        this.view.btn_employee_cancel.addActionListener(this);
        this.view.jLabel_Employed.addMouseListener(this);
        this.view.txt_employees_search.addKeyListener(this);
        this.view.btn_profile_update.addActionListener(this);
        this.view.jTable_employee.addMouseListener(this);
        this.view.jLabel_Settings.addMouseListener(this);
        
    }
    
     //Listar todos los empleados
    public void listAllEmployees(){
        if(rol.equals("Administrador")){
            List<Employees> list = employeeDao.listEmployees(view.txt_employees_search.getText());
            model = (DefaultTableModel) view.jTable_employee.getModel();
            Object[] row = new Object[7];
            for(int i = 0; i < list.size(); i++){
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getUserName();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getPhone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                model.addRow(row);
            }
        }
    }
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getSource() == view.btn_employee_register){
            //Verificar si los campos estan vacios
            if(view.txt_employees_id.getText().equals("")
                    || view.txt_employees_name.getText().equals("")
                    || view.txt_employees_name.getText().equals("")
                    || view.txt_employees_address.getText().equals("")
                    || view.txt_employees_phone.getText().equals("")
                    || view.txt_employees_email.getText().equals("")
                    || view.cmv_employee_Rol.getSelectedItem().toString().equals("")
                    || String.valueOf(view.txt_employees_password.getPassword()).equals("")){
                
                JOptionPane.showMessageDialog(null, "All fields are required");
            }else{
                //Realizar la inserción
                employee.setId(Integer.parseInt(view.txt_employees_id.getText().trim()));
                employee.setName(view.txt_employees_name.getText().trim());
                employee.setUserName(view.txt_employees_username.getText().trim());
                employee.setAddress(view.txt_employees_address.getText().trim());
                employee.setPhone(view.txt_employees_phone.getText().trim());
                employee.setEmail(view.txt_employees_email.getText().trim());
                employee.setPassword(String.valueOf(view.txt_employees_password.getPassword()));
                employee.setRol(view.cmv_employee_Rol.getSelectedItem().toString());
                
                if(employeeDao.registerEmployee(employee)){
                    cleanTable();
                    cleanFields();
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Successfully registered employee");
                }else{
                    JOptionPane.showMessageDialog(null, "An error occurred while registering the employee");
                }
            }
        }else if(e.getSource() == view.btn_employee_update){
            if(view.txt_employees_id.equals("")){
                JOptionPane.showMessageDialog(null, "Select a row to continue");
            }else{
                //Verificar si los campos estan vacios
                if(view.txt_employees_id.getText().equals("")
                   || view.txt_employees_name.getText().equals("")
                   || view.cmv_employee_Rol.getSelectedItem().toString().equals("")){
                    
                    JOptionPane.showMessageDialog(null, "All fields are required");
                }else{
                    employee.setId(Integer.parseInt(view.txt_employees_id.getText().trim()));
                    employee.setName(view.txt_employees_name.getText().trim());
                    employee.setUserName(view.txt_employees_username.getText().trim());
                    employee.setAddress(view.txt_employees_address.getText().trim());
                    employee.setPhone(view.txt_employees_phone.getText().trim());
                    employee.setEmail(view.txt_employees_email.getText().trim());
                    employee.setPassword(String.valueOf(view.txt_employees_password.getPassword()));
                    employee.setRol(view.cmv_employee_Rol.getSelectedItem().toString());
                    
                    if(employeeDao.updateEmployee(employee)){
                        cleanTable();
                        cleanFields();
                        listAllEmployees();
                        view.btn_employee_register.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Employee data successfully modified");
                    }else{
                        JOptionPane.showMessageDialog(null, "An error occurred while modifying the employee");
                    }
                    
                }
            }
        }else if(e.getSource() == view.btn_employee_remove){
            int row = view.jTable_employee.getSelectedRow();
            
            if(row == -1){
                JOptionPane.showMessageDialog(null, "You must select an employee to delete");
            }else if(view.jTable_employee.getValueAt(row, 0).equals(id_user)){
                JOptionPane.showMessageDialog(null, "You cannot delete the authenticated user");
            }else{
                int id = Integer.parseInt(view.jTable_employee.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Do you really want to eliminate this employee?");
                
                if(question == 0 && employeeDao.deleteEmployee(id) != false){
                    cleanTable();
                    cleanFields();
                    view.btn_employee_register.setEnabled(true);
                    view.txt_employees_password.setEnabled(true);
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Employee successfully removed");
                }         
            }
        }else if(e.getSource() == view.btn_employee_cancel){
            cleanFields();
            view.btn_employee_register.setEnabled(true);
            view.txt_employees_password.setEnabled(true);
            view.txt_employees_id.setEnabled(true);
        }else if(e.getSource() == view.btn_profile_update){
            //Recolectamos información de las cajas password
            String password = String.valueOf(view.txt_profile_password.getPassword());
            String confirm_password = String.valueOf(view.txt_profile_passwordConfirm.getPassword());
            //Verificamos si las cajas de texto estan vacias
            if(!password.equals("") && !confirm_password.equals("")){
                //Verificar que las contraseñas sean iguales
                if(password.equals(confirm_password)){
                    employee.setPassword(String.valueOf(view.txt_profile_password.getPassword()));
                    
                    if(employeeDao.updateEmployeePassword(employee) != false){
                        JOptionPane.showMessageDialog(null, "Password changed successfully");
                    }else{
                        JOptionPane.showMessageDialog(null, "An error occurred while changing the password");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Passwords do not match");
                }
            }else{
                JOptionPane.showMessageDialog(null, "All fields are required");
            }
        }
    
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
   
         if(e.getSource() == view.jTable_employee){
           int row = view.jTable_employee.rowAtPoint(e.getPoint());
           
           view.txt_employees_id.setText(view.jTable_employee.getValueAt(row, 0).toString());
           view.txt_employees_name.setText(view.jTable_employee.getValueAt(row, 1).toString());
           view.txt_employees_username.setText(view.jTable_employee.getValueAt(row, 2).toString());
           view.txt_employees_address.setText(view.jTable_employee.getValueAt(row, 3).toString());
           view.txt_employees_phone.setText(view.jTable_employee.getValueAt(row, 4).toString());
           view.txt_employees_email.setText(view.jTable_employee.getValueAt(row, 5).toString());
           view.cmv_employee_Rol.setSelectedItem(view.jTable_employee.getValueAt(row, 6).toString());
           
           //Deshabilitar
           view.txt_employees_id.setEditable(false);
           view.txt_employees_password.setEnabled(false);
           view.btn_employee_register.setEnabled(false);
           
       }else if(e.getSource() == view.jLabel_Employed){
           if(rol.equals("Administrador")){
               view.jTabbed_Products.setSelectedIndex(4);
               //Limpiar tabla
               cleanTable();
               //Limpiar campos
               cleanFields();
               //Listar empleados
               listAllEmployees();
           }else{
               view.jTabbed_Products.setEnabledAt(4, false);
               view.jLabel_Employed.setEnabled(false);
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
        
        if(e.getSource() == view.txt_employees_search){
            cleanTable();
            listAllEmployees();
        }
        
    }
    
    //Limpiar campos
    public void cleanFields(){
        view.txt_employees_id.setText("");
        view.txt_employees_id.setEditable(true);
        view.txt_employees_name.setText("");
        view.txt_employees_username.setText("");
        view.txt_employees_address.setText("");
        view.txt_employees_phone.setText("");
        view.txt_employees_email.setText("");
        view.txt_employees_password.setText("");
        view.cmv_employee_Rol.setSelectedIndex(0);
    }
    
    public void cleanTable(){
        for(int i = 0; i< model.getRowCount(); i++){
            model.removeRow(i);
            i = i - 1;
        }
    }
    
    
    
}
