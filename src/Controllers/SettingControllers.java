
package Controllers;

import static Dao.EmployeesDao.address_user;
import static Dao.EmployeesDao.email_user;
import static Dao.EmployeesDao.id_user;
import static Dao.EmployeesDao.name_user;
import static Dao.EmployeesDao.phone_user;
import Views.SystemView;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author AngelJs
 */
public class SettingControllers implements MouseListener{

    private SystemView viewSystem;
  
    
    
    public SettingControllers(SystemView viewSystem){
        this.viewSystem= viewSystem;
        this.viewSystem.jLabel_Category.addMouseListener(this);
        this.viewSystem.jLabel_Customer.addMouseListener(this);
        this.viewSystem.jLabel_Employed.addMouseListener(this);
        this.viewSystem.jLabel_Product.addMouseListener(this);
        this.viewSystem.jLabel_Purchase.addMouseListener(this);
        this.viewSystem.jLabel_Reports.addMouseListener(this);
        this.viewSystem.jLabel_Supplier.addMouseListener(this);
        this.viewSystem.jLabel_Settings.addMouseListener(this);
        this.viewSystem.jLabel_Sales.addMouseListener(this);
        Profile();
    }
    
    public void Profile() {
        this.viewSystem.txt_profile_id.setText("" + id_user);
        this.viewSystem.txt_profile_name.setText(name_user);
        this.viewSystem.txt_profile_email.setText(email_user);
        this.viewSystem.txt_profile_address.setText(address_user);
        this.viewSystem.txt_profile_phone.setText(phone_user);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() ==  viewSystem.jLabel_Settings ){
            viewSystem.jTabbed_Products.setSelectedIndex(7);
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
       if(e.getSource()== viewSystem.jLabel_Product){
           viewSystem.jPannel_Product.setBackground(new Color(152,202,63));
       } else if(e.getSource()== viewSystem.jLabel_Category ){
           viewSystem.jPanel_Category.setBackground(new Color (152,202,63));
       }  else if(e.getSource()== viewSystem.jLabel_Purchase ){
           viewSystem.jPannel_Purchase.setBackground(new Color (152,202,63));
       } else if(e.getSource()== viewSystem.jLabel_Customer ){
           viewSystem.jPannl_Customer.setBackground(new Color (152,202,63));
       } else if(e.getSource()== viewSystem.jLabel_Settings ){
           viewSystem.jPanel_Settings.setBackground(new Color (152,202,63));
       }  else if(e.getSource()== viewSystem.jLabel_Employed ){
           viewSystem.jPanel_Employed.setBackground(new Color (152,202,63));
       }  else if(e.getSource()== viewSystem.jLabel_Supplier ){
           viewSystem.jPanel_Supplier.setBackground(new Color (152,202,63));
       }  else if(e.getSource()== viewSystem.jLabel_Reports ){
           viewSystem.jPanel_Reports.setBackground(new Color (152,202,63));
       }    else if( e.getSource()== viewSystem.jLabel_Sales){
           viewSystem.jPanel_Sales.setBackground(new Color (152,202,63));
       }
    }

    @Override
    public void mouseExited(MouseEvent e) {
   
          if(e.getSource()== viewSystem.jLabel_Product){
           viewSystem.jPannel_Product.setBackground(new Color(0,102,102));
       } else if(e.getSource()== viewSystem.jLabel_Category ){
           viewSystem.jPanel_Category.setBackground(new Color (0,102,102));
       }  else if(e.getSource()== viewSystem.jLabel_Purchase ){
           viewSystem.jPannel_Purchase.setBackground(new Color (0,102,102));
       } else if(e.getSource()== viewSystem.jLabel_Customer ){
           viewSystem.jPannl_Customer.setBackground(new Color (0,102,102));
       } else if(e.getSource()== viewSystem.jLabel_Settings ){
           viewSystem.jPanel_Settings.setBackground(new Color (0,102,102));
       }  else if(e.getSource()== viewSystem.jLabel_Employed ){
           viewSystem.jPanel_Employed.setBackground(new Color (0,102,102));
       }  else if(e.getSource()== viewSystem.jLabel_Supplier ){
           viewSystem.jPanel_Supplier.setBackground(new Color (0,102,102));
       }  else if(e.getSource()== viewSystem.jLabel_Reports ){
           viewSystem.jPanel_Reports.setBackground(new Color (0,102,102));
       }     else if(e.getSource()== viewSystem.jLabel_Sales ){
           viewSystem.jPanel_Sales.setBackground(new Color (0,102,102));
       }    
    
    }
    
    
}
