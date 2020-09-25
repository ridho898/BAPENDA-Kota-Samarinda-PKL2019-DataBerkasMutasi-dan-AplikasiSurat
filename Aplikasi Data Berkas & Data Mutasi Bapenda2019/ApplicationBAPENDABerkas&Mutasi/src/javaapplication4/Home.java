/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
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
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;

/* CREATED BY RIDHO IRYA Email : Ridho.898@Gmail.com */

public class Home extends javax.swing.JFrame {

    private DefaultTableModel model;
    private JTable tabel;
    private Statement stt;
    private ResultSet rss;
    private Koneksi konek = new Koneksi();
    private Connection con = konek.KoneksiDB();
    private boolean CariButton = false;
    private String fileInput;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    
    int No = 0;
    String NoPelayanan = null;
    String NamaWP= null;
    String Noberkas= null;
    String TGLBerkas= null;
    String TGLTerima = null;
    String TGLCetak= null;
    String Keluarahan= null;    
    String Keterangan= null;    
    String Kecamatan = null;
    String TGLDiserahkan = null;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
    int temp;             
    
    public Home() { 
        URL iconURL = getClass().getResource("/javaapplication4/pemkot.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        initComponents();                
        TanggalberkasTF.setDateFormatString("dd-MMM-yy");
        TanggalterimaTF.setDateFormatString("dd-MMM-yy");
        TanggalcetakTF.setDateFormatString("dd-MMM-yy");
        TanggaldiserahkanTF.setDateFormatString("dd-MMM-yy");        
//        TanggalberkasTF.getDateEditor().setEnabled(false);   
        ((JTextFieldDateEditor)TanggalberkasTF.getDateEditor()).setDisabledTextColor(Color.black);
//        TanggalcetakTF.getDateEditor().setEnabled(false);
        ((JTextFieldDateEditor)TanggalcetakTF.getDateEditor()).setDisabledTextColor(Color.black);
//        TanggalterimaTF.getDateEditor().setEnabled(false);
        ((JTextFieldDateEditor)TanggalterimaTF.getDateEditor()).setDisabledTextColor(Color.black);
//        TanggaldiserahkanTF.getDateEditor().setEnabled(false);        
        ((JTextFieldDateEditor)TanggaldiserahkanTF.getDateEditor()).setDisabledTextColor(Color.black);
        DataBaruBTN.setEnabled(false);                
        setExtendedState(JFrame.MAXIMIZED_BOTH);                
    }
    
    private void InitTable()
    {                
        TanggalberkasTF.setDateFormatString("dd-MMM-yy");
        TanggalterimaTF.setDateFormatString("dd-MMM-yy");
        TanggalcetakTF.setDateFormatString("dd-MMM-yy");                
        TanggaldiserahkanTF.setDateFormatString("dd-MMM-yy");
        Object[] columns = {"NO.", "NO. PELAYANAN","NAMA WP", "NO. BERKAS", "TGL BERKAS", "TGL TERIMA", "TGL CETAK", "KELURAHAN",
        "KECAMATAN", "KETERANGAN", "TGL DISERAHKAN"};
        model = new DefaultTableModel(new Object[0][],columns){
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
        jTable1.setModel(model);      
        jTable1.setAutoCreateRowSorter(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );        
        jTable1.getColumnModel().getColumn(10).setCellRenderer( centerRenderer );
    }
    
    private void ChangeFormat(){
        TanggalberkasTF.setDateFormatString("dd-MMM-yy");
        TanggalterimaTF.setDateFormatString("dd-MMM-yy");
        TanggalcetakTF.setDateFormatString("dd-MMM-yy");    
        TanggaldiserahkanTF.setDateFormatString("dd-MMM-yy");
    }
    
    private String ChangeCharacter(String Karakter){
        String ChangedUserString = Karakter.replace("'", "''");
        return ChangedUserString;
    }
    
    @SuppressWarnings("UnusedAssignment")
    private String ChangeCharacterDot(String Karakter){        
        String ChangedUserString;        
        ChangedUserString = Karakter.replace(".01.", " Jan ");
        ChangedUserString = ChangedUserString.replace(".02.", " Feb ");
        ChangedUserString = ChangedUserString.replace(".03.", " Mar ");
        ChangedUserString = ChangedUserString.replace(".04.", " Apr ");
        ChangedUserString = ChangedUserString.replace(".05.", " Mei ");
        ChangedUserString = ChangedUserString.replace(".06.", " Jun ");
        ChangedUserString = ChangedUserString.replace(".07.", " Jul ");
        ChangedUserString = ChangedUserString.replace(".08.", " Agu ");
        ChangedUserString = ChangedUserString.replace(".09.", " Sep ");
        ChangedUserString = ChangedUserString.replace(".10.", " Okt ");
        ChangedUserString = ChangedUserString.replace(".11.", " Nov ");
        ChangedUserString = ChangedUserString.replace(".12.", " Des ");
        ChangedUserString = ChangedUserString.replace(":01:", " Jan ");
        ChangedUserString = ChangedUserString.replace(":02:", "  Feb ");
        ChangedUserString = ChangedUserString.replace(":03:", "  Mar ");
        ChangedUserString = ChangedUserString.replace(":04:", "  Apr ");
        ChangedUserString = ChangedUserString.replace(":05:", "  Mei ");
        ChangedUserString = ChangedUserString.replace(":06:", "  Jun ");
        ChangedUserString = ChangedUserString.replace(":07:", "  Jul ");
        ChangedUserString = ChangedUserString.replace(":08:", "  Agu ");
        ChangedUserString = ChangedUserString.replace(":09:", "  Sep ");
        ChangedUserString = ChangedUserString.replace(":10:", "  Okt ");
        ChangedUserString = ChangedUserString.replace(":11:", "  Nov ");
        ChangedUserString = ChangedUserString.replace(":12:", "  Des ");
        ChangedUserString = ChangedUserString.replace(" 2018", "  18");
        ChangedUserString = ChangedUserString.replace(" 2019", "  19");
        ChangedUserString = ChangedUserString.replace(" 2020", "  20");
        ChangedUserString = ChangedUserString.replace(" 2021", "  21");
        ChangedUserString = ChangedUserString.replace(" 2022", "  22");
        ChangedUserString = ChangedUserString.replace(" 2023", "  23");
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
        String Tahun = "";
        String Type  = "";
        String WarningNumberSheet = "";
        String WarningSheet = "";
        String WarningNumberViolation = "";
        String WarningArrayIndexOutOfBound = "";
        w = Workbook.getWorkbook(fileExcel);
        // Ambil sheet pertama, nomer 0 menandakan sheet ke 1
        for(int n = 1;n<=w.getNumberOfSheets();n++){
            Sheet sheet = w.getSheet(n-1);        
            for(int i=2017;i<=2023;i++){
                if(w.getSheet(n-1).getName().equalsIgnoreCase("DATA BARU "+i)){
                    Tahun = String.valueOf(i);
                    Type = "databerkas";
                    break;
                }else if(w.getSheet(n-1).getName().equalsIgnoreCase("MUTASI "+i)){
                    Tahun = String.valueOf(i);
                    Type = "datamutasi";
                    break;
                }else if(i==2023){
                    Tahun = "";
                    Type = "";                    
                    JOptionPane.showMessageDialog(this, "Sheet Dengan Nama '"+w.getSheet(n-1).getName()+"' Tidak dapat diterima\n(Sheet dengan nama yang benar = Data Baru 2019, Mutasi 2019)","WARNING",2);
                    break;
                }
            }   
            
            if(Tahun.equalsIgnoreCase("") && Type.equalsIgnoreCase("")){continue;}
            
            for (int i = 0; i < sheet.getRows(); i++){                                           
                    if(i!=0){                                       
                            try{
                            Cell cell = sheet.getCell(0,i);                        
                            this.No =Integer.parseInt(cell.getContents());                                        
                            cell = sheet.getCell(1,i);                        
                            this.NoPelayanan = cell.getContents();                        
                            cell = sheet.getCell(2,i);
                            this.NamaWP = cell.getContents();                        
                            cell = sheet.getCell(3,i);                                              
                            this.Noberkas = cell.getContents();                    
                            cell = sheet.getCell(4,i);                        
                            this.TGLBerkas = cell.getContents();                   
                            cell = sheet.getCell(5,i);                        
                            this.TGLTerima = cell.getContents();                  
                            cell = sheet.getCell(6,i);                        
                            this.TGLCetak = cell.getContents();                   
                            cell = sheet.getCell(7,i);                        
                            this.Keluarahan = cell.getContents();                            
                            cell = sheet.getCell(8,i);                        
                            this.Keterangan = cell.getContents();
                            cell = sheet.getCell(9,i);                        
                            this.Kecamatan = cell.getContents();
                            cell = sheet.getCell(10,i);                        
                            this.TGLDiserahkan = cell.getContents();
                            }catch(ArrayIndexOutOfBoundsException e){
                                WarningArrayIndexOutOfBound = WarningArrayIndexOutOfBound+w.getSheet(n-1).getName()+" ";
                                continue;
                            }
                             catch(NumberFormatException e){
                                WarningNumberViolation = WarningNumberViolation+i+" ";                                
                                continue;
                            }
                            try{
                                    String sql2 = "INSERT INTO "+Type+Tahun+" VALUES(NULL,"+this.No+",'"+this.NamaWP+"','"+this.Noberkas+"','"+ChangeCharacterDot(ChangeCharacter(this.TGLBerkas))+"','"+ChangeCharacterDot(ChangeCharacter(this.TGLTerima))+"','"+ChangeCharacterDot(ChangeCharacter(this.TGLCetak))+"','"+this.Keluarahan+"','"+this.Kecamatan+"','"+this.Keterangan+"','"+ChangeCharacterDot(ChangeCharacter(this.TGLDiserahkan))+"','"+this.NoPelayanan+"')";            
                                    stt = con.createStatement();
                                    stt.executeUpdate(sql2);                                                          
                                    InitTable();
                                    TampilData();            
                                }catch(SQLException e){
    //                                System.out.println(e);                                
//                                    JOptionPane.showMessageDialog(rootPane, "Nomor "+this.No+" Sudah ada");
                                      WarningNumberSheet = WarningNumberSheet+" "+String.valueOf(this.No);
                                      WarningSheet = w.getSheet(n-1).getName();
                                }
                            }
                    }                           
                if(!WarningNumberViolation.equalsIgnoreCase("")){
                    JOptionPane.showMessageDialog(this, "Nomor Pada Baris "+WarningNumberViolation+" Pada Sheet "+w.getSheet(n-1).getName()+" Harus Angka", "KESALAHAN INPUT!!!", 0);
                    WarningNumberViolation="";
                }
                if(!WarningArrayIndexOutOfBound.equalsIgnoreCase("")){
                    JOptionPane.showMessageDialog(this, "Sheet "+WarningArrayIndexOutOfBound+" Tidak diterima\nSheet Harus memiliki 12 kolom yang terdiri dari : \nNo, NamaWP, TglBerkas, TglTerima, TglCetak, Kelurahan,\nNoK, JenisPelayanan, Kecamatan, Keterangan, TGLDiserahkan", "SHEET TIDAK DAPAT DI TERIMA SISTEM!!!", 0);                
                    WarningArrayIndexOutOfBound="";
                }
                if(!WarningNumberSheet.equalsIgnoreCase("")){JOptionPane.showMessageDialog(null, "Nomor"+WarningNumberSheet+" Sudah ada pada Sistem\nPada Sheet : "+WarningSheet+"", "NOMOR TIDAK BOLEH SAMA!!!",2);
                    WarningNumberSheet ="";
                    WarningSheet="";
                }
            } 
        }
    
    //=============================================================================//
    
    //FUNGSI EXPORT FILE TO EXCEL==================================================//
    private boolean exportToExcel(JTable table, File file) {
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
            return true;
        } catch (IOException e){
            return false;
        }
    }
    //==============================================================================//
    
    private void TampilData()
   {
        try{            
            String sql = "SELECT * FROM databerkas"+TahunBerkasCB.getSelectedItem()+" ORDER BY NO ASC";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){            
               Object[] o = new Object[13];
               o[0] = rss.getInt("NO");
               o[1] = rss.getString("NOPELAYANAN");
               o[2] = rss.getString("NAMAWP");               
               o[3] = rss.getString("NOBERKAS");
               o[4] = rss.getString("TGLBERKAS");
               o[5] = rss.getString("TGLTERIMA");
               o[6] = rss.getString("TGLCETAK");               
               o[7] = rss.getString("KELUARAHAN");                                                                             
               o[8] = rss.getString("KECAMATAN");                               
               o[9] = rss.getString("KETERANGAN");                                              
               o[10] = rss.getString("TGLDISERAHKAN");                                              
               model.addRow(o);
            }            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private boolean TambahData(int NO, String NAMAWP, String NOBERKAS, String TGLBERKAS, String TGLTERIMA, String TGLCETAK, String KELUARAHAN, String KECAMATAN, String KETERANGAN, String TGLDISERAHKAN, String NOPELAYANAN)
    {
        try{            
            String sql2 = "INSERT INTO databerkas"+TahunBerkasCB.getSelectedItem()+" VALUES(NULL,"+NO+",'"+NAMAWP+"','"+NOBERKAS+"','"+TGLBERKAS+"','"+TGLTERIMA+"','"+TGLCETAK+"','"+KELUARAHAN+"','','','"+KECAMATAN+"','"+KETERANGAN+"','"+TGLDISERAHKAN+"','"+NOPELAYANAN+"')";            
            stt = con.createStatement();
            stt.executeUpdate(sql2);                                                          
            return true;                         
        }
        catch(SQLException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(rootPane, "Nomor "+NO+" Sudah ada");
            return false;
        }
    }
    
    public boolean HapusData(int NO){        
        try{
            String sql = "DELETE FROM databerkas"+TahunBerkasCB.getSelectedItem()+" WHERE No='"+NO+"';";
            stt = con.createStatement();
            stt.executeUpdate(sql);            
            return true;            
        }catch(SQLException e){
            System.out.println(e.getMessage());            
            return false;
        }
    }
    
    public boolean UbahData(int NOawal, int NO, String NAMAWP, String NOBERKAS, String TGLBERKAS, String TGLTERIMA, String TGLCETAK, String KELUARAHAN, String KECAMATAN, String KETERANGAN, String TGLDISERAHKAN, String NOPELAYANAN){        
        try{            
            String sql = "UPDATE databerkas"+TahunBerkasCB.getSelectedItem()+" SET no="+NO+", namawp='"+NAMAWP+"', noberkas='"+NOBERKAS+"', tglberkas='"+TGLBERKAS+"', tglterima='"+TGLTERIMA+"', tglcetak='"+TGLCETAK+"', keluarahan='"+KELUARAHAN+"', nok='', jenispelayanan='', kecamatan='"+KECAMATAN+"', keterangan='"+KETERANGAN+"', tgldiserahkan='"+TGLDISERAHKAN+"', nopelayanan='"+NOPELAYANAN+"' Where no='"+NOawal+"';";
            stt = con.createStatement();            
            stt.executeUpdate(sql);                                    
            return true; 
            }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Nomor "+NO+" Sudah ada");
            return false;
            }        
    }
    
    @SuppressWarnings("SuspiciousIndentAfterControlStatement")
    private void PencarianCaret(){
                ChangeFormat();
                if(CariButton){//
                    ChangeFormat();
                    InitTable();
                    
                    String TANGGALBERKAS;                    
                    if(TanggalberkasTF.getDate() == null)
                    TANGGALBERKAS = "";
                    else
                      TANGGALBERKAS = ((JTextField)TanggalberkasTF.getDateEditor().getUiComponent()).getText();  
//                    TANGGALBERKAS = DateFormat.getDateInstance().format(TanggalberkasTF.getDate());        

                    String TANGGALTERIMA;
                    if(TanggalterimaTF.getDate() == null)
                    TANGGALTERIMA = "";
                    else
                      TANGGALTERIMA = ((JTextField)TanggalterimaTF.getDateEditor().getUiComponent()).getText();  
//                    TANGGALTERIMA = DateFormat.getDateInstance().format(TanggalterimaTF.getDate());

                    String TANGGALCETAK;
                    if(TanggalcetakTF.getDate() == null)
                    TANGGALCETAK = "";
                    else
                      TANGGALCETAK = ((JTextField)TanggalcetakTF.getDateEditor().getUiComponent()).getText();  
//                    TANGGALCETAK = DateFormat.getDateInstance().format(TanggalcetakTF.getDate());
                    
                    String TANGGALDISERAHKAN;
                    if(TanggaldiserahkanTF.getDate() == null)
                    TANGGALDISERAHKAN = "";
                    else
                      TANGGALDISERAHKAN = ((JTextField)TanggaldiserahkanTF.getDateEditor().getUiComponent()).getText();  
//                    TANGGALDISERAHKAN = DateFormat.getDateInstance().format(TanggaldiserahkanTF.getDate());
                    
                    PencarianData(NoTF.getText(), NamaWPTF.getText(),NoberkasTF.getText(),TANGGALBERKAS,TANGGALTERIMA,TANGGALCETAK,KelurahanTF.getText(),KecamatanTF.getText(),KeteranganTF.getText(),TANGGALDISERAHKAN,NoPelayananTF.getText());
                }
    }                
    
    @SuppressWarnings({"SuspiciousIndentAfterControlStatement", "UnusedAssignment"})
    private void PencarianData(String No, String NamaWP, String Noberkas, String Tglberkas, String Tglterima, String Tglcetak, String Keluarahan, String Kecamatan, String Keterangan, String Tgldiserahkan, String NoPelayanan)
    {        
        try
        {
            if(Tglberkas == null)
            Tglberkas = "";
            if(Tglcetak == null)
            Tglcetak = "";
            if(Tglterima == null)
            Tglterima = "";
            if(Tgldiserahkan == null)
            Tgldiserahkan = "";
                                             
            if(NoTF.getText().length() != 0){               
                @SuppressWarnings("LocalVariableHidesMemberVariable")                    
                int No1 = Integer.parseInt(No);                
                String sql = "SELECT * FROM databerkas"+TahunBerkasCB.getSelectedItem()+" WHERE no LIKE '%"+No1+"%' "
                        + "AND UPPER(nopelayanan) LIKE UPPER('%"+NoPelayanan+"%') "
                        + "AND UPPER(namawp) LIKE UPPER('%"+NamaWP+"%') "
                        + "AND noberkas LIKE '%"+Noberkas+"%' "
                        + "AND UPPER(tglberkas) LIKE UPPER('%"+Tglberkas+"%')"
                        + "AND UPPER(tglcetak) LIKE UPPER('%"+Tglcetak+"%') "
                        + "AND UPPER(tglterima) LIKE UPPER('%"+Tglterima+"%') "
                        + "AND UPPER(Keluarahan) LIKE UPPER('%"+Keluarahan+"%')"
                        + "AND UPPER(Kecamatan) LIKE UPPER('%"+Kecamatan+"%')"
                        + "AND UPPER(Keterangan) LIKE UPPER('%"+Keterangan+"%')"
                        + "AND UPPER(tgldiserahkan) LIKE UPPER('%"+Tgldiserahkan+"%')"
                        + "ORDER BY NO ASC";
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
                while(rss.next())
                {
                    Object[] o = new Object[13];
                    o[0] = rss.getString("NO");
                    o[1] = rss.getString("NOPELAYANAN");
                    o[2] = rss.getString("NAMAWP");
                    o[3] = rss.getString("NOBERKAS");
                    o[4] = rss.getString("TGLBERKAS");
                    o[5] = rss.getString("TGLTERIMA");
                    o[6] = rss.getString("TGLCETAK");               
                    o[7] = rss.getString("KELUARAHAN");                                                                              
                    o[8] = rss.getString("KECAMATAN");                               
                    o[9] = rss.getString("KETERANGAN");                                                   
                    o[10] = rss.getString("TGLDISERAHKAN");                               
                    model.addRow(o);
                }
            }
            else{
                String sql = "SELECT * FROM databerkas"+TahunBerkasCB.getSelectedItem()+" WHERE UPPER(nopelayanan) LIKE UPPER('%"+NoPelayanan+"%') "
                    + "AND UPPER(namawp) LIKE UPPER('%"+NamaWP+"%') "
                    + "AND UPPER(noberkas) LIKE UPPER('%"+Noberkas+"%') "
                    + "AND UPPER(tglberkas) LIKE UPPER('%"+Tglberkas+"%') "
                    + "AND UPPER(tglcetak) LIKE UPPER('%"+Tglcetak+"%') "
                    + "AND UPPER(tglterima) LIKE UPPER('%"+Tglterima+"%') "
                    + "AND UPPER(Keluarahan) LIKE UPPER('%"+Keluarahan+"%')"                                       
                    + "AND UPPER(Kecamatan) LIKE UPPER('%"+Kecamatan+"%')"
                    + "AND UPPER(Keterangan) LIKE UPPER('%"+Keterangan+"%')"
                    + "AND UPPER(tgldiserahkan) LIKE UPPER('%"+Tgldiserahkan+"%')"
                    + "ORDER BY NO ASC";                        
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
            while(rss.next())
            {
                    Object[] o = new Object[13];
                    o[0] = rss.getString("NO");
                    o[1] = rss.getString("NOPELAYANAN");
                    o[2] = rss.getString("NAMAWP");
                    o[3] = rss.getString("NOBERKAS");
                    o[4] = rss.getString("TGLBERKAS");
                    o[5] = rss.getString("TGLTERIMA");
                    o[6] = rss.getString("TGLCETAK");               
                    o[7] = rss.getString("KELUARAHAN");                                                                                                                                  
                    o[8] = rss.getString("KECAMATAN");                               
                    o[9] = rss.getString("KETERANGAN");  
                    o[10] = rss.getString("TGLDISERAHKAN");                               
                    model.addRow(o);
            }
            }                                    
        }
        catch(NumberFormatException | SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }        
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LogoutBTN = new javax.swing.JButton();
        DataBaruBTN = new javax.swing.JButton();
        DataMutasiBTN = new javax.swing.JButton();
        TahunBerkasCB = new javax.swing.JComboBox<>();
        ExportBTN = new javax.swing.JButton();
        ImportBTN = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        NoTF = new javax.swing.JTextField();
        NamaWPTF = new javax.swing.JTextField();
        NoberkasTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        KelurahanTF = new javax.swing.JTextField();
        InputBTN = new javax.swing.JButton();
        EditBTN = new javax.swing.JButton();
        HapusBTN = new javax.swing.JButton();
        CariBTN = new javax.swing.JButton();
        TanggalberkasTF = new com.toedter.calendar.JDateChooser();
        TanggalterimaTF = new com.toedter.calendar.JDateChooser();
        TanggalcetakTF = new com.toedter.calendar.JDateChooser();
        OlahBTN = new javax.swing.JButton();
        ResetTF = new javax.swing.JButton();
        RefreshBTN = new javax.swing.JButton();
        KeteranganTF = new javax.swing.JTextField();
        TanggaldiserahkanTF = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        KecamatanTF = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        NoPelayananTF = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        DataMutasiBTN1 = new javax.swing.JButton();
        GenerateNumberBTN = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication4/pemkot(3).png"))); // NOI18N

        LogoutBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication4/logout.png"))); // NOI18N
        LogoutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBTNActionPerformed(evt);
            }
        });

        DataBaruBTN.setText("Data Baru");

        DataMutasiBTN.setText("Data Mutasi");
        DataMutasiBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataMutasiBTNActionPerformed(evt);
            }
        });

        TahunBerkasCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2022", "2021", "2020", "2019", "2018" }));
        TahunBerkasCB.setSelectedItem("2019");
        TahunBerkasCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TahunBerkasCBItemStateChanged(evt);
            }
        });

        ExportBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication4/excel.png"))); // NOI18N
        ExportBTN.setText("  EXPORT TO EXCEL");
        ExportBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportBTNActionPerformed(evt);
            }
        });

        ImportBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication4/excel.png"))); // NOI18N
        ImportBTN.setText("  IMPORT FROM EXCEL");
        ImportBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportBTNActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Data Baru");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tahun Berkas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addComponent(DataBaruBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DataMutasiBTN)
                .addGap(99, 99, 99)
                .addComponent(jLabel13)
                .addGap(90, 90, 90)
                .addComponent(ImportBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ExportBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(TahunBerkasCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(LogoutBTN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TahunBerkasCB, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(DataBaruBTN)
                                .addComponent(DataMutasiBTN)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ImportBTN)
                                .addComponent(ExportBTN)
                                .addComponent(jLabel15)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));

        NoTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NoTFCaretUpdate(evt);
            }
        });

        NamaWPTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NamaWPTFCaretUpdate(evt);
            }
        });

        NoberkasTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NoberkasTFCaretUpdate(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("No.");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama WP");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("No Berkas");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tanggal Berkas");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tanggal Terima");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tanggal Cetak");

        KelurahanTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                KelurahanTFCaretUpdate(evt);
            }
        });

        InputBTN.setText("INPUT");
        InputBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputBTNActionPerformed(evt);
            }
        });

        EditBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication4/edit.png"))); // NOI18N
        EditBTN.setText("EDIT");
        EditBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBTNActionPerformed(evt);
            }
        });

        HapusBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication4/delete.png"))); // NOI18N
        HapusBTN.setText(" HAPUS");
        HapusBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusBTNActionPerformed(evt);
            }
        });

        CariBTN.setText("CARI DATA");
        CariBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CariBTNActionPerformed(evt);
            }
        });

        TanggalberkasTF.setDateFormatString("dd MMM YY");
        TanggalberkasTF.setMaxSelectableDate(new java.util.Date(253370739675000L));
        TanggalberkasTF.setMinSelectableDate(new java.util.Date(-62135794725000L));
        TanggalberkasTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TanggalberkasTFPropertyChange(evt);
            }
        });

        TanggalterimaTF.setDateFormatString("dd MMM YY");
        TanggalterimaTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TanggalterimaTFPropertyChange(evt);
            }
        });

        TanggalcetakTF.setDateFormatString("dd MMM YY");
        TanggalcetakTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TanggalcetakTFPropertyChange(evt);
            }
        });

        OlahBTN.setText("OLAH DATA");
        OlahBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OlahBTNActionPerformed(evt);
            }
        });

        ResetTF.setText("Reset Form");
        ResetTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetTFActionPerformed(evt);
            }
        });

        RefreshBTN.setText("Refresh");
        RefreshBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBTNActionPerformed(evt);
            }
        });

        KeteranganTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                KeteranganTFCaretUpdate(evt);
            }
        });

        TanggaldiserahkanTF.setDateFormatString("dd MMM YY");
        TanggaldiserahkanTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TanggaldiserahkanTFPropertyChange(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Kecamatan OP");

        KecamatanTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                KecamatanTFCaretUpdate(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tanggal diserahkan");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kelurahan OP");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Keterangan");

        NoPelayananTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                NoPelayananTFCaretUpdate(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("No Pelayanan");

        DataMutasiBTN1.setText("CETAK TABEL");
        DataMutasiBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataMutasiBTN1ActionPerformed(evt);
            }
        });

        GenerateNumberBTN.setText("#");
        GenerateNumberBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateNumberBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GenerateNumberBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(NoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NoPelayananTF, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NamaWPTF, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NoberkasTF)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TanggalberkasTF, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TanggalterimaTF, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TanggalcetakTF, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(KelurahanTF, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(KecamatanTF, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(KeteranganTF, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(TanggaldiserahkanTF, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ResetTF))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(InputBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EditBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HapusBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(327, 327, 327)
                                        .addComponent(DataMutasiBTN1))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(OlahBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CariBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RefreshBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 287, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel16)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GenerateNumberBTN)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NamaWPTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NoberkasTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NoPelayananTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TanggalberkasTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TanggalcetakTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TanggalterimaTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(KelurahanTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(TanggaldiserahkanTF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(KecamatanTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(KeteranganTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ResetTF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InputBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HapusBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OlahBTN)
                    .addComponent(CariBTN)
                    .addComponent(RefreshBTN)
                    .addComponent(DataMutasiBTN1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "STRING", "INTEGER"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(1214, 628));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBTNActionPerformed
        this.dispose();                 
    }//GEN-LAST:event_LogoutBTNActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        InitTable();
        TampilData();
        
        if(CariButton){
        OlahBTN.setEnabled(true);
        CariBTN.setEnabled(false);
        }
        else{
        OlahBTN.setEnabled(false);
        CariBTN.setEnabled(true);
        }
    }//GEN-LAST:event_formComponentShown

    private void ExportBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportBTNActionPerformed
    exportToExcel(jTable1, new File("DataTable.xls")); 
        JOptionPane.showMessageDialog(rootPane, "ExportBerhasil");
    }//GEN-LAST:event_ExportBTNActionPerformed

    private void ImportBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportBTNActionPerformed
    JFileChooser fileopen = new JFileChooser();
    FileFilter filter = new FileNameExtensionFilter("c files", "c");
    fileopen.addChoosableFileFilter(filter);

    int ret = fileopen.showDialog(null, "Open file");

    if (ret == JFileChooser.APPROVE_OPTION) {
      File file = fileopen.getSelectedFile();      
      JavaApplication4 test = new JavaApplication4();
      setInputFile(file.toString());
            try {
                ngeBaca();
            } catch (IOException | BiffException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }//GEN-LAST:event_ImportBTNActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        ChangeFormat();
        if(CariButton == false){              
            int baris = jTable1.getSelectedRow();
            NoTF.setText(jTable1.getValueAt(baris, 0).toString());
            NoPelayananTF.setText(jTable1.getValueAt(baris, 1).toString());
            NamaWPTF.setText(jTable1.getValueAt(baris, 2).toString());
            NoberkasTF.setText(jTable1.getValueAt(baris, 3).toString());
            KelurahanTF.setText(jTable1.getValueAt(baris, 7).toString());
            KecamatanTF.setText(jTable1.getValueAt(baris, 8).toString());
            KeteranganTF.setText(jTable1.getValueAt(baris, 9).toString());            
            
            try {
                java.util.Date date;

                if(jTable1.getValueAt(baris, 4).toString().length() == 1){
                    TanggalberkasTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 4).toString());
                    TanggalberkasTF.setDate(date);
                }

                if(jTable1.getValueAt(baris, 5).toString().length() == 1){
                    TanggalterimaTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 5).toString());
                    TanggalterimaTF.setDate(date);
                }

                if(jTable1.getValueAt(baris, 6).toString().length() == 1){
                    TanggalcetakTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 6).toString());
                    TanggalcetakTF.setDate(date);
                }
                
                if(jTable1.getValueAt(baris, 10).toString().length() == 1){
                    TanggaldiserahkanTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 10).toString());
                    TanggaldiserahkanTF.setDate(date);
                }
            } catch (ParseException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            } catch(Exception e){
                System.out.println("here");
                System.out.println(e);
            }                          
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void RefreshBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBTNActionPerformed
        InitTable();
        TampilData();
    }//GEN-LAST:event_RefreshBTNActionPerformed

    private void ResetTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetTFActionPerformed
        Reset();
    }//GEN-LAST:event_ResetTFActionPerformed

    private void OlahBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OlahBTNActionPerformed
        CariButton = false;
        OlahBTN.setEnabled(false);
        CariBTN.setEnabled(true);
        InputBTN.setEnabled(true);
        GenerateNumberBTN.setEnabled(true);
        EditBTN.setEnabled(true);
    }//GEN-LAST:event_OlahBTNActionPerformed

    private void TanggalcetakTFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TanggalcetakTFPropertyChange
        PencarianCaret();
    }//GEN-LAST:event_TanggalcetakTFPropertyChange

    private void TanggalterimaTFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TanggalterimaTFPropertyChange
        PencarianCaret();
    }//GEN-LAST:event_TanggalterimaTFPropertyChange

    private void TanggalberkasTFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TanggalberkasTFPropertyChange
        PencarianCaret();
    }//GEN-LAST:event_TanggalberkasTFPropertyChange

    private void CariBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CariBTNActionPerformed
        CariButton = true;
        OlahBTN.setEnabled(true);
        CariBTN.setEnabled(false);
        InputBTN.setEnabled(false);
        GenerateNumberBTN.setEnabled(false);
        EditBTN.setEnabled(false);
        Reset();
    }//GEN-LAST:event_CariBTNActionPerformed

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

    @SuppressWarnings({"SuspiciousIndentAfterControlStatement", "UnusedAssignment"})
    private void EditBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBTNActionPerformed
        int baris = jTable1.getSelectedRow();
        try{
                int NoAwal = Integer.parseInt(jTable1.getValueAt(baris, 0).toString());
                ChangeFormat();
                this.No = -1;
                if(NoTF.getText().length() == 0)
                JOptionPane.showMessageDialog(rootPane, "Nomor tidak bisa Kosong");
                else
                try{
                No = Integer.parseInt(NoTF.getText());
                }catch(NumberFormatException e){JOptionPane.showMessageDialog(rootPane, "Kolom No. Harus Angka", "Error Message",0);}

                this.NoPelayanan        = NoPelayananTF.getText();
                if(NoPelayananTF.getText().length() == 0)
                NoPelayanan = " ";

                this.NamaWP        = NamaWPTF.getText();
                if(NamaWPTF.getText().length() == 0)
                NamaWP = " ";

                this.Noberkas         = NoberkasTF.getText();
                if(NoberkasTF.getText().length() == 0)
                Noberkas = " ";

                if(TanggalberkasTF.getDate() == null)
                TGLBerkas = " ";
                else{
                      TGLBerkas = ((JTextField)TanggalberkasTF.getDateEditor().getUiComponent()).getText();  
//                    TGLBerkas = DateFormat.getDateInstance().format(TanggalberkasTF.getDate());
                }

                if(TanggalterimaTF.getDate() == null)
                TGLTerima = " ";
                else
                  TGLTerima = ((JTextField)TanggalterimaTF.getDateEditor().getUiComponent()).getText();  
//                TGLTerima = DateFormat.getDateInstance().format(TanggalterimaTF.getDate());

                if(TanggalcetakTF.getDate() == null)
                TGLCetak = " ";
                else
                  TGLCetak = ((JTextField)TanggalcetakTF.getDateEditor().getUiComponent()).getText();  
//                TGLCetak = DateFormat.getDateInstance().format(TanggalcetakTF.getDate());

                this.Keluarahan = KelurahanTF.getText();
                if(Keluarahan.length() == 0)
                Keluarahan = " ";                               

                this.Keterangan = KeteranganTF.getText();
                if(Keterangan.length() == 0)
                Keterangan = " ";

                this.Kecamatan = KecamatanTF.getText();
                if(Kecamatan.length() == 0)
                Kecamatan = " ";

                if(TanggaldiserahkanTF.getDate() == null)
                TGLDiserahkan = " ";
                else
                  TGLDiserahkan = ((JTextField)TanggaldiserahkanTF.getDateEditor().getUiComponent()).getText();  
//                TGLDiserahkan = DateFormat.getDateInstance().format(TanggaldiserahkanTF.getDate());

                if(No != -1){                        
                    if(UbahData(NoAwal,No,ChangeCharacter(NamaWP),Noberkas,TGLBerkas,TGLTerima,TGLCetak,ChangeCharacter(Keluarahan),ChangeCharacter(Kecamatan),ChangeCharacter(Keterangan),TGLDiserahkan,ChangeCharacter(NoPelayanan))){
                        model.setValueAt(No, baris, 0);
                        model.setValueAt(NoPelayanan, baris, 1);
                        model.setValueAt(NamaWP, baris, 2);
                        model.setValueAt(Noberkas, baris, 3);
                        model.setValueAt(TGLBerkas, baris, 4);
                        model.setValueAt(TGLTerima, baris, 5);
                        model.setValueAt(TGLCetak, baris, 6);
                        model.setValueAt(Keluarahan, baris, 7);
                        model.setValueAt(Kecamatan, baris, 8);
                        model.setValueAt(Keterangan, baris, 9);
                        model.setValueAt(TGLDiserahkan, baris, 10);
                    }
            }
        }catch(NumberFormatException e){JOptionPane.showMessageDialog(rootPane, "Kolom No. Harus Angka", "Error Message",0);}

    }//GEN-LAST:event_EditBTNActionPerformed

    @SuppressWarnings("SuspiciousIndentAfterControlStatement")
    private void InputBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputBTNActionPerformed
        ChangeFormat();
        this.No = -1;
        if(NoTF.getText().length() == 0)
        JOptionPane.showMessageDialog(rootPane, "Nomor tidak bisa Kosong");
        else
        try{
        No = Integer.parseInt(NoTF.getText());
        }catch(NumberFormatException e){JOptionPane.showMessageDialog(rootPane, "Kolom No. Harus Angka", "Error Message",0);}
        
        this.NoPelayanan        = NoPelayananTF.getText();
        if(NoPelayananTF.getText().length() == 0)
        NoPelayanan = " ";
        
        this.NamaWP        = NamaWPTF.getText();
        if(NamaWPTF.getText().length() == 0)
        NamaWP = " ";

        this.Noberkas         = NoberkasTF.getText();
        if(NoberkasTF.getText().length() == 0)
        Noberkas = " ";
        
        if(TanggalberkasTF.getDate() == null)
        TGLBerkas = " ";
        else{
              TGLBerkas = ((JTextField)TanggalberkasTF.getDateEditor().getUiComponent()).getText();  
//            TGLBerkas = DateFormat.getDateInstance().format(TanggalberkasTF.getDate());
        }
        
        if(TanggalterimaTF.getDate() == null)
        TGLTerima = " ";
        else
          TGLTerima = ((JTextField)TanggalterimaTF.getDateEditor().getUiComponent()).getText();  
//        TGLTerima = DateFormat.getDateInstance().format(TanggalterimaTF.getDate());
        
        if(TanggalcetakTF.getDate() == null)
        TGLCetak = " ";
        else
          TGLCetak = ((JTextField)TanggalcetakTF.getDateEditor().getUiComponent()).getText();  
//        TGLCetak = DateFormat.getDateInstance().format(TanggalcetakTF.getDate());

        this.Keluarahan = KelurahanTF.getText();
        if(Keluarahan.length() == 0)
        Keluarahan = " ";                               
        
        this.Keterangan = KeteranganTF.getText();
        if(Keterangan.length() == 0)
        Keterangan = " ";
        
        this.Kecamatan = KecamatanTF.getText();
        if(Kecamatan.length() == 0)
        Kecamatan = " ";
                
        if(TanggaldiserahkanTF.getDate() == null)
        TGLDiserahkan = " ";
        else
          TGLDiserahkan = ((JTextField)TanggaldiserahkanTF.getDateEditor().getUiComponent()).getText();  
//        TGLDiserahkan = DateFormat.getDateInstance().format(TanggaldiserahkanTF.getDate());

        if(No != -1){                        
            if(TambahData(No,ChangeCharacter(NamaWP),Noberkas,TGLBerkas,TGLTerima,TGLCetak,ChangeCharacter(Keluarahan),ChangeCharacter(Kecamatan),ChangeCharacter(Keterangan),TGLDiserahkan,ChangeCharacter(NoPelayanan))){
                Object[] o = new Object[11];
                            o[0] = No;
                            o[1] = NoPelayanan;                        
                            o[2] = NamaWP;                        
                            o[3] = Noberkas;                        
                            o[4] = TGLBerkas;               
                            o[5] = TGLTerima;
                            o[6] = TGLCetak;
                            o[7] = Keluarahan;
                            o[8] = Kecamatan;
                            o[9] = Keterangan;
                            o[10] = TGLDiserahkan;                            
                            model.addRow(o);             
            }
        }        
    }//GEN-LAST:event_InputBTNActionPerformed

    private void KelurahanTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_KelurahanTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_KelurahanTFCaretUpdate

    private void NoberkasTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NoberkasTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NoberkasTFCaretUpdate

    private void NamaWPTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NamaWPTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NamaWPTFCaretUpdate

    private void NoTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NoTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NoTFCaretUpdate

    private void KecamatanTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_KecamatanTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_KecamatanTFCaretUpdate

    private void KeteranganTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_KeteranganTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_KeteranganTFCaretUpdate

    private void TanggaldiserahkanTFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TanggaldiserahkanTFPropertyChange
        PencarianCaret();
    }//GEN-LAST:event_TanggaldiserahkanTFPropertyChange

    private void TahunBerkasCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TahunBerkasCBItemStateChanged
        InitTable();
        TampilData();
    }//GEN-LAST:event_TahunBerkasCBItemStateChanged

    private void NoPelayananTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_NoPelayananTFCaretUpdate
        PencarianCaret();
    }//GEN-LAST:event_NoPelayananTFCaretUpdate

    private void DataMutasiBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataMutasiBTNActionPerformed
        Mutasi a = new Mutasi();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_DataMutasiBTNActionPerformed

    private void DataMutasiBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataMutasiBTN1ActionPerformed
        try {
            MessageFormat Header = new MessageFormat("Data Berkas "+TahunBerkasCB.getSelectedItem());
            MessageFormat footer = new MessageFormat("Page - {0}");
            boolean completed = jTable1.print(JTable.PrintMode.FIT_WIDTH, Header,footer);             
                    jTable1.print(JTable.PrintMode.NORMAL, Header, footer, completed, null, true);    
                    
            
            
        } catch (PrinterException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DataMutasiBTN1ActionPerformed

    private void GenerateNumberBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateNumberBTNActionPerformed
        try{
            String sql = "SELECT MAX(NO) AS NO FROM databerkas"+TahunBerkasCB.getSelectedItem()+" ORDER BY NO ASC";            
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

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        ChangeFormat();
        if(CariButton == false){              
            int baris = jTable1.getSelectedRow();
            NoTF.setText(jTable1.getValueAt(baris, 0).toString());
            NoPelayananTF.setText(jTable1.getValueAt(baris, 1).toString());
            NamaWPTF.setText(jTable1.getValueAt(baris, 2).toString());
            NoberkasTF.setText(jTable1.getValueAt(baris, 3).toString());
            KelurahanTF.setText(jTable1.getValueAt(baris, 7).toString());
            KecamatanTF.setText(jTable1.getValueAt(baris, 8).toString());
            KeteranganTF.setText(jTable1.getValueAt(baris, 9).toString());            
            
            try {
                java.util.Date date;

                if(jTable1.getValueAt(baris, 4).toString().length() == 1){
                    TanggalberkasTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 4).toString());
                    TanggalberkasTF.setDate(date);
                }

                if(jTable1.getValueAt(baris, 5).toString().length() == 1){
                    TanggalterimaTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 5).toString());
                    TanggalterimaTF.setDate(date);
                }

                if(jTable1.getValueAt(baris, 6).toString().length() == 1){
                    TanggalcetakTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 6).toString());
                    TanggalcetakTF.setDate(date);
                }
                
                if(jTable1.getValueAt(baris, 10).toString().length() == 1){
                    TanggaldiserahkanTF.setCalendar(null);
                }else{
                    date = new SimpleDateFormat("dd MMM yy").parse(jTable1.getValueAt(baris, 10).toString());
                    TanggaldiserahkanTF.setDate(date);
                }
            } catch (ParseException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            } catch(Exception e){
                System.out.println(e);
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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CariBTN;
    private javax.swing.JButton DataBaruBTN;
    private javax.swing.JButton DataMutasiBTN;
    private javax.swing.JButton DataMutasiBTN1;
    private javax.swing.JButton EditBTN;
    private javax.swing.JButton ExportBTN;
    private javax.swing.JButton GenerateNumberBTN;
    private javax.swing.JButton HapusBTN;
    private javax.swing.JButton ImportBTN;
    private javax.swing.JButton InputBTN;
    private javax.swing.JTextField KecamatanTF;
    private javax.swing.JTextField KelurahanTF;
    private javax.swing.JTextField KeteranganTF;
    private javax.swing.JButton LogoutBTN;
    private javax.swing.JTextField NamaWPTF;
    private javax.swing.JTextField NoPelayananTF;
    private javax.swing.JTextField NoTF;
    private javax.swing.JTextField NoberkasTF;
    private javax.swing.JButton OlahBTN;
    private javax.swing.JButton RefreshBTN;
    private javax.swing.JButton ResetTF;
    private javax.swing.JComboBox<String> TahunBerkasCB;
    private com.toedter.calendar.JDateChooser TanggalberkasTF;
    private com.toedter.calendar.JDateChooser TanggalcetakTF;
    private com.toedter.calendar.JDateChooser TanggaldiserahkanTF;
    private com.toedter.calendar.JDateChooser TanggalterimaTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    
    public void Reset(){
        NoTF.setText("");
        NoPelayananTF.setText("");
        NamaWPTF.setText("");
        NoberkasTF.setText("");        
        TanggalberkasTF.setCalendar(null);
        TanggalterimaTF.setCalendar(null);
        TanggalcetakTF.setCalendar(null);
        KelurahanTF.setText("");        
        KeteranganTF.setText("");
        KecamatanTF.setText("");
        TanggaldiserahkanTF.setCalendar(null);
    }

}

