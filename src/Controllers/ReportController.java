
package Controllers;

import static Dao.EmployeesDao.rol_user;
import Dao.PurchasesDao;
import Entitys.Purchases;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author AngelJs
 */
public class ReportController implements  ActionListener, MouseListener, KeyListener {
    
    private SystemView view;
    private PurchasesDao purchasesDao;
    private Purchases purchases;
    String rol= rol_user;

    public ReportController(SystemView view, PurchasesDao purchasesDao, Purchases purchases) {
        this.view = view;
        this.purchasesDao = purchasesDao;
        this.purchases = purchases;
        this.view.jLabel_Reports.addMouseListener(this);
        this.view.jTable_reports_purchase.addMouseListener(this);
        this.view.jTable_reports_sale.addMouseListener(this);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      /*  if(e.getSource()== view.jLabel_Reports){
             if (rol.equals("Administrador")){
            view.jTabbed_Products.setSelectedIndex(8);
           
             }
        }*/
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
    }
    
}
