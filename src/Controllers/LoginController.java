package Controllers;

import Dao.EmployeesDao;
import Entitys.Employees;
import Views.LoginView;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author AngelJs
 */
public class LoginController implements ActionListener {

    private LoginView loginView;
    private Employees employee;
    private EmployeesDao employeeDao;

    public LoginController(LoginView loginView, Employees employee, EmployeesDao employeeDao) {
        this.loginView = loginView;
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.loginView.btn_Enter.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Obtener los datos de la vista
        String user = loginView.txt_UserName.getText().trim();
        String pass = String.valueOf(loginView.txt_Password.getPassword());

        if (e.getSource() == loginView.btn_Enter) {
            //Validamos que los campos no esten vacios
            if (!user.equals("") || !pass.equals("")) {
                employee = employeeDao.loginQuery(user, pass);
                //Verificar la existencia del usuario
                if (employee.getUserName() != null) {
                    if (employee.getRol().equals("ADMINISTRADOR")) {
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                    } else {
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                    }
                    this.loginView.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The fields are empty.");
            }

        }

    }
}
