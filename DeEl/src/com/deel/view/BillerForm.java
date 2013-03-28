package com.deel.view;

import com.deel.domain.Product;
import com.deel.model.ProductModel;
import com.deel.model.TaxModel;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
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
    private ArrayList<String> productList;
    private DefaultTableModel dtm;
    protected static final int TABLE_BILL_DETAIL_COLUMN_SERIAL_NUMBER = 0;
    protected static final int TABLE_BILL_DETAIL_COLUMN_CODE = 1;
    protected static final int TABLE_BILL_DETAIL_COLUMN_PRODUCT = 2;
    protected static final int TABLE_BILL_DETAIL_COLUMN_PRICE = 3;
    protected static final int TABLE_BILL_DETAIL_COLUMN_QUANTITY = 4;
    protected static final int TABLE_BILL_DETAIL_COLUMN_AMOUNT = 5;
    protected static final int TABLE_BILL_DETAIL_COLUMN_TAX = 6;
    protected static final int TABLE_BILL_DETAIL_COLUMN_REMOVE = 7;
    
    private String getItemCount() {
        int intItemCount = dtm.getRowCount();
        String itemCount = Integer.toString(intItemCount);
        return itemCount;
    }
    
    private String getQuantityCount() {
        int intQuantityCount = 0;
        for(int i = 0; i < dtm.getRowCount(); i++) {
            intQuantityCount += Integer.parseInt(dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString());
        }
        String quantityCount = Integer.toString(intQuantityCount);
        return quantityCount;
    }    
   
    private void calculateTaxAmount() {
        float taxAmount = 0.0f;
        try {
            for(int i = 0; i < dtm.getRowCount(); i++) {
                float tax = Float.parseFloat(dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_TAX).toString());
                taxAmount += tax;
            }
        }catch(Exception ex){ex.printStackTrace();}
        jTextFieldTaxAmount.setText(String.format("%.2f", taxAmount));
    }
    
    private void calculateTotal() {
        float total = 0.0f;
        try {
            for(int i = 0; i < dtm.getRowCount(); i++) {
                float tax = Float.parseFloat(dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_TAX).toString());
                float amount = Float.parseFloat(dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_AMOUNT).toString());
                total += (tax + amount);
            }
        }catch(Exception ex){ex.printStackTrace();}
        jTextFieldTotal.setText(String.format("%.2f", total));
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
        jTextFieldBalance.setText(String.format("%.2f", balance));
    }   
    
    private void previewBill(){
        String billDetailItemCount = getItemCount();
        String billDetailQuantityCount = getQuantityCount();
        String billDetailTax = jTextFieldTax.getText().toString();    
        String billDetailTaxAmount = jTextFieldTaxAmount.getText().toString();    
        String billDetailTotal = jTextFieldTotal.getText().toString();
        String billDetailPaid = jTextFieldPaid.getText().toString();
        String billDetailBalance = jTextFieldBalance.getText().toString();
        String billDetailCustomerName = jTextFieldCustomerName.getText().toString();        
        new PrintBillForm(
            dtm,
            billDetailItemCount,
            billDetailQuantityCount,
            billDetailTax,
            billDetailTaxAmount,
            billDetailTotal,
            billDetailPaid,
            billDetailBalance,
            billDetailCustomerName
        );
    }
    
    private float calculateTax() {
         float amount = Float.parseFloat(jTextFieldAmount.getText());
         float taxPercent = Float.parseFloat(jTextFieldTax.getText());
         float tax = (taxPercent / 100) * amount;
         return tax;
    }
     
    private void calculateAmount() {
        float price = 0.0f;
        int quantity = 0;
        String strPrice = "";
        String strQuantity = "";
        strPrice = jTextFieldPrice.getText();
        try {
            if(jComboBoxQuantity.getSelectedItem() != null) {
                strQuantity = jComboBoxQuantity.getSelectedItem().toString();
            } else {
                strQuantity = "1";
            }
        } catch(Exception ex) {ex.printStackTrace();}
        if (!strPrice.trim().equals("")) {
            price = Float.parseFloat(strPrice);
        }
        if(!strQuantity.trim().equals("")) {
            quantity = Integer.parseInt(strQuantity);
        }
        float amount = price * quantity;
        jTextFieldAmount.setText(String.format("%.2f", amount));
    }
    
    private void addToCart(){
        String code = (String) jTextFieldCode.getText();
        String product = jTextFieldCategory.getText() + " " + jTextFieldBrand.getText();
        String price = jTextFieldPrice.getText();
        String quantity = jComboBoxQuantity.getSelectedItem().toString();
        String amount = jTextFieldAmount.getText();
        String tax = String.format("%.2f", calculateTax());
        if(isProductInCart(code)){
            int row = getRowOfProductInCart(code);
            int existingQuantity = Integer.parseInt(dtm.getValueAt(row, TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString());
            int newQuantity = Integer.parseInt(jComboBoxQuantity.getSelectedItem().toString());
            int updatedQuantity = existingQuantity + newQuantity;
            float existingAmount = Float.parseFloat(dtm.getValueAt(row, TABLE_BILL_DETAIL_COLUMN_AMOUNT).toString());
            float newAmount = Float.parseFloat(jTextFieldAmount.getText());
            float updatedAmount = existingAmount + newAmount;
            dtm.setValueAt(updatedQuantity, row, TABLE_BILL_DETAIL_COLUMN_QUANTITY);
            dtm.setValueAt(updatedAmount, row, TABLE_BILL_DETAIL_COLUMN_AMOUNT);
        }else{
            Object[] o = {
                dtm.getRowCount() + 1,
                code,
                product,
                price,
                quantity,
                amount,
                tax,
            };
            dtm.addRow(o);            
        }
        if(jCheckBoxMenuItemManualBilling.isSelected() == true){
            jTextFieldCode.requestFocus();
        }else{
            jComboBoxSearch.requestFocus();
        }
    }
    
    private boolean isProductInCart(String code){ 
        boolean isProductInCart = false;
        for(int i = 0; i < dtm.getRowCount(); i++){            
            String tempCode = (String) dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_CODE);
            if(tempCode.equals(code)){
                isProductInCart = true;
                break;
            }
        }
        return isProductInCart;
    }    
    
    private int getRowOfProductInCart(String code){        
        int row = -1;        
        for(int i = 0; i < dtm.getRowCount(); i++){
            String tempCode = (String) dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_CODE);
            if(tempCode.equals(code)){                
                row = i;
                break;
            }
        }
        return row;
    }
    
    private int getQuantityOfProductInCart(String code){        
        int row = getRowOfProductInCart(code);
        String strQuantity =  dtm.getValueAt(row, TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString();
        int quantity = Integer.parseInt(strQuantity);
        return quantity;
    }
    
    private void updateProductList(final ArrayList<String> productList){
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) jComboBoxSearch.getModel();
        DefaultComboBoxModel dcbm1 = (DefaultComboBoxModel) jComboBoxQuantity.getModel();
        dcbm.removeAllElements();
        if(productList.size() > 0){
            for(String item: productList){
                dcbm.addElement(item);
            }
        }else{
            dcbm.addElement("<Item not found>");
            jTextFieldCode.setText("");
            jTextFieldCategory.setText("");
            jTextFieldBrand.setText("");
            jTextFieldTax.setText("");
            dcbm1.removeAllElements();
            dcbm1.addElement("0");
            try{
                jLabelImage.setIcon(null);
                jLabelImage.setToolTipText(null);
            }catch(Exception ex){ex.printStackTrace();}
        }
        jComboBoxSearch.getEditor().selectAll();
    }
    
    private void onProductSelection(String searchQuery){      
        String selectedProduct = searchQuery.split(":")[1];
        Product p = ProductModel.fetchSingleProduct(selectedProduct);
        if(p != null){
            jTextFieldCode.setText(p.getCode());
            jTextFieldCategory.setText(p.getCategory());
            jTextFieldBrand.setText(p.getBrand());
            jTextFieldPrice.setText(String.format("%.2f", p.getPrice()));
            if(jCheckBoxMenuItemPreferencesTax.isSelected()) {
                jTextFieldTax.setText(new TaxModel().fetchTax("vat"));
            } else {
                jTextFieldTax.setText("0");
            }            
            DefaultComboBoxModel dcbm = (DefaultComboBoxModel) jComboBoxQuantity.getModel();
            dcbm.removeAllElements();
            if(p.getStock() >= 1){
                String code = (String) jTextFieldCode.getText();
                int existingQuantityInCart = 0;
                int quantity = p.getStock();                    
                if(isProductInCart(code)){
                   existingQuantityInCart = getQuantityOfProductInCart(code);
                }
                int newQuantity = quantity - existingQuantityInCart;
                if(newQuantity >= 1){
                    for(int i = 1; i <= newQuantity; i++){
                        dcbm.addElement(Integer.toString(i));
                    }
                    jButtonAddToCart.setEnabled(true);
                }else{
                    jButtonAddToCart.setEnabled(false);
                    dcbm.addElement("0");
                    //JOptionPane.showMessageDialog(null, "All available stocks are in the cart", "No Stock", JOptionPane.ERROR_MESSAGE);
                }
            }else{            
                jButtonAddToCart.setEnabled(false);
                dcbm.addElement("0");
                JOptionPane.showMessageDialog(null, "No stocks available", "No Stock", JOptionPane.ERROR_MESSAGE);
            }
            calculateAmount();
            try{
                jLabelImage.setIcon(new javax.swing.ImageIcon(imagePath + p.getImage()));
                jLabelImage.setToolTipText(p.getDescription());
            }catch(Exception ex){ex.printStackTrace();}
        }
    }
    
    private void onSearch(){
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) jComboBoxSearch.getModel();
        String searchQuery = (String) dcbm.getSelectedItem();        
        if(searchQuery == null){
            searchQuery = "";
        }
        if(searchQuery.contains(":")){
            onProductSelection(searchQuery);
        }else{
            if(searchQuery.equals("")){
                productList = (ArrayList) ProductModel.fetchAllProduct();
            }else{
                productList = (ArrayList) ProductModel.dynamicSearch(searchQuery);
            }
            updateProductList(productList);
        }
    }    
      
    private void customCode(){
        try {
            java.util.Properties props=new java.util.Properties();
            props.load(new java.io.FileInputStream("src/defaults.properties"));
            imagePath=props.getProperty("imagePath");
        } catch(Exception ex) {ex.printStackTrace();}
        dtm = (DefaultTableModel) jTableBillDetail.getModel();
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel());
        onSearch();
        jTextFieldCodeDocumentListener();
        jTextFieldPriceDocumentListener();
        jTextFieldTotalDocumentListener();
        jTextFieldPaidDocumentListener();
        jTableBillDetailTableModelListener();
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
        jTextFieldCategory = new javax.swing.JTextField();
        jTextFieldBrand = new javax.swing.JTextField();
        jLabelImage = new javax.swing.JLabel();
        jButtonAddToCart = new javax.swing.JButton();
        jLabelTax = new javax.swing.JLabel();
        jTextFieldTax = new javax.swing.JTextField();
        jLabelQuantity = new javax.swing.JLabel();
        jComboBoxQuantity = new javax.swing.JComboBox();
        jLabelAmount = new javax.swing.JLabel();
        jTextFieldAmount = new javax.swing.JTextField();
        jLabelPrice = new javax.swing.JLabel();
        jTextFieldPrice = new javax.swing.JTextField();
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
        jLabelTaxAmount = new javax.swing.JLabel();
        jTextFieldTaxAmount = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemFileExit = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuPreferences = new javax.swing.JMenu();
        jCheckBoxMenuItemPrefrencesDynamicSearch = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemPreferencesTax = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemManualBilling = new javax.swing.JCheckBoxMenuItem();
        jMenuItemPreferencesAddProduct = new javax.swing.JMenuItem();
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
        jTextFieldCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCodeFocusGained(evt);
            }
        });

        jLabelCategory.setText("Category");
        jLabelCategory.setFocusable(false);
        jLabelCategory.setRequestFocusEnabled(false);

        jLabelBrand.setText("Brand");
        jLabelBrand.setFocusable(false);
        jLabelBrand.setRequestFocusEnabled(false);

        jTextFieldCategory.setEditable(false);
        jTextFieldCategory.setFocusable(false);
        jTextFieldCategory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCategoryFocusGained(evt);
            }
        });

        jTextFieldBrand.setEditable(false);
        jTextFieldBrand.setFocusable(false);
        jTextFieldBrand.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldBrandFocusGained(evt);
            }
        });

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
        jButtonAddToCart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonAddToCartKeyPressed(evt);
            }
        });

        jLabelTax.setText("Tax");
        jLabelTax.setFocusable(false);
        jLabelTax.setRequestFocusEnabled(false);

        jTextFieldTax.setEditable(false);
        jTextFieldTax.setText("0.0");
        jTextFieldTax.setEnabled(false);
        jTextFieldTax.setFocusable(false);
        jTextFieldTax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldTaxFocusGained(evt);
            }
        });

        jLabelQuantity.setText("Quantity");

        jComboBoxQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxQuantityActionPerformed(evt);
            }
        });

        jLabelAmount.setText("Amount");

        jTextFieldAmount.setEditable(false);
        jTextFieldAmount.setText("0.00");
        jTextFieldAmount.setFocusable(false);
        jTextFieldAmount.setRequestFocusEnabled(false);

        jLabelPrice.setText("Price");

        jTextFieldPrice.setEditable(false);
        jTextFieldPrice.setText("0.00");
        jTextFieldPrice.setFocusable(false);

        javax.swing.GroupLayout jPanelProductDetailLayout = new javax.swing.GroupLayout(jPanelProductDetail);
        jPanelProductDetail.setLayout(jPanelProductDetailLayout);
        jPanelProductDetailLayout.setHorizontalGroup(
            jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCategory)
                    .addComponent(jLabelBrand)
                    .addComponent(jLabelCode, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTax, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextFieldPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jComboBoxQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldBrand)
                        .addComponent(jTextFieldCategory)
                        .addComponent(jTextFieldCode))
                    .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
            .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                .addGroup(jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPrice)
                    .addComponent(jLabelTax)
                    .addComponent(jLabelQuantity)
                    .addComponent(jLabelAmount)
                    .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelProductDetailLayout.setVerticalGroup(
            jPanelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProductDetailLayout.createSequentialGroup()
                .addContainerGap()
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
                .addGap(18, 18, 18)
                .addComponent(jButtonAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Search", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP));

        jComboBoxSearch.setEditable(true);
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
                "Sr. No.", "Code", "Product", "Price", "Quantity", "Amount", "Vat", "Remove"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
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
        jTableBillDetail.getColumnModel().getColumn(7).setResizable(false);

        jLabelTotal.setText("Total");
        jLabelTotal.setFocusable(false);
        jLabelTotal.setRequestFocusEnabled(false);

        jTextFieldTotal.setEditable(false);
        jTextFieldTotal.setText("0.00");
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
        jButtonPreviewBill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonPreviewBillKeyPressed(evt);
            }
        });

        jTextFieldPaid.setText("0.00");
        jTextFieldPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldPaidFocusGained(evt);
            }
        });

        jTextFieldBalance.setEditable(false);
        jTextFieldBalance.setText("0.00");
        jTextFieldBalance.setFocusable(false);
        jTextFieldBalance.setRequestFocusEnabled(false);

        jLabelPaid.setText("Paid");

        jLabelBalance.setText("Balance");

        jTextFieldCustomerName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCustomerNameFocusGained(evt);
            }
        });

        jLabelCustomerName.setText("Customer Name");

        jLabelTaxAmount.setText("VAT Amount");

        jTextFieldTaxAmount.setEditable(false);
        jTextFieldTaxAmount.setText("0.00");
        jTextFieldTaxAmount.setFocusable(false);
        jTextFieldTaxAmount.setRequestFocusEnabled(false);

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
                            .addComponent(jLabelPaid)
                            .addComponent(jLabelBalance)
                            .addComponent(jLabelTaxAmount)
                            .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBillDetailLayout.createSequentialGroup()
                                .addComponent(jTextFieldPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))
                            .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonPreviewBill, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jTextFieldBalance, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jTextFieldTaxAmount))
                                .addContainerGap())))))
        );
        jPanelBillDetailLayout.setVerticalGroup(
            jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCustomerName)
                    .addComponent(jLabelTaxAmount)
                    .addComponent(jTextFieldTaxAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotal)
                    .addComponent(jTextFieldTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(119, Short.MAX_VALUE))
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

        jMenuPreferences.setText("Preferences");

        jCheckBoxMenuItemPrefrencesDynamicSearch.setSelected(true);
        jCheckBoxMenuItemPrefrencesDynamicSearch.setText("Dynamic Search");
        jCheckBoxMenuItemPrefrencesDynamicSearch.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemPrefrencesDynamicSearchItemStateChanged(evt);
            }
        });
        jMenuPreferences.add(jCheckBoxMenuItemPrefrencesDynamicSearch);

        jCheckBoxMenuItemPreferencesTax.setText("Tax");
        jCheckBoxMenuItemPreferencesTax.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemPreferencesTaxItemStateChanged(evt);
            }
        });
        jMenuPreferences.add(jCheckBoxMenuItemPreferencesTax);

        jCheckBoxMenuItemManualBilling.setText("Manual Billing");
        jCheckBoxMenuItemManualBilling.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemManualBillingItemStateChanged(evt);
            }
        });
        jMenuPreferences.add(jCheckBoxMenuItemManualBilling);

        jMenuItemPreferencesAddProduct.setText("Add Product");
        jMenuItemPreferencesAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreferencesAddProductActionPerformed(evt);
            }
        });
        jMenuPreferences.add(jMenuItemPreferencesAddProduct);

        jMenuEdit.add(jMenuPreferences);

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
                        .addComponent(jPanelProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(854, 659));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTableBillDetailTableModelListener(){
        if(dtm.getRowCount() == 0){
            jButtonPreviewBill.setEnabled(false);
        }
        dtm.addTableModelListener(new javax.swing.event.TableModelListener() {

            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                if(dtm.getRowCount() == 0) {
                    jButtonPreviewBill.setEnabled(false);
                }else {
                    jButtonPreviewBill.setEnabled(true);            
                }
                int editingRow = jTableBillDetail.getEditingRow();
                int rowCount = jTableBillDetail.getRowCount();
                jTableBillDetail.setEditingRow(-1);
                if(editingRow != -1 && dtm.getValueAt(editingRow, TABLE_BILL_DETAIL_COLUMN_REMOVE) == true ) {
                    if(editingRow == rowCount - 1) {
                        try {
                            dtm.removeRow(editingRow);
                        } catch(Exception ex){ex.printStackTrace();}
                    }
                    else {
                        dtm.removeRow(editingRow);
                    }
                    try {
                        rowCount = jTableBillDetail.getRowCount();
                        for(int i = editingRow; i < rowCount; i++) {
                            int serialNumber = Integer.parseInt(dtm.getValueAt(i, TABLE_BILL_DETAIL_COLUMN_SERIAL_NUMBER).toString());
                            dtm.setValueAt(serialNumber - 1, i, TABLE_BILL_DETAIL_COLUMN_SERIAL_NUMBER);
                        }
                    }catch(Exception ex){ex.printStackTrace();}
                }
                calculateTaxAmount();
                calculateTotal();
                onSearch();
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
    
    private void jTextFieldCodeDocumentListener(){
        jTextFieldCode.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if(jTextFieldCode.getText().trim().equals("")){
                    jButtonAddToCart.setEnabled(false);
                }else{
                    jButtonAddToCart.setEnabled(true);            
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(jTextFieldCode.getText().trim().equals("")){
                    jButtonAddToCart.setEnabled(false);
                }else{
                    jButtonAddToCart.setEnabled(true);            
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        
    }
    
    private void jTextFieldPriceDocumentListener() {
        jTextFieldPrice.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    private void jComboBoxSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSearchActionPerformed
        onSearch();
    }//GEN-LAST:event_jComboBoxSearchActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        onSearch();
        dispatchEvent(new ActionEvent(jComboBoxSearch, 0, null));//search button not working
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jMenuItemFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFileExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemFileExitActionPerformed

    private void jButtonAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddToCartActionPerformed
        addToCart();
    }//GEN-LAST:event_jButtonAddToCartActionPerformed

    private void jButtonPreviewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviewBillActionPerformed
        previewBill();
    }//GEN-LAST:event_jButtonPreviewBillActionPerformed

    private void jMenuItemHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHelpAboutActionPerformed
        JOptionPane.showMessageDialog(null, "\t\t De El \n Version: 1.0", "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItemHelpAboutActionPerformed

    private void jCheckBoxMenuItemPrefrencesDynamicSearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemPrefrencesDynamicSearchItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            jComboBoxSearch.setEditable(true);
        }else{            
            jComboBoxSearch.setEditable(false);
            DefaultComboBoxModel dcbm = (DefaultComboBoxModel) jComboBoxSearch.getModel();
            dcbm.removeAllElements();
            dcbm.setSelectedItem("");
            onSearch();
        }
    }//GEN-LAST:event_jCheckBoxMenuItemPrefrencesDynamicSearchItemStateChanged

    private void jCheckBoxMenuItemPreferencesTaxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemPreferencesTaxItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            jTextFieldTax.setEnabled(true);
            jTextFieldTax.setText(new TaxModel().fetchTax("vat"));
        }else{
            jTextFieldTax.setEnabled(false);
            jTextFieldTax.setText("0");
        }        
    }//GEN-LAST:event_jCheckBoxMenuItemPreferencesTaxItemStateChanged

    private void jCheckBoxMenuItemManualBillingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemManualBillingItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            jTextFieldCode.setEditable(true);
            jTextFieldCode.setFocusable(true);
            jTextFieldCode.setText("");
            jTextFieldCode.requestFocus();
            
            jTextFieldCategory.setEditable(true);
            jTextFieldCategory.setFocusable(true);
            jTextFieldCategory.setText("");
            
            jTextFieldBrand.setEditable(true);
            jTextFieldBrand.setFocusable(true);
            jTextFieldBrand.setText("");       
            
            jTextFieldPrice.setEditable(true);
            jTextFieldPrice.setFocusable(true);
            jTextFieldPrice.setText("");       
            
            jTextFieldTax.setEditable(true);
            jTextFieldTax.setFocusable(true);            

            DefaultComboBoxModel dcbm = (DefaultComboBoxModel) jComboBoxQuantity.getModel();
            dcbm.removeAllElements();
            for(int i = 1; i <= 100; i++){
                dcbm.addElement(i);
            }
        }else{
            jTextFieldCode.setEditable(false);
            jTextFieldCode.setFocusable(false);
            
            jTextFieldCategory.setEditable(false);
            jTextFieldCategory.setFocusable(false);
            
            jTextFieldBrand.setEditable(false);
            jTextFieldBrand.setFocusable(false);            
            
            jTextFieldPrice.setEditable(false);
            jTextFieldPrice.setFocusable(false);
            
            jTextFieldTax.setEditable(false);
            jTextFieldTax.setFocusable(false);            
            
            jComboBoxSearch.requestFocus();
            onSearch();
        }
    }//GEN-LAST:event_jCheckBoxMenuItemManualBillingItemStateChanged

    private void jTextFieldCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCodeFocusGained
        jTextFieldCode.selectAll();
    }//GEN-LAST:event_jTextFieldCodeFocusGained

    private void jTextFieldCategoryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCategoryFocusGained
        jTextFieldCategory.selectAll();
    }//GEN-LAST:event_jTextFieldCategoryFocusGained

    private void jTextFieldBrandFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldBrandFocusGained
        jTextFieldBrand.selectAll();
    }//GEN-LAST:event_jTextFieldBrandFocusGained

    private void jTextFieldTaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldTaxFocusGained
        jTextFieldTax.selectAll();
    }//GEN-LAST:event_jTextFieldTaxFocusGained

    private void jButtonAddToCartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonAddToCartKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             addToCart();
        }
    }//GEN-LAST:event_jButtonAddToCartKeyPressed

    private void jButtonPreviewBillKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonPreviewBillKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            previewBill();
        }
    }//GEN-LAST:event_jButtonPreviewBillKeyPressed

    private void jTextFieldPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldPaidFocusGained
        jTextFieldPaid.selectAll();
    }//GEN-LAST:event_jTextFieldPaidFocusGained

    private void jTextFieldCustomerNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCustomerNameFocusGained
        jTextFieldCustomerName.selectAll();
    }//GEN-LAST:event_jTextFieldCustomerNameFocusGained

    private void jComboBoxQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxQuantityActionPerformed
        calculateAmount();
    }//GEN-LAST:event_jComboBoxQuantityActionPerformed

    private void jMenuItemPreferencesAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPreferencesAddProductActionPerformed
        new AddProductForm();
    }//GEN-LAST:event_jMenuItemPreferencesAddProductActionPerformed

    // <editor-fold defaultstate="collapsed" desc="class fields">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddToCart;
    private javax.swing.JButton jButtonPreviewBill;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemManualBilling;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemPreferencesTax;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemPrefrencesDynamicSearch;
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
    private javax.swing.JLabel jLabelTaxAmount;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemFileExit;
    private javax.swing.JMenuItem jMenuItemHelpAbout;
    private javax.swing.JMenuItem jMenuItemPreferencesAddProduct;
    private javax.swing.JMenu jMenuPreferences;
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
    private javax.swing.JTextField jTextFieldTaxAmount;
    private javax.swing.JTextField jTextFieldTotal;
    // End of variables declaration//GEN-END:variables
}
//</editor-fold>