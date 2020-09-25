/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import static javaapplication5.Suratmasuk.year;
import javax.swing.ImageIcon;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

public class Suratkeluar extends javax.swing.JFrame {

    private DefaultTableModel model;
    private JTable tabel;
    private Statement stt;
    private ResultSet rss;
    private Koneksi konek = new Koneksi();
    private Connection con = konek.KoneksiDB();
    private boolean CariButton = false;
    static public boolean AdvSearch = false;
    private String fileInput;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    AdvanceSearchingSKel a = new AdvanceSearchingSKel();
    
    int No = 0;
    String NoSurat= null;
    String NoSuratX= null;
    String NoSuratX2= null;
    String TujuanSurat = null;
    String TGLSurat= null;    
    String PRIHAL = null;    
    String Keterangan = null;    
    static int year = Calendar.getInstance().get(Calendar.YEAR);
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
    int temp;
    
    
    
    public Suratkeluar() { 
        this.setTitle("Aplikasi Bapenda Surat Masuk & Keluar");
        URL iconURL = getClass().getResource("/javaapplication5/pemkot.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        initComponents();                
        OlahBTN.setEnabled(false);
        SuratKeluarBTN.setEnabled(false);
        NoSuratCol_1TF.setEnabled(false);        
        NoSuratCol_2TF.setEnabled(false);  
        jTable1.setAutoCreateRowSorter(true);
        TGLSuratTF.getDateEditor().setEnabled(false);
        ((JTextFieldDateEditor)TGLSuratTF.getDateEditor()).setDisabledTextColor(Color.black);        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void InitTable()
    {                        
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column)
            {
              return false;//This causes all cells to be not editable
            }
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;                    
                    default:
                        return String.class;
                }
            }
                    
          };
        model.addColumn("No.");
        model.addColumn("Nomor Surat");
        model.addColumn("Tujuan Surat");
        model.addColumn("Tanggal Surat");        
        model.addColumn("Prihal");                
        model.addColumn("Keterangan");
        jTable1.setModel(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
    }
    
    private String ChangeCharacter(String Karakter){
        String ChangedUserString = Karakter.replace("'", "''");
        return ChangedUserString;
    }
    
    //FUNGSI IMPORT FROM EXCEL====================================================//
    
    public void setInputFile(String fileInputX) {
        fileInput = fileInputX;
    }
 
    public void ngeBaca() throws IOException, BiffException  {
        File fileExcel = new File(fileInput);
        Workbook w;
        String kolom;                
        w = Workbook.getWorkbook(fileExcel);
        // Ambil sheet pertama, nomer 0 menandakan sheet ke 1
        Sheet sheet = w.getSheet(0);
        for (int i = 0; i < sheet.getRows(); i++) {                                            
                if(i==0){
//                    System.out.println("Isi tabel "+ cell.getContents());
//                    kolom = cell.getContents();
                }else{                                                            
                        Cell cell = sheet.getCell(0,i);                        
                        this.No =Integer.parseInt(cell.getContents());                                        
                        cell = sheet.getCell(1,i);                        
                        this.NoSurat = cell.getContents();
                        cell = sheet.getCell(2,i);                        
                        this.TujuanSurat = cell.getContents();                                                
                        cell = sheet.getCell(3,i);                                                                      
                        this.TGLSurat = cell.getContents();                   
                        cell = sheet.getCell(4,i);                                                                      
                        this.PRIHAL = cell.getContents();                                          
                        cell = sheet.getCell(5,i);                        
                        this.Keterangan = cell.getContents();                                          
                        try{
                                String sql2 = "INSERT INTO datasuratkeluar VALUES(NULL,'"+this.No+"','"+this.NoSurat+"','"+this.TujuanSurat+"','"+this.TGLSurat+"','"+this.PRIHAL+"','"+this.Keterangan+"')";
                                stt = con.createStatement();
                                stt.executeUpdate(sql2);                                                          
                                InitTable();
                                TampilData();            
                            }catch(SQLException e){
                                System.out.println(e);
                                JOptionPane.showMessageDialog(rootPane, "Nomor Sama");
                            }
                }                           
        }
    }
    
    //=============================================================================//
    
    //FUNGSI EXPORT FILE TO EXCEL==================================================//
    private void exportToExcel(JTable table, File file) {
        try{
            TableModel tableModel = table.getModel();
            try (FileWriter fOut = new FileWriter(file)) {
                for(int i = 0; i < tableModel.getColumnCount(); i++){
                    fOut.write(tableModel.getColumnName(i)+"\t");
                }
                
                fOut.write("\n");
                
                for(int i = 0; i < tableModel.getRowCount(); i++){
                    for(int j = 0; j < tableModel.getColumnCount(); j++){
                        fOut.write(tableModel.getValueAt(i, j).toString()+"\t");
                    }
                    fOut.write("\n");
                }
            }
        } catch (IOException e){
        }
    }
    //==============================================================================//
    
    private void TampilData()
   {
       if(!AdvSearch){
            try{
                String sql = "SELECT * FROM datasuratkeluar ORDER BY no ASC";
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
                while(rss.next()){            
                   Object[] o = new Object[6];
                   o[0] = rss.getInt("NO");
                   o[1] = rss.getString("NOSURAT");               
                   o[2] = rss.getString("TUJUANSURAT");               
                   o[3] = rss.getString("TGLSURAT");              
                   o[4] = rss.getString("PRIHAL");               
                   o[5] = rss.getString("KETERANGAN");               
                   model.addRow(o);
                }            
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
       }else{
           try{
                String sql = "SELECT * FROM datasuratkeluar WHERE "
                        + "tglsurat LIKE '_______"+a.getKomponen()[1].toString().substring(2,4)+"%' "
                        + "AND tglsurat LIKE '%"+a.getKomponen()[0]+"%' "
                        + "ORDER BY no ASC";
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
                while(rss.next()){            
                   Object[] o = new Object[6];
                   o[0] = rss.getInt("NO");
                   o[1] = rss.getString("NOSURAT");               
                   o[2] = rss.getString("TUJUANSURAT");               
                   o[3] = rss.getString("TGLSURAT");              
                   o[4] = rss.getString("PRIHAL");               
                   o[5] = rss.getString("KETERANGAN");               
                   model.addRow(o);
                }            
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
       }
    }
    
    private boolean TambahData(int NO, String NOSURAT, String TUJUANSURAT, String TGLSURAT, String PRIHAL, String KETERANGAN)
    {
        try{
            String sql2 = "INSERT INTO datasuratkeluar VALUES(NULL,"+NO+",'"+NOSURAT+"','"+TUJUANSURAT+"','"+TGLSURAT+"','"+PRIHAL+"','"+KETERANGAN+"')";            
            stt = con.createStatement();
            stt.executeUpdate(sql2);                                                          
            return true;            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Nomor "+NO+" Telah Terdaftar");
            return false;
        }
    }
    
    public boolean HapusData(int NO){
        
        try{
            
            String sql = "DELETE FROM datasuratkeluar WHERE No="+NO+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;            
        }catch(SQLException e){
            System.out.println(e.getMessage());            
            return false;
        }
    }
    
    public boolean UbahData(int NOawal, int NO, String NOSURAT, String TUJUANSURAT, String TGLSURAT, String PRIHAL, String KETERANGAN){        
        try{            
            String sql = "UPDATE datasuratkeluar SET no="+NO+", nosurat='"+NOSURAT+"', tujuansurat='"+TUJUANSURAT+"', tglsurat='"+TGLSURAT+"', prihal='"+PRIHAL+"', Keterangan='"+KETERANGAN+"' Where no='"+NOawal+"';";
            stt = con.createStatement();            
            stt.executeUpdate(sql);
            return true; 
            }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Nomor "+NO+" Telah Terdaftar");
            return false;
            }        
    }
    
    @SuppressWarnings("SuspiciousIndentAfterControlStatement")
    private void PencarianCaret(){
        
                if(CariButton){//  
                    InitTable();
                                       
                    if(TGLSuratTF.getDate() == null)
                    {TGLSurat = "";}
                    else{
//                    TGLSurat = DateFormat.getDateInstance().format(TGLSuratTF.getDate());
                      TGLSurat = ((JTextField)TGLSuratTF.getDateEditor().getUiComponent()).getText();  
                    }                                                                                
                    PencarianData(NoTF.getText(),NoSuratTF.getText(),NoSuratXTF.getText(),NoSuratX2TF.getText(),TujuanSuratTF.getText(),TGLSurat,PerihalTF.getText(),KeteranganTF.getText());
                }
    }                
    
    @SuppressWarnings("SuspiciousIndentAfterControlStatement")
    private void PencarianData(String NO, String NOSURAT, String NOSURATX, String NOSURATX2, String TUJUANSURAT, String TGLSURAT, String PRIHAL, String KETERANGAN)
    {   
        if(AdvSearch){
           try
            {
                if(TGLSURAT == null)
                TGLSURAT = "";                                                

                if(NoTF.getText().length() != 0){                                                
                    this.No = Integer.parseInt(NO);                 
                    String sql = "SELECT * FROM datasuratkeluar WHERE no LIKE '%"+this.No+"%' "
                            + "AND UPPER(nosurat) LIKE UPPER('____"+NOSURAT+"__________%')"
//                            + "AND UPPER(nosurat) LIKE UPPER('%______"+NOSURATX+"________%')"
                            + "AND (UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"_/300.03%') OR UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"/300.03%'))"
                            + "AND UPPER(nosurat) LIKE UPPER('%_______________"+NOSURATX2+"%')"    
                            + "AND UPPER(tujuansurat) LIKE UPPER('%"+TUJUANSURAT+"%')"
                            + "AND UPPER(tglsurat) LIKE UPPER('%"+TGLSURAT+"%')"                        
                            + "AND UPPER(prihal) LIKE UPPER('%"+PRIHAL+"%') "
                            + "AND UPPER(keterangan) LIKE UPPER('%"+KETERANGAN+"%') "
                            + "AND tglsurat LIKE '_______"+a.getKomponen()[1].toString().substring(2,4)+"%' "
                            + "AND tglsurat LIKE '%"+a.getKomponen()[0]+"%' "
                            + "ORDER BY no ASC";                
                    stt = con.createStatement();
                    rss = stt.executeQuery(sql);
                    while(rss.next())
                    {
                        Object[] o = new Object[6];
                        o[0] = rss.getString("NO");
                        o[1] = rss.getString("NOSURAT");                    
                        o[2] = rss.getString("TUJUANSURAT");                    
                        o[3] = rss.getString("TGLSURAT");                    
                        o[4] = rss.getString("PRIHAL");               
                        o[5] = rss.getString("KETERANGAN");               
                        model.addRow(o);
                    }
                }
                else{
                        String sql = "SELECT * FROM datasuratkeluar WHERE "
                            + "UPPER(nosurat) LIKE UPPER('____"+NOSURAT+"__________%')"
//                            + "AND UPPER(nosurat) LIKE UPPER('%______"+NOSURATX+"________%')"
                            + "AND (UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"_/300.03%') OR UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"/300.03%'))"
                            + "AND UPPER(nosurat) LIKE UPPER('%_______________"+NOSURATX2+"%')"    
                            + "AND UPPER(tujuansurat) LIKE UPPER('%"+TUJUANSURAT+"%')"    
                            + "AND UPPER(tglsurat) LIKE UPPER('%"+TGLSURAT+"%')"                        
                            + "AND UPPER(prihal) LIKE UPPER('%"+PRIHAL+"%') "
                            + "AND UPPER(keterangan) LIKE UPPER('%"+KETERANGAN+"%') "
                            + "AND tglsurat LIKE '_______"+a.getKomponen()[1].toString().substring(2,4)+"%' "
                            + "AND tglsurat LIKE '%"+a.getKomponen()[0]+"%' " 
                            + "ORDER BY no ASC";
                        stt = con.createStatement();
                        rss = stt.executeQuery(sql);
                        while(rss.next())
                        {
                            Object[] o = new Object[6];
                            o[0] = rss.getString("NO");
                            o[1] = rss.getString("NOSURAT");                        
                            o[2] = rss.getString("TUJUANSURAT");                        
                            o[3] = rss.getString("TGLSURAT");                        
                            o[4] = rss.getString("PRIHAL");               
                            o[5] = rss.getString("KETERANGAN"); 
                            model.addRow(o);                        
                        }
                   }      
            }
            catch(NumberFormatException | SQLException e)
            {
                System.out.println(e.getMessage());
            } 
        }
        
        else{
            try
            {
                if(TGLSURAT == null)
                TGLSURAT = "";                                                

                if(NoTF.getText().length() != 0){                                                
                    this.No = Integer.parseInt(NO);                 
                    String sql = "SELECT * FROM datasuratkeluar WHERE no LIKE '%"+this.No+"%' "
                            + "AND UPPER(nosurat) LIKE UPPER('____"+NOSURAT+"__________%')"
//                            + "AND UPPER(nosurat) LIKE UPPER('%______"+NOSURATX+"________%')"
                            + "AND (UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"_/300.03%') OR UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"/300.03%'))"
                            + "AND UPPER(nosurat) LIKE UPPER('%_______________"+NOSURATX2+"%')"    
                            + "AND UPPER(tujuansurat) LIKE UPPER('%"+TUJUANSURAT+"%')"
                            + "AND UPPER(tglsurat) LIKE UPPER('%"+TGLSURAT+"%')"                        
                            + "AND UPPER(prihal) LIKE UPPER('%"+PRIHAL+"%') "
                            + "AND UPPER(keterangan) LIKE UPPER('%"+KETERANGAN+"%') "
                            + "ORDER BY no ASC";                
                    stt = con.createStatement();
                    rss = stt.executeQuery(sql);
                    while(rss.next())
                    {
                        Object[] o = new Object[6];
                        o[0] = rss.getString("NO");
                        o[1] = rss.getString("NOSURAT");                    
                        o[2] = rss.getString("TUJUANSURAT");                    
                        o[3] = rss.getString("TGLSURAT");                    
                        o[4] = rss.getString("PRIHAL");               
                        o[5] = rss.getString("KETERANGAN");               
                        model.addRow(o);
                    }
                }
                else{
                        String sql = "SELECT * FROM datasuratkeluar WHERE "
                            + "UPPER(nosurat) LIKE UPPER('____"+NOSURAT+"__________%')"
//                            + "AND UPPER(nosurat) LIKE UPPER('%______"+NOSURATX+"________%')"
                            + "AND (UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"_/300.03%') OR UPPER(nosurat) LIKE UPPER('%"+NOSURATX+"/300.03%'))"    
                            + "AND UPPER(nosurat) LIKE UPPER('%_______________"+NOSURATX2+"%')"    
                            + "AND UPPER(tujuansurat) LIKE UPPER('%"+TUJUANSURAT+"%')"    
                            + "AND UPPER(tglsurat) LIKE UPPER('%"+TGLSURAT+"%')"                        
                            + "AND UPPER(prihal) LIKE UPPER('%"+PRIHAL+"%') "
                            + "AND UPPER(keterangan) LIKE UPPER('%"+KETERANGAN+"%') "
                            + "ORDER BY no ASC";
                        stt = con.createStatement();
                        rss = stt.executeQuery(sql);
                        while(rss.next())
                        {
                            Object[] o = new Object[6];
                            o[0] = rss.getString("NO");
                            o[1] = rss.getString("NOSURAT");                        
                            o[2] = rss.getString("TUJUANSURAT");                        
                            o[3] = rss.getString("TGLSURAT");                        
                            o[4] = rss.getString("PRIHAL");               
                            o[5] = rss.getString("KETERANGAN"); 
                            model.addRow(o);                        
                        }
                   }      
            }
            catch(NumberFormatException | SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public String ReadChar(String teks){
        String CharFus = "";
        for(int i=0;i<teks.length();i++){
            if(teks.charAt(i)=='/'){
                for(int j=i+1;j<teks.length();j++){
                  if(teks.charAt(j)!='/')
                    CharFus =  CharFus+Character.toString(teks.charAt(j));
                  else
                      break;                  
                }
                break;
            }
        }
        return CharFus;
    }
    
    public String ReadChar2(String teks){
        String CharFus = "";        
        for(int i=0;i<teks.length();i++){            
            if(teks.charAt(i)=='/'){
                for(int j=i+1;j<teks.length();j++){                                        
                  if(teks.charAt(j)=='/'){                      
                      for(int k=j+1;k<teks.length();k++){                                                                           
                       if(teks.charAt(k)!='/')   
                       CharFus =  CharFus+Character.toString(teks.charAt(k));   
                       else
                       return CharFus;
                      }                    
                  }                  
                }                
            }
        }        
        return CharFus;
    }
    
    public String ReadChar3(String teks){
        String CharFus = "";        
        for(int i=0;i<teks.length();i++){            
            if(teks.charAt(i)=='/'){
                for(int j=i+1;j<teks.length();j++){                                        
                  if(teks.charAt(j)=='/'){                      
                      for(int k=j+1;k<teks.length();k++){                                                                           
                        if(teks.charAt(k)=='/'){
                            for(int l=k+1;l<teks.length();l++){
                                if(teks.charAt(l)=='/'){
                                    for(int m=l+1;m<teks.length();m++){
                                        if(teks.charAt(m)!='/')
                                            CharFus =  CharFus+Character.toString(teks.charAt(m));   
                                            else
                                            return CharFus;                                            
                                    }
                                }
                            }                             
                        }                          
                      }                    
                  }                  
                }                
            }
        }        
        return CharFus;
    }
    
    public void Reset(){
        NoTF.setText("");
        NoSuratTF.setText("");        
        NoSuratXTF.setText(""); 
        NoSuratX2TF.setText(""); 
        TujuanSuratTF.setText("");
        TGLSuratTF.setCalendar(null);        
        PerihalTF.setText("");
        KeteranganTF.setText("");
    }
    
    public void Refresh(){
        InitTable();
        TampilData(); 
        if(!AdvSearch){
            jLabel9.setText("Pencarian Lanjutan Mati");
            jLabel9.setBackground(new java.awt.Color(255, 102, 102));
        }else{
            jLabel9.setText("Pencarian Lanjutan Aktif");
            jLabel9.setBackground(new java.awt.Color(153, 255, 255));
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SuratMasukBTN = new javax.swing.JButton();
        SuratKeluarBTN = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        NoTF = new javax.swing.JTextField();
        InputBTN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        EditBTN = new javax.swing.JButton();
        HapusBTN = new javax.swing.JButton();
        TGLSuratTF = new com.toedter.calendar.JDateChooser();
        ResetTF = new javax.swing.JButton();
        CariBTN = new javax.swing.JButton();
        OlahBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        PerihalTF = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        KeteranganTF = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        TujuanSuratTF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        NoSuratX2TF = new javax.swing.JTextField();
        NoSuratXTF = new javax.swing.JTextField();
        NoSuratTF = new javax.swing.JTextField();
        NoSuratCol_1TF = new javax.swing.JTextField();
        GenerateNumberBTN = new javax.swing.JButton();
        CariBTN1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        NoSuratCol_2TF = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        ImportBTN = new javax.swing.JButton();
        ExportBTN = new javax.swing.JButton();
        RefreshTF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        SuratMasukBTN.setText("Surat Masuk");
        SuratMasukBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuratMasukBTNActionPerformed(evt);
            }
        });

        SuratKeluarBTN.setText("Surat Keluar");
        SuratKeluarBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuratKeluarBTNActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SURAT KELUAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SuratMasukBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SuratKeluarBTN)
                .addGap(424, 424, 424)
                .addComponent(jLabel3)
                .addContainerGap(687, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SuratMasukBTN)
                    .addComponent(SuratKeluarBTN)
                    .addComponent(jLabel3))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        NoTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NoTFCaretUpdate(evt);
            }
        });

        InputBTN.setText("Input");
        InputBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputBTNActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("No.");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("No Surat");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tanggal Surat");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Perihal");

        EditBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication5/edit.png"))); // NOI18N
        EditBTN.setText("Edit");
        EditBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBTNActionPerformed(evt);
            }
        });

        HapusBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication5/delete.png"))); // NOI18N
        HapusBTN.setText("Hapus");
        HapusBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusBTNActionPerformed(evt);
            }
        });

        TGLSuratTF.setDateFormatString("dd MMM YY");
        TGLSuratTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TGLSuratTFPropertyChange(evt);
            }
        });

        ResetTF.setText("↩");
        ResetTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetTFActionPerformed(evt);
            }
        });

        CariBTN.setText("Cari Data");
        CariBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CariBTNActionPerformed(evt);
            }
        });

        OlahBTN.setText("Olah Data");
        OlahBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OlahBTNActionPerformed(evt);
            }
        });

        PerihalTF.setColumns(20);
        PerihalTF.setRows(5);
        PerihalTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                PerihalTFCaretUpdate(evt);
            }
        });
        jScrollPane2.setViewportView(PerihalTF);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        KeteranganTF.setColumns(20);
        KeteranganTF.setRows(5);
        KeteranganTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                KeteranganTFCaretUpdate(evt);
            }
        });
        jScrollPane3.setViewportView(KeteranganTF);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Keterangan");

        TujuanSuratTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TujuanSuratTFCaretUpdate(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tujuan Surat");

        NoSuratX2TF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NoSuratX2TFCaretUpdate(evt);
            }
        });

        NoSuratXTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NoSuratXTFCaretUpdate(evt);
            }
        });

        NoSuratTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NoSuratTFCaretUpdate(evt);
            }
        });

        NoSuratCol_1TF.setText("973");

        GenerateNumberBTN.setText("#");
        GenerateNumberBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateNumberBTNActionPerformed(evt);
            }
        });

        CariBTN1.setText("Pencarian Lanjut");
        CariBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CariBTN1ActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 102, 102));
        jLabel9.setText("Pencarian Lanjutan Mati");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        NoSuratCol_2TF.setText("300.03");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(NoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NoSuratCol_1TF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(NoSuratTF, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(NoSuratXTF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(NoSuratX2TF, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TujuanSuratTF, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(TGLSuratTF, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(InputBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EditBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HapusBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(GenerateNumberBTN)
                                .addGap(119, 119, 119)
                                .addComponent(NoSuratCol_2TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(OlahBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CariBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CariBTN1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ResetTF))))
                    .addComponent(jLabel6))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel6)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(NoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(NoSuratX2TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(NoSuratXTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(NoSuratTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TujuanSuratTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TGLSuratTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ResetTF))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(OlahBTN)
                                            .addComponent(GenerateNumberBTN)
                                            .addComponent(jLabel9)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(NoSuratCol_1TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(NoSuratCol_2TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                    .addComponent(InputBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(EditBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(HapusBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CariBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CariBTN1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel9.setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        ImportBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication5/excel.png"))); // NOI18N
        ImportBTN.setText("  Import From Excel");
        ImportBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportBTNActionPerformed(evt);
            }
        });

        ExportBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication5/excel.png"))); // NOI18N
        ExportBTN.setText("  Export To Excel");
        ExportBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportBTNActionPerformed(evt);
            }
        });

        RefreshTF.setText("Refresh ");
        RefreshTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshTFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RefreshTF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ImportBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ExportBTN)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ImportBTN)
                    .addComponent(ExportBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RefreshTF))
                .addContainerGap())
        );

        jTable1.setBackground(new java.awt.Color(153, 153, 153));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Nomor Surat", "Sifat", "Tanggal Surat", "Perincian"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1420, 503));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        InitTable();
        TampilData();           
    }//GEN-LAST:event_formComponentShown

@SuppressWarnings("SuspiciousIndentAfterControlStatement")
    private void InputBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputBTNActionPerformed
        No = -1;
        if(NoTF.getText().length() == 0)
        JOptionPane.showMessageDialog(rootPane, "Nomor tidak bisa Kosong");
        else               
        No = Integer.parseInt(NoTF.getText());
        
        NoSuratX        = NoSuratXTF.getText();
        if(NoSuratXTF.getText().length() == 0)
        NoSuratX = "  ";
        
        NoSuratX2        = NoSuratX2TF.getText();
        if(NoSuratX2TF.getText().length() == 0)
        NoSuratX2 = "  ";
        
        NoSurat        = "973/"+NoSuratTF.getText()+"/"+NoSuratX+"/300.03/"+NoSuratX2;
        if(NoSuratTF.getText().length() == 0)
        NoSurat = "973/     /"+NoSuratX+"/300.03/"+NoSuratX2;                   
        
        TujuanSurat        = TujuanSuratTF.getText();
        if(TujuanSuratTF.getText().length() == 0)
        TujuanSurat = " ";
                
        if(TGLSuratTF.getDate() == null)
            TGLSurat = " ";
        else
          TGLSurat = ((JTextField)TGLSuratTF.getDateEditor().getUiComponent()).getText();
//        TGLSurat = DateFormat.getDateInstance().format(TGLSuratTF.getDate());               
        
        PRIHAL = PerihalTF.getText();
        if(PerihalTF.getText().length() == 0)
        PRIHAL = " ";                
        
        Keterangan = KeteranganTF.getText();
        if(KeteranganTF.getText().length() == 0)
        Keterangan = " ";                
        
        if(No != -1)
        if(TambahData(No,NoSurat,ChangeCharacter(TujuanSurat),TGLSurat,ChangeCharacter(PRIHAL),ChangeCharacter(Keterangan)))
        {    
            Object[] o = new Object[6];
                            o[0] = No;
                            o[1] = NoSurat;                        
                            o[2] = TujuanSurat;                        
                            o[3] = TGLSurat;                        
                            o[4] = PRIHAL;               
                            o[5] = Keterangan; 
                            model.addRow(o); 
        }                  
    }//GEN-LAST:event_InputBTNActionPerformed

@SuppressWarnings("SuspiciousIndentAfterControlStatement")
    private void EditBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBTNActionPerformed
        int baris = jTable1.getSelectedRow();               
        int NoAwal = Integer.parseInt(jTable1.getValueAt(baris, 0).toString());
        No = -1;
        if(NoTF.getText().length() == 0)
        JOptionPane.showMessageDialog(rootPane, "Nomor tidak bisa Kosong");
        else               
        No = Integer.parseInt(NoTF.getText());
        
        NoSuratX        = NoSuratXTF.getText();
        if(NoSuratXTF.getText().length() == 0)
        NoSuratX = "  ";
        
        NoSuratX2        = NoSuratX2TF.getText();
        if(NoSuratXTF.getText().length() == 0)
        NoSuratX2 = "  ";
        
        NoSurat        = "973/"+NoSuratTF.getText()+"/"+NoSuratX+"/300.03/"+NoSuratX2;
        if(NoSuratTF.getText().length() == 0)
        NoSurat = "973/     /"+NoSuratX+"/300.03/"+NoSuratX2;
        
        TujuanSurat        = TujuanSuratTF.getText();
        if(TujuanSuratTF.getText().length() == 0)
        TujuanSurat = " ";
                
        if(TGLSuratTF.getDate() == null)
        TGLSurat = " ";
        else
        TGLSurat = ((JTextField)TGLSuratTF.getDateEditor().getUiComponent()).getText();    
//        TGLSurat = DateFormat.getDateInstance().format(TGLSuratTF.getDate());                        
        
        PRIHAL = PerihalTF.getText();
        if(PerihalTF.getText().length() == 0)
        PRIHAL = " ";                
        
        Keterangan = KeteranganTF.getText();
        if(KeteranganTF.getText().length() == 0)
        Keterangan = " ";                
        
        if(No != -1)
        if(UbahData(NoAwal,No,NoSurat,ChangeCharacter(TujuanSurat),TGLSurat,ChangeCharacter(PRIHAL),ChangeCharacter(Keterangan)))
        {
            model.setValueAt(No, baris, 0);
            model.setValueAt(NoSurat, baris, 1);
            model.setValueAt(TujuanSurat, baris, 2);
            model.setValueAt(TGLSurat, baris, 3);
            model.setValueAt(PRIHAL, baris, 4);
            model.setValueAt(Keterangan, baris, 5);
        }
//        InitTable();
//        TampilData();                      
    }//GEN-LAST:event_EditBTNActionPerformed

    private void HapusBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusBTNActionPerformed
        int baris = jTable1.getSelectedRow();
        int[] SelectedRow;                
        SelectedRow = jTable1.getSelectedRows();        
        int ok = JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Mengubah Data?", "Konfirmasi ",JOptionPane.YES_NO_OPTION);                
        if(ok == 0){
            for (int i = SelectedRow.length - 1; i >= 0; i--) {

                this.No = Integer.parseInt(jTable1.getValueAt(SelectedRow[i], 0).toString());                
                if(HapusData(this.No)){
                    model.removeRow(SelectedRow[i]);
                }else
                    JOptionPane.showMessageDialog(null, "Gagal Hapus Data");                                    
            }                        
        }                
    }//GEN-LAST:event_HapusBTNActionPerformed

    private void ImportBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportBTNActionPerformed
    JFileChooser fileopen = new JFileChooser();
    FileFilter filter = new FileNameExtensionFilter("c files", "c");
    fileopen.addChoosableFileFilter(filter);

    int ret = fileopen.showDialog(null, "Open file");

    if (ret == JFileChooser.APPROVE_OPTION) {
      File file = fileopen.getSelectedFile();      
      JavaApplication5 test = new JavaApplication5();
      setInputFile(file.toString());
            try {
                ngeBaca();
            } catch (IOException | BiffException ex) {
                Logger.getLogger(Suratkeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }//GEN-LAST:event_ImportBTNActionPerformed

    private void ExportBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportBTNActionPerformed
        exportToExcel(jTable1, new File("DataTable.xls")); 
        JOptionPane.showMessageDialog(rootPane, "ExportBerhasil");
    }//GEN-LAST:event_ExportBTNActionPerformed

    private void NoTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NoTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NoTFCaretUpdate

    private void TGLSuratTFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TGLSuratTFPropertyChange
        PencarianCaret();
    }//GEN-LAST:event_TGLSuratTFPropertyChange

    private void RefreshTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshTFActionPerformed
        Refresh();      
    }//GEN-LAST:event_RefreshTFActionPerformed

    private void ResetTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetTFActionPerformed
        Reset();
    }//GEN-LAST:event_ResetTFActionPerformed

    private void CariBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CariBTNActionPerformed
        Reset();
        CariButton = true;
        OlahBTN.setEnabled(true);
        CariBTN.setEnabled(false);
        InputBTN.setEnabled(false);
        EditBTN.setEnabled(false);
        GenerateNumberBTN.setEnabled(false);
    }//GEN-LAST:event_CariBTNActionPerformed

    private void OlahBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OlahBTNActionPerformed
        CariButton = false;
        OlahBTN.setEnabled(false);
        CariBTN.setEnabled(true);        
        InputBTN.setEnabled(true);                        
        EditBTN.setEnabled(true);
        GenerateNumberBTN.setEnabled(true);
    }//GEN-LAST:event_OlahBTNActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(CariButton == false){
            int baris = jTable1.getSelectedRow();
            System.out.print(baris);
            NoTF.setText(jTable1.getValueAt(baris, 0).toString());
            NoSuratTF.setText(ReadChar(jTable1.getValueAt(baris, 1).toString()));
            NoSuratXTF.setText(ReadChar2(jTable1.getValueAt(baris, 1).toString()));            
            NoSuratX2TF.setText(ReadChar3(jTable1.getValueAt(baris, 1).toString()));            
            TujuanSuratTF.setText(jTable1.getValueAt(baris, 2).toString());
            PerihalTF.setText(jTable1.getValueAt(baris, 4).toString());
            KeteranganTF.setText(jTable1.getValueAt(baris, 5).toString());
            try {
                java.util.Date date;

                if(jTable1.getValueAt(baris, 3).toString().length() == 1){
                    TGLSuratTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 3).toString());
                    TGLSuratTF.setDate(date);
                }
                                
            } catch (ParseException ex) {
                Logger.getLogger(Suratkeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void SuratKeluarBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuratKeluarBTNActionPerformed
        Suratkeluar k = new Suratkeluar();
        k.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_SuratKeluarBTNActionPerformed

    private void SuratMasukBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuratMasukBTNActionPerformed
        Suratmasuk m = new Suratmasuk();
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_SuratMasukBTNActionPerformed

    private void TujuanSuratTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TujuanSuratTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_TujuanSuratTFCaretUpdate

    private void NoSuratXTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NoSuratXTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NoSuratXTFCaretUpdate

    private void NoSuratTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NoSuratTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NoSuratTFCaretUpdate

    private void GenerateNumberBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateNumberBTNActionPerformed
        try{
            String sql = "SELECT MAX(NO) AS NO FROM datasuratkeluar";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){            
               Object[] o = new Object[1];                
               o[0] = rss.getInt("NO")+1;                
               NoTF.setText(String.valueOf(o[0]));
            }            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_GenerateNumberBTNActionPerformed

    private void CariBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CariBTN1ActionPerformed
        AdvSearch = true;
        a.setVisible(true);
    }//GEN-LAST:event_CariBTN1ActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        Refresh();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void PerihalTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_PerihalTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_PerihalTFCaretUpdate

    private void KeteranganTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_KeteranganTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_KeteranganTFCaretUpdate

    private void NoSuratX2TFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NoSuratX2TFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NoSuratX2TFCaretUpdate

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if(CariButton == false){
            int baris = jTable1.getSelectedRow();
//            System.out.println(baris);
            NoTF.setText(jTable1.getValueAt(baris, 0).toString());
            NoSuratTF.setText(ReadChar(jTable1.getValueAt(baris, 1).toString()));
            NoSuratXTF.setText(ReadChar2(jTable1.getValueAt(baris, 1).toString()));            
            NoSuratX2TF.setText(ReadChar3(jTable1.getValueAt(baris, 1).toString()));            
            TujuanSuratTF.setText(jTable1.getValueAt(baris, 2).toString());
            PerihalTF.setText(jTable1.getValueAt(baris, 4).toString());
            KeteranganTF.setText(jTable1.getValueAt(baris, 5).toString());
            try {
                java.util.Date date;

                if(jTable1.getValueAt(baris, 3).toString().length() == 1){
                    TGLSuratTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 3).toString());
                    TGLSuratTF.setDate(date);
                }
                                
            } catch (ParseException ex) {
                Logger.getLogger(Suratkeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTable1KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Suratkeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Suratkeluar().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CariBTN;
    private javax.swing.JButton CariBTN1;
    private javax.swing.JButton EditBTN;
    private javax.swing.JButton ExportBTN;
    private javax.swing.JButton GenerateNumberBTN;
    private javax.swing.JButton HapusBTN;
    private javax.swing.JButton ImportBTN;
    private javax.swing.JButton InputBTN;
    private javax.swing.JTextArea KeteranganTF;
    private javax.swing.JTextField NoSuratCol_1TF;
    private javax.swing.JTextField NoSuratCol_2TF;
    private javax.swing.JTextField NoSuratTF;
    private javax.swing.JTextField NoSuratX2TF;
    private javax.swing.JTextField NoSuratXTF;
    private javax.swing.JTextField NoTF;
    private javax.swing.JButton OlahBTN;
    private javax.swing.JTextArea PerihalTF;
    private javax.swing.JButton RefreshTF;
    private javax.swing.JButton ResetTF;
    private javax.swing.JButton SuratKeluarBTN;
    private javax.swing.JButton SuratMasukBTN;
    private com.toedter.calendar.JDateChooser TGLSuratTF;
    private javax.swing.JTextField TujuanSuratTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

