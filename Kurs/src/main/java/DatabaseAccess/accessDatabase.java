package DatabaseAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class accessDatabase {
    String db_uri = "jdbc:mysql://127.0.0.1:3306/taxation";
    String db_user = "root";
    String db_pwd = "L1tig0VsF0X$ue$L4dL3";

    String workingTableName="taxation";
    Connection dbConnection;

    DefaultTableModel dm = new DefaultTableModel();

    public accessDatabase(){
        try {
            dbConnection = DriverManager.getConnection(db_uri, db_user, db_pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean changeTable(String changeToTable){
        if (changeToTable!=""){
            workingTableName=changeToTable;
            return true;
        }
        return false;
    }

    public Boolean createTable(String workingTableName){
        String sql="";
        if (workingTableName!=""){
            sql = "CREATE TABLE " + workingTableName + " (StatementID INTEGER not NULL,Taxtype VARCHAR(32),TaxPercent DOUBLE,Tax DOUBLE,PersonalIncome DOUBLE,DisposableIncome DOUBLE) ";
        }
        else{
            return false;
        }
        try {
            // PREPARED STMT
            Statement s = dbConnection.prepareStatement(sql);

            s.execute(sql);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }
    //INSERT INTO DB
    public Boolean addToTable(String statID,String taxType,String taxPerc,String taxReal,String persInc,String dispInc) {
        dm.addRow( new Object[] {statID,taxType,taxPerc,taxReal,persInc,dispInc});
        String sql = "";
        if (workingTableName!=""){
            //SQL STATEMENT
            sql = "INSERT INTO " + workingTableName + "(StatementID,Taxtype,TaxPercent,Tax,PersonalIncome,DisposableIncome) VALUES('" + statID+"','"+taxType + "','" + taxPerc +"','" + taxReal +"','" + persInc+ "','" + dispInc + "')";
        }
        else{
            return false;
        }
        try {

            // PREPARED STMT
            Statement s = dbConnection.prepareStatement(sql);

            s.execute(sql);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public void Remove(JTable table, int col_index){
        TableColumn tcol = table.getColumnModel().getColumn(col_index);
        table.removeColumn(tcol);
    }
    public void addRow2(JTable table, int col_index){
        TableColumn tcol = table.getColumnModel().getColumn(col_index);
        table.removeColumn(tcol);
    }

    //RETRIEVE DATA
    public DefaultTableModel getTableData() {
        //ADD COLUMNS TO TABLE MODEL

        dm.addColumn("StatementID");
        dm.addColumn("Taxtype");
        dm.addColumn("TaxPercent");
        dm.addColumn("Tax");
        dm.addColumn("PersonalIncome");
        dm.addColumn("DisposableIncome");
        dm.addRow( new Object[] {});
        //SQL STATEMENT
        String sql = "SELECT * FROM "+workingTableName +"";

        try {

            //PREPARED STMT
            Statement s = dbConnection.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);

            //LOOP THRU GETTING ALL VALUES
            while (rs.next()) {
                //GET VALUES
                String statID = rs.getString(1);
                String taxType = rs.getString(2);
                String taxPerc = rs.getString(3);
                String taxReal = rs.getString(4);
                String persInc = rs.getString(5);
                String dispInc = rs.getString(6);


                dm.addRow(new String[]{statID, taxType, taxPerc, taxReal, persInc, dispInc});
            }

            return dm;

        } catch (Exception ex) {
            try{
                createTable(workingTableName);

                //PREPARED STMT
                Statement s = dbConnection.prepareStatement(sql);
                ResultSet rs = s.executeQuery(sql);

                //LOOP THRU GETTING ALL VALUES
                while (rs.next()) {
                    //GET VALUES
                    String statID = rs.getString(1);
                    String taxType = rs.getString(2);
                    String taxPerc = rs.getString(3);
                    String taxReal = rs.getString(4);
                    String persInc = rs.getString(5);
                    String dispInc = rs.getString(6);


                    dm.addRow(new String[]{statID, taxType, taxPerc, taxReal, persInc, dispInc});
                }

                return dm;

            }catch (Exception exc) {
                exc.printStackTrace();
            }
        }

        return null;

    }

    //UPDATE DATA
    public Boolean update(String statID,String taxType,String taxPerc,String taxReal,String persInc,String dispInc) {
        //SQL STMT
        String sql = "UPDATE " + workingTableName + " SET Taxtype='" + taxType + "',TaxPercent='" + taxPerc + "',Tax='" + taxReal+"',PersonalIncome='"+ persInc + "',DisposableIncome='"+ dispInc +"' WHERE StatementID ='"+statID+"'";

        try {
            //GET COONECTION

            //STATEMENT
            Statement s = dbConnection.prepareStatement(sql);

            //EXECUTE
            s.execute(sql);

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //DELETE DATA
    public Boolean delete(String id)
    {
        //SQL STMT
        String sql="DELETE FROM " + workingTableName +" WHERE StatementID ='"+id+"'";

        try
        {

            //STATEMENT
            Statement s=dbConnection.prepareStatement(sql);

            //EXECUTE
            s.execute(sql);

            return true;

        }catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

}
