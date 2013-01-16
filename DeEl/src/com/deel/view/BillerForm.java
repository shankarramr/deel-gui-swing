package com.deel.view;

import com.deel.domain.Product;
import com.deel.model.FetchTax;
import com.deel.model.Search;
import java.util.List;

/**
 *
 * @author neo
 */
public class BillerForm extends javax.swing.JFrame {
    private static String imagePath;
    private List<String> productList;


    private void calculateTotal(){
        String sTotal="";
        float total=0.0f;
        for(int i = 0; i < ((javax.swing.table.DefaultTableModel)jTableBillDetail.getModel()).getRowCount(); i++){
            sTotal=(String)((javax.swing.table.DefaultTableModel)jTableBillDetail.getModel()).getValueAt(i, 4);
            if(sTotal != null && !sTotal.equals("") && !sTotal.equals("null")){
                total+=Float.parseFloat(sTotal);
            }
        }
        jTextFieldTotal.setText(Float.toString(total));
        calculateBalance();
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
        amount=(price+((tax/100)*price))*quantity;
        jTextFieldAmount.setText(Float.toString(amount));
    }

    private void jTableBillDetailTableModelListener(){
        ((javax.swing.table.DefaultTableModel)jTableBillDetail.getModel()).addTableModelListener(new javax.swing.event.TableModelListener() {

            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                calculateTotal();
            }
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
        final Product p=new Search().fetchSingleProduct(selectedProduct);
        jTextFieldCode.setText(p.getCode());
        jTextFieldCategory.setText(p.getCategory());
        jTextFieldBrand.setText(p.getBrand());
        jTextFieldPrice.setText(Float.toString(p.getPrice()));
        jTextFieldTax.setText(new FetchTax().fetchTax("vat"));
        new Thread(new Runnable(){

            @Override
            public void run() {
                for(int i = 2;i <= p.getStock();i++){
                    jComboBoxQuantity.addItem(Integer.toString(i));
                }
            }
        }).start();
        try {
            calculateAmount();
        } catch (Exception e) {e.printStackTrace();}
        try{
            jLabelImage.setIcon(new javax.swing.ImageIcon(imagePath+p.getImage()));
            jLabelImage.setToolTipText(p.getDescription());
        }catch(Exception ex){ex.printStackTrace();}
    }

    private void fetchAllProduct(){
        productList=new Search().fetchAllProduct();
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

        jComboBoxQuantity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));
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
                    .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(jButtonAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(99, 99, 99)))))
                .addGap(0, 0, Short.MAX_VALUE))
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
                "Sr. No.", "Product", "Price", "Quantity", "Amount", "Remove"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
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

        javax.swing.GroupLayout jPanelBillDetailLayout = new javax.swing.GroupLayout(jPanelBillDetail);
        jPanelBillDetail.setLayout(jPanelBillDetailLayout);
        jPanelBillDetailLayout.setHorizontalGroup(
            jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBillDetailLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPaid)
                            .addComponent(jLabelBalance))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPreviewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))))
        );
        jPanelBillDetailLayout.setVerticalGroup(
            jPanelBillDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(41, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-907)/2, (screenSize.height-652)/2, 907, 652);
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
        try{
            final javax.swing.JCheckBox jcb=new javax.swing.JCheckBox();
            jcb.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    try{
                        jcb.setSelected(false);
                        ((javax.swing.table.DefaultTableModel)jTableBillDetail.getModel()).removeRow(jTableBillDetail.getSelectedRow());
                    }catch(Exception ex){ex.printStackTrace();}
                }
            });
            Object[] o={((javax.swing.table.DefaultTableModel)jTableBillDetail.getModel()).getRowCount()+1,
                jTextFieldCategory.getText()+jTextFieldBrand.getText(),jTextFieldPrice.getText(),
                jComboBoxQuantity.getSelectedItem(),jTextFieldAmount.getText(),
            };
            ((javax.swing.table.DefaultTableModel)jTableBillDetail.getModel()).addRow(o);
            jTableBillDetail.getColumnModel().getColumn(5).setCellEditor(new javax.swing.DefaultCellEditor(jcb));
        }catch(Exception ex){ex.printStackTrace();}
    }//GEN-LAST:event_jButtonAddToCartActionPerformed

    private void jButtonPreviewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviewBillActionPerformed
            new PrintBillForm((javax.swing.table.DefaultTableModel)jTableBillDetail.getModel());
    }//GEN-LAST:event_jButtonPreviewBillActionPerformed


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
    private javax.swing.JTextField jTextFieldPaid;
    private javax.swing.JTextField jTextFieldPrice;
    private javax.swing.JTextField jTextFieldTax;
    private javax.swing.JTextField jTextFieldTotal;
    // End of variables declaration//GEN-END:variables
}
//</editor-fold>