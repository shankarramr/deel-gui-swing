package com.deel.view;

import com.deel.domain.Product;
import com.deel.model.ProductModel;
import com.deel.model.TaxModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author neo
 */
public class BillerForm extends javax.swing.JFrame {
    private static String imagePath;
    private List<String> productList;
    protected static final int TABLE_BILL_DETAIL_COLUMN_SERIAL_NUMBER = 0;
    protected static final int TABLE_BILL_DETAIL_COLUMN_CODE = 1;
    protected static final int TABLE_BILL_DETAIL_COLUMN_PRODUCT = 2;
    protected static final int TABLE_BILL_DETAIL_COLUMN_PRICE = 3;
    protected static final int TABLE_BILL_DETAIL_COLUMN_QUANTITY = 4;
    protected static final int TABLE_BILL_DETAIL_COLUMN_AMOUNT = 5;
    protected static final int TABLE_BILL_DETAIL_COLUMN_REMOVE = 6;

    private void calculateTotal(){
        DefaultTableModel dtm = (DefaultTableModel)jTableBillDetail.getModel();
        float total = 0.0f;
        try{
            for(int i = 0; i < dtm.getRowCount(); i++){
                float amount = Float.parseFloat(dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_AMOUNT).toString());
                int quantity = Integer.parseInt(dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString());
                total += amount * quantity;
            }
        }catch(Exception ex){ex.printStackTrace();}
        jTextFieldTotal.setText(Float.toString(total));
    }

    private void calculateBalance(){
        String sTotal=jTextFieldTotal.getText();
        String sPaid=jTextFieldPaid.getText();
        float total=0.0f;
        float paid=0.0f;
        float balance=0.0f;
        try{
            if(!sTotal.equals("")){
                total=Float.parseFloat(sTotal);
            }
        }catch(Exception ex){ex.printStackTrace();}
        try{
            if(!sPaid.equals("")){
                paid=Float.parseFloat(sPaid);
            }
        }catch(Exception ex){ex.printStackTrace();}
        balance=paid-total;
        jTextFieldBalance.setText(Float.toString(balance));
    }

    private void calculateAmount(){
        String sPrice="";
        String sTax="";
        String sQuantity="";
        float price=0.0f;
        float tax=0.0f;
        int quantity=1;
        float amount=0.0f;
        try{
            sPrice=jTextFieldPrice.getText();
            if(!sPrice.trim().equals("")){
               price=Float.parseFloat(sPrice);
            }
        }catch(Exception ex){ex.printStackTrace();}
        try{
            sTax=jTextFieldTax.getText();
            if(!sTax.trim().equals("")){
                tax=Float.parseFloat(sTax);
            }
        }catch(Exception ex){ex.printStackTrace();}
        try{
            if(jComboBoxQuantity != null){
                sQuantity = (String)jComboBoxQuantity.getSelectedItem();
                if(sQuantity == null){
                    sQuantity=Integer.toString(1);
                }
            }
            if(!sQuantity.trim().equals("")){
               quantity=Integer.parseInt(sQuantity);
            }
        }catch(Exception ex){ex.printStackTrace();}
        if(jTextFieldTax.isEnabled()){
            amount = (price + ((tax/100)*price))*quantity;
        }else{
            amount = price * quantity;
        }
        jTextFieldAmount.setText(Float.toString(amount));
    }

    private void jTableBillDetailTableModelListener(){
        final DefaultTableModel dtm = (DefaultTableModel)jTableBillDetail.getModel();
        dtm.addTableModelListener(new javax.swing.event.TableModelListener() {

            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                int editingRow = jTableBillDetail.getEditingRow();
                int rowCount = jTableBillDetail.getRowCount();
                jTableBillDetail.setEditingRow(-1);
                if(editingRow != -1 && dtm.getValueAt(editingRow, TABLE_BILL_DETAIL_COLUMN_REMOVE) == true ){
                    if(editingRow == rowCount - 1){
                        try{
                            dtm.removeRow(editingRow);
                        }catch(Exception ex){ex.printStackTrace();}
                    }
                    else{
                        dtm.removeRow(editingRow);
                    }
                    try{
                        rowCount = jTableBillDetail.getRowCount();
                        for(int i = editingRow; i < rowCount; i++){
                            int srNo = Integer.parseInt(dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_SERIAL_NUMBER).toString());
                            dtm.setValueAt(srNo - 1, i, TABLE_BILL_DETAIL_COLUMN_SERIAL_NUMBER);
                        }
                    }catch(Exception ex){ex.printStackTrace();}
                }
                calculateTotal();
            }
        });
    }

    private void jTextFieldTotalDocumentListener(){
        jTextFieldTotal.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateBalance();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateBalance();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    private void jTextFieldPaidDocumentListener(){
        jTextFieldPaid.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calculateBalance();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calculateBalance();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });
    }

    private void jTextFieldTaxDocumentListener(){
        jTextFieldTax.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

           @Override
           public void insertUpdate(javax.swing.event.DocumentEvent e) {
               try {
                   calculateAmount();
               } catch (Exception ex) {ex.printStackTrace();}
           }

           @Override
           public void removeUpdate(javax.swing.event.DocumentEvent e) {
               try {
                   calculateAmount();
               } catch (Exception ex) {ex.printStackTrace();}
           }

           @Override
           public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });
    }

    private void jTextFieldPriceDocumentListener(){
        jTextFieldPrice.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

           @Override
           public void insertUpdate(javax.swing.event.DocumentEvent e) {
               try {
                   calculateAmount();
               } catch (Exception ex) {ex.printStackTrace();}
           }

           @Override
           public void removeUpdate(javax.swing.event.DocumentEvent e) {
               try {
                   calculateAmount();
               } catch (Exception ex) {ex.printStackTrace();}
           }

           @Override
           public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });
    }

    private void onSearch(){
        String selectedProduct=jComboBoxSearch.getSelectedItem().toString().split(":")[1];
        final Product p=new ProductModel().fetchSingleProduct(selectedProduct);
        jTextFieldCode.setText(p.getCode());
        jTextFieldCategory.setText(p.getCategory());
        jTextFieldBrand.setText(p.getBrand());
        jTextFieldPrice.setText(Float.toString(p.getPrice()));
        jTextFieldTax.setText(new TaxModel().fetchTax("vat"));
        if(p.getStock() >= 1){
            new Thread(new Runnable(){

                @Override
                public void run() {
                    jComboBoxQuantity.removeAllItems();
                    for(int i = 1; i <= p.getStock(); i++){
                        jComboBoxQuantity.addItem(Integer.toString(i));
                        jButtonAddToCart.setEnabled(true);
                    }
                }
            }).start();
        }else{
            new Thread(new Runnable(){

                @Override
                public void run() {
                    jButtonAddToCart.setEnabled(false);
                    jComboBoxQuantity.removeAllItems();
                    jComboBoxQuantity.addItem("0");
                    JOptionPane.showMessageDialog(null, "No stock available", "Stock", JOptionPane.ERROR_MESSAGE);
                }
            }).start();
        }
        try {
            calculateAmount();
        } catch (Exception e) {e.printStackTrace();}
        try{
            jLabelImage.setIcon(new javax.swing.ImageIcon(imagePath+p.getImage()));
            jLabelImage.setToolTipText(p.getDescription());
        }catch(Exception ex){ex.printStackTrace();}
    }

    private void fetchAllProduct(){
        productList=new ProductModel().fetchAllProduct();
        new Thread(new Runnable(){
            @Override
           public void run(){
               for(String item:productList){
                   jComboBoxSearch.addItem(item);
               }
           }
        }).start();
    }

    private void customCode(){
        try{
            java.util.Properties props=new java.util.Properties();
            props.load(new java.io.FileInputStream("src/defaults.properties"));
            imagePath=props.getProperty("imagePath");
        }catch(Exception ex){ex.printStackTrace();}
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel());
        fetchAllProduct();
        jTextFieldPriceDocumentListener();
        jTextFieldTaxDocumentListener();
        jTextFieldPaidDocumentListener();
        jTableBillDetailTableModelListener();
        jTextFieldTotalDocumentListener();
    }

    public BillerForm() {
        initComponents();
        customCode();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelProductDetail = new javax.swing.JPanel();
        jLabelCode = new javax.swing.JLabel();
        jTextFieldCode = new javax.swing.JTextField();
        jLabelCategory = new javax.swing.JLabel();
        jLabelBrand = new javax.swing.JLabel();
        jLabelPrice = new javax.swing.JLabel();
        jTextFieldCategory = new javax.swing.JTextField();
        jTextFieldBrand = new javax.swing.JTextField();
        jTextFieldPrice = new javax.swing.JTextField();
        jLabelImage = new javax.swing.JLabel();
        jButtonAddToCart = new javax.swing.JButton();
        jLabelTax = new javax.swing.JLabel();
        jTextFieldTax = new javax.swing.JTextField();
        jLabelQuantity = new javax.swing.JLabel();
        jComboBoxQuantity = new javax.swing.JComboBox();
        jLabelAmount = new javax.swing.JLabel();
        jTextFieldAmount = new javax.swing.JTextField();
        jPanelSearch = new javax.swing.JPanel();
        jComboBoxSearch = new javax.swing.JComboBox();
        jButtonSearch = new javax.swing.JButton();
        jPanelBillDetail = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBillDetail = new javax.swing.JTable();
        jLabelTotal = new javax.swing.JLabel();
        jTextFieldTotal = new javax.swing.JTextField();
        jButtonPreviewBill = new javax.swing.JButton();
        jTextFieldPaid = new javax.swing.JTextField();
        jTextFieldBalance = new javax.swing.JTextField();
        jLabelPaid = new javax.swing.JLabel();
        jLabelBalance = new javax.swing.JLabel();
        jTextFieldCustomerName = new javax.swing.JTextField();
        jLabelCustomerName = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemFileExit = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemEditPreferences = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemHelpAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("De El");
        setPreferredSize(new java.awt.Dimension(500, 500));
        setResizable(false);

        jPanelProductDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Detail", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP));

        jLabelCode.setText("Code");
        jLabelCode.setFocusable(false);
        jLabelCode.setRequestFocusEnabled(false);

        jTextFieldCode.setEditable(false);
        jTextFieldCode.setFocusable(false);
        jTextFieldCode.setRequestFocusEnabled(false);

        jLabelCategory.setText("Category");
        jLabelCategory.setFocusable(false);
        jLabelCategory.setRequestFocusEnabled(false);

        jLabelBrand.setText("Brand");
        jLabelBrand.setFocusable(false);
        jLabelBrand.setRequestFocusEnabled(false);

        jLabelPrice.setText("Price");
        jLabelPrice.setFocusable(false);
        jLabelPrice.setRequestFocusEnabled(false);

        jTextFieldCategory.setEditable(false);
        jTextFieldCategory.setFocusable(false);
        jTextFieldCategory.setRequestFocusEnabled(false);

        jTextFieldBrand.setEditable(false);
        jTextFieldBrand.setFocusable(false);
        jTextFieldBrand.setRequestFocusEnabled(false);

        jTextFieldPrice.setText("0.0");

        jLabelImage.setBackground(new java.awt.Color(171, 138, 138));
        jLabelImage.setFocusable(false);
        jLabelImage.setRequestFocusEnabled(false);

        jButtonAddToCart.setText("Add To Cart");
        jButtonAddToCart.setPreferredSize(new java.awt.Dimension(100, 40));
        jButtonAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddToCartActionPerformed(evt);
            }
        });

        jLabelTax.setText("Tax");
        jLabelTax.setFocusable(false);
        jLabelTax.setRequestFocusEnabled(false);

        jTextFieldTax.setText("0.0");

        jLabelQuantity.setText("Quantity");

        jComboBoxQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxQuantityActionPerformed(evt);
            }
        });

        jLabelAmount.setText("Amount");

        jTextFieldAmount.setEditable(false);
        jTextFieldAmount.setText("0.0");
        jTextFieldAmount.setFocusable(false);
        jTextFieldAmount.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanelProductDetailLayout = new javax.swing.GroupLayout(jPanelProductDetail);
        jPanelProductDetail.setLayout(jPanelProductDetailLayout);
        jPanelProductDetailLayout.setHorizontalGroup(
            jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCategory)
                    .addComponent(jLabelBrand)
                    .addComponent(jLabelPrice)
                    .addComponent(jLabelCode, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTax)
                    .addComponent(jLabelQuantity)
                    .addComponent(jLabelAmount))
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTax, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(jTextFieldBrand)
                                .addComponent(jTextFieldCategory)
                                .addComponent(jTextFieldCode))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProductDetailLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(99, 99, 99)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelProductDetailLayout.setVerticalGroup(
            jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCode)
                    .addComponent(jTextFieldCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCategory)
                    .addComponent(jTextFieldCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBrand)
                    .addComponent(jTextFieldBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPrice)
                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTax)
                    .addComponent(jTextFieldTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelQuantity)
                    .addComponent(jComboBoxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAmount)
                    .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Search", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP));

        jComboBoxSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSearchActionPerformed(evt);
            }
        });

        jButtonSearch.setText("Search");
        jButtonSearch.setFocusable(false);
        jButtonSearch.setRequestFocusEnabled(false);
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSearchLayout = new javax.swing.GroupLayout(jPanelSearch);
        jPanelSearch.setLayout(jPanelSearchLayout);
        jPanelSearchLayout.setHorizontalGroup(
            jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchLayout.createSequentialGroup()
                .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        jPanelSearchLayout.setVerticalGroup(
            jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jComboBoxSearch)
                .addComponent(jButtonSearch))
        );

        jPanelBillDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bill Detail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.black));

        jTableBillDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr. No.", "Code", "Product", "Price", "Quantity", "Amount", "Remove"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableBillDetail.setColumnSelectionAllowed(true);
        jTableBillDetail.setFocusable(false);
        jTableBillDetail.setRequestFocusEnabled(false);
        jTableBillDetail.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableBillDetail);
        jTableBillDetail.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableBillDetail.getColumnModel().getColumn(6).setResizable(false);

        jLabelTotal.setText("Total");
        jLabelTotal.setFocusable(false);
        jLabelTotal.setRequestFocusEnabled(false);

        jTextFieldTotal.setEditable(false);
        jTextFieldTotal.setText("0.0");
        jTextFieldTotal.setFocusable(false);
        jTextFieldTotal.setRequestFocusEnabled(false);

        jButtonPreviewBill.setText("Preview Bill");
        jButtonPreviewBill.setMaximumSize(new java.awt.Dimension(90, 40));
        jButtonPreviewBill.setMinimumSize(new java.awt.Dimension(90, 40));
        jButtonPreviewBill.setPreferredSize(new java.awt.Dimension(90, 40));
        jButtonPreviewBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviewBillActionPerformed(evt);
            }
        });

        jTextFieldPaid.setText("0.0");

        jTextFieldBalance.setEditable(false);
        jTextFieldBalance.setText("0.0");
        jTextFieldBalance.setFocusable(false);
        jTextFieldBalance.setRequestFocusEnabled(false);

        jLabelPaid.setText("Paid");

        jLabelBalance.setText("Balance");

        jLabelCustomerName.setText("Customer Name");

        javax.swing.GroupLayout jPanelBillDetailLayout = new javax.swing.GroupLayout(jPanelBillDetail);
        jPanelBillDetail.setLayout(jPanelBillDetailLayout);
        jPanelBillDetailLayout.setHorizontalGroup(
            jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBillDetailLayout.createSequentialGroup()
                        .addComponent(jLabelCustomerName)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldCustomerName)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPaid)
                            .addComponent(jLabelBalance))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldBalance, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jTextFieldPaid, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jTextFieldTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jButtonPreviewBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(17, 17, 17))))
        );
        jPanelBillDetailLayout.setVerticalGroup(
            jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotal)
                    .addComponent(jTextFieldTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCustomerName))
                .addGap(18, 18, 18)
                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPaid)
                    .addComponent(jTextFieldPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBalance)
                    .addComponent(jTextFieldBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonPreviewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuFile.setText("File");

        jMenuItemFileExit.setText("Exit");
        jMenuItemFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemFileExit);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Edit");

        jMenuItemEditPreferences.setText("Preferences");
        jMenuEdit.add(jMenuItemEditPreferences);

        jMenuBar1.add(jMenuEdit);

        jMenuHelp.setText("Help");

        jMenuItemHelpAbout.setText("About");
        jMenuItemHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHelpAboutActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemHelpAbout);

        jMenuBar1.add(jMenuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelBillDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelProductDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBillDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-854)/2, (screenSize.height-646)/2, 854, 646);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSearchActionPerformed
        onSearch();
    }//GEN-LAST:event_jComboBoxSearchActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        onSearch();
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jComboBoxQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxQuantityActionPerformed
        try {
            calculateAmount();
        } catch (Exception e) {e.printStackTrace();}
    }//GEN-LAST:event_jComboBoxQuantityActionPerformed

    private void jMenuItemFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFileExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemFileExitActionPerformed

    private void jButtonAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddToCartActionPerformed
        DefaultTableModel dtm = (DefaultTableModel)jTableBillDetail.getModel();
        String code = (String) jTextFieldCode.getText();
        boolean isProductInCart = false;
        int row = -1;
        for(int i = 0; i < dtm.getRowCount(); i++){
            row = i;
            String tempCode = (String) dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_CODE);
            if(tempCode.equals(code)){
                isProductInCart = true;
                break;
            }
        }
        if(isProductInCart){
            int existingQuantity = Integer.parseInt(dtm.getValueAt(row, TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString());
            int newQuantity = Integer.parseInt(jComboBoxQuantity.getSelectedItem().toString());
            dtm.setValueAt(existingQuantity + newQuantity, row, TABLE_BILL_DETAIL_COLUMN_QUANTITY);
        }else{
            Object[] o={
                dtm.getRowCount() + 1,
                jTextFieldCode.getText(),
                jTextFieldCategory.getText() + jTextFieldBrand.getText(),
                jTextFieldPrice.getText(),
                jComboBoxQuantity.getSelectedItem(),
                jTextFieldAmount.getText(),
            };
            dtm.addRow(o);
        }
    }//GEN-LAST:event_jButtonAddToCartActionPerformed

    private void jButtonPreviewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviewBillActionPerformed
            new PrintBillForm((DefaultTableModel)jTableBillDetail.getModel(),
                jTextFieldTotal.getText().toString(),
                jTextFieldPaid.getText().toString(),
                jTextFieldBalance.getText().toString(),
                jTextFieldCustomerName.getText().toString()
            );
    }//GEN-LAST:event_jButtonPreviewBillActionPerformed

    private void jMenuItemHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHelpAboutActionPerformed
        JOptionPane.showMessageDialog(null, "\t\t De El \n Version: 1.0", "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItemHelpAboutActionPerformed


    // <editor-fold defaultstate="collapsed" desc="class fields">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddToCart;
    private javax.swing.JButton jButtonPreviewBill;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JComboBox jComboBoxQuantity;
    private javax.swing.JComboBox jComboBoxSearch;
    private javax.swing.JLabel jLabelAmount;
    private javax.swing.JLabel jLabelBalance;
    private javax.swing.JLabel jLabelBrand;
    private javax.swing.JLabel jLabelCategory;
    private javax.swing.JLabel jLabelCode;
    private javax.swing.JLabel jLabelCustomerName;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelPaid;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JLabel jLabelQuantity;
    private javax.swing.JLabel jLabelTax;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemEditPreferences;
    private javax.swing.JMenuItem jMenuItemFileExit;
    private javax.swing.JMenuItem jMenuItemHelpAbout;
    private javax.swing.JPanel jPanelBillDetail;
    private javax.swing.JPanel jPanelProductDetail;
    private javax.swing.JPanel jPanelSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBillDetail;
    private javax.swing.JTextField jTextFieldAmount;
    private javax.swing.JTextField jTextFieldBalance;
    private javax.swing.JTextField jTextFieldBrand;
    private javax.swing.JTextField jTextFieldCategory;
    private javax.swing.JTextField jTextFieldCode;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldPaid;
    private javax.swing.JTextField jTextFieldPrice;
    private javax.swing.JTextField jTextFieldTax;
    private javax.swing.JTextField jTextFieldTotal;
    // End of variables declaration//GEN-END:variables
}
//</editor-fold>