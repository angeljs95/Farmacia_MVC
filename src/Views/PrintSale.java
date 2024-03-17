package Views;

import Dao.SalesDao;
import Entitys.Sales;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Angeljs
 */
public final class PrintSale extends javax.swing.JFrame {

    Sales sales = new Sales();
    SalesDao salesDao = new SalesDao();
    DefaultTableModel model = new DefaultTableModel();

    public PrintSale(int id) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Factura de Venta");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        txt_sale_detail_id.setText("" + id);
        listAllSaleDetail(id);
        calculateSale();
    }

    public void listAllSaleDetail(int id) {
        List<Sales> list = salesDao.listSaleDetails(id);
        model = (DefaultTableModel) jTable_sale_detail.getModel();
        Object[] row = new Object[5];

        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getSale_employee_name();
            row[2] = list.get(i).getSale_customer_name();
            row[3] = list.get(i).getTotal();
            row[4] = list.get(i).getSale_date();
            model.addRow(row);
        }
        jTable_sale_detail.setModel(model);
    }

    public void calculateSale() {
        double total = 0.00;
        int numRow = jTable_sale_detail.getRowCount();
        for (int i = 0; i < numRow; i++) {
            total = total + Double.parseDouble(String.valueOf(jTable_sale_detail.getValueAt(i, 3)));
        }
        txt_saleDetail_total.setText("" + total);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Form_Print = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_sale_detail_id = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlabel_sale_detail = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_sale_detail = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt_saleDetail_total = new javax.swing.JTextField();
        btn_sale_print = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setMinimumSize(new java.awt.Dimension(620, 569));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Form_Print.setBackground(new java.awt.Color(0, 102, 102));
        Form_Print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sky Pharmacy");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 140, 30));

        txt_sale_detail_id.setEditable(false);
        jPanel1.add(txt_sale_detail_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 120, -1));

        jPanel3.setBackground(new java.awt.Color(18, 45, 61));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-130, 0, 620, 620));

        Form_Print.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 490, 110));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/farmacia_1.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 110));

        Form_Print.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 110));

        jlabel_sale_detail.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlabel_sale_detail.setForeground(new java.awt.Color(255, 255, 255));
        jlabel_sale_detail.setText("Sale Detail:");
        Form_Print.add(jlabel_sale_detail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 150, 30));

        jTable_sale_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Seller", "Customer", "Subtotal", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_sale_detail);

        Form_Print.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 220, 620, 180));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total:");
        Form_Print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, 80, -1));

        txt_saleDetail_total.setEditable(false);
        Form_Print.add(txt_saleDetail_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 440, 120, 30));

        getContentPane().add(Form_Print, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 520));

        btn_sale_print.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_sale_print.setText("Print");
        btn_sale_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sale_printActionPerformed(evt);
            }
        });
        getContentPane().add(btn_sale_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 550, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sale_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sale_printActionPerformed
        Toolkit tk = Form_Print.getToolkit();
        PrintJob pj = tk.getPrintJob(this, null, null);
        Graphics graphics = pj.getGraphics();
        Form_Print.print(graphics);
        graphics.dispose();
        pj.end();

    }//GEN-LAST:event_btn_sale_printActionPerformed

    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Form_Print;
    public javax.swing.JButton btn_sale_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable_sale_detail;
    public javax.swing.JLabel jlabel_sale_detail;
    public javax.swing.JTextField txt_saleDetail_total;
    public javax.swing.JTextField txt_sale_detail_id;
    // End of variables declaration//GEN-END:variables
}
