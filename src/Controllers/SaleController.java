package Controllers;

import Dao.SalesDao;
import Entitys.Sales;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AngelJs
 */
public class SaleController implements  ActionListener, MouseListener, KeyListener{
    
    private SystemView view;
    private Sales sales;
    private SalesDao salesDao;
    DefaultTableModel model= new DefaultTableModel();

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
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
     }

    @Override
    public void mouseClicked(MouseEvent e) {
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
