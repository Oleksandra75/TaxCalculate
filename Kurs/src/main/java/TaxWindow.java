import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.awt.event.*;

import taxation.*;
import DatabaseAccess.accessDatabase;

public class TaxWindow extends JFrame {
    public JTable accessTable;
    public JTextField taxPercentTextField;
    public JTextField incomeTextField;
    public JTextField taxTextField;
    public JTextField disposableIncomeTextField;
    public JComboBox taxTypeChoose;
    public JButton chooseTableButton;
    public JButton calculateTaxButton;
    public JButton enterIntoTableButton;
    private JButton saveToDatabaseButton;
    private JButton deleteFromTableButton;
    private JTextField tableName;
    private JButton exitProgramButton;
    private JLabel SumLabel;
    private JTextField optionalTextField;
    private JLabel optionalLabel;
    private JLabel incomeLabel;
    public JPanel MainWin;

    private Integer taxTypeIndicator=1;
    private Integer taxNumIdentifier=0;

    accessDatabase DatabaseControl =new accessDatabase();

    private String TaxTypeToString(Integer tTi) {
        //String TaxTypeString;
        switch (tTi) {
            case 1:
                return "заробітна плата";
            case 2:
                return "подарунок";
            case 3:
                return "фінансова допомога";
            case 4:
                return "податкові пільги з дітей";
            case 5:
                return "нерухомість";
            case 6:
                return "переказ з-за кордону";
            default:
                return "unknown";
        }
    }

    public String GetData(JTable table, int row_index, int col_index){
        return String.valueOf(table.getModel().getValueAt(row_index, col_index));
    }

    public TaxWindow() {
        taxTypeChoose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String taxType = (String)taxTypeChoose.getSelectedItem();
                switch (taxType){
                    case "заробітна плата":
                        taxTypeIndicator = 1;
                        taxPercentTextField.setText("18");
                        optionalTextField.setVisible(false);
                        optionalLabel.setVisible(false);
                        optionalLabel.setText(" ");
                        break;
                    case "подарунок":
                        taxTypeIndicator = 2;
                        optionalTextField.setVisible(true);
                        optionalLabel.setVisible(true);
                        optionalLabel.setText("Родичі(ступінь віддалення):");
                        break;
                    case "фінансова допомога":
                        taxTypeIndicator = 3;
                        taxPercentTextField.setText("0");
                        optionalTextField.setVisible(false);
                        optionalLabel.setVisible(false);
                        optionalLabel.setText(" ");
                        break;
                    case "податкові пільги з дітей":
                        taxTypeIndicator = 4;
                        //add choice of kids No
                        optionalTextField.setVisible(true);
                        optionalLabel.setVisible(true);
                        optionalLabel.setText("Кількість дітей:");
                        break;
                    case "нерухомість":
                        taxTypeIndicator = 5;
                        //add choice of number
                        optionalTextField.setVisible(true);
                        optionalLabel.setVisible(true);
                        optionalLabel.setText("Тип об'єкта: 1 квартира 2 дім 3 нежитло");
                        incomeLabel.setText("Площа об'єкта нерухомості:");
                        break;
                    case "переказ з-за кордону":
                        taxTypeIndicator = 6;
                        taxPercentTextField.setText("18");
                        optionalTextField.setVisible(false);
                        optionalLabel.setVisible(false);
                        optionalLabel.setText(" ");
                        break;

                }
            }
        });
        chooseTableButton.addActionListener(e -> {
            DatabaseControl.changeTable(tableName.getText());
            accessTable.setModel(DatabaseControl.getTableData());
            optionalLabel.setVisible(true);
            optionalLabel.setText("WORKS");
            System.out.println("chooseTable click success");
        });
        calculateTaxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (taxTypeIndicator){
                    case 1:
                        taxPercentTextField.setText("19.5");
                        Income CurrIncome=new Income(Double.parseDouble(incomeTextField.getText()));
                        taxTextField.setText(Double.toString(CurrIncome.taxCalculate()));
                        disposableIncomeTextField.setText(Double.toString(CurrIncome.postTaxCalculate()));
                        break;
                    case 2:
                        Double GTax;
                        switch (Integer.parseInt(optionalTextField.getText())) {
                            case 1:
                                GTax = 0.0;
                                break;
                            case 2:
                                GTax = 5.0;
                                break;
                            case 3:
                                GTax = 19.5;
                                break;
                            default:
                                GTax = 19.5;
                                break;
                        }
                        taxPercentTextField.setText(GTax + " ");
                        Gift CurrGift=new Gift(Double.parseDouble(incomeTextField.getText()),Integer.parseInt(optionalTextField.getText()));
                        taxTextField.setText(Double.toString(CurrGift.taxCalculate()));
                        disposableIncomeTextField.setText(Double.toString(CurrGift.postTaxCalculate()));
                        break;
                    case 3:
                        taxPercentTextField.setText("0.0");
                        FinancialAid FinnAid=new FinancialAid(Double.parseDouble(incomeTextField.getText()));
                        taxTextField.setText(Double.toString(FinnAid.taxCalculate()));
                        disposableIncomeTextField.setText(Double.toString(FinnAid.postTaxCalculate()));
                        break;
                    case 4:
                        taxPercentTextField.setText("19.5");
                        ChildBenefits KidBen=new ChildBenefits(Integer.parseInt(optionalTextField.getText()));
                        taxTextField.setText(Double.toString(KidBen.taxCalculate()));
                        disposableIncomeTextField.setText(Double.toString(KidBen.postTaxCalculate()));
                        incomeLabel.setText("");
                        incomeTextField.setText("");
                        break;
                    case 5:
                        taxPercentTextField.setText("");
                        //incomeLabel.setText("Площа житла");
                        //optionalLabel.setText("Тип житла 1 - квартира, 2 - дім, 3 - інше ");
                        PropertyTax propT = new PropertyTax(Double.parseDouble(incomeTextField.getText()),Integer.parseInt(optionalTextField.getText()));
                        taxTextField.setText(Double.toString(propT.taxCalculate()));

                        break;
                    case 6:
                        taxPercentTextField.setText("19.5 ");
                        MoneyTransfer transfer=new MoneyTransfer(Double.parseDouble(incomeTextField.getText()));
                        taxTextField.setText(Double.toString(transfer.taxCalculate()));
                        disposableIncomeTextField.setText(Double.toString(transfer.postTaxCalculate()));
                        break;
                }
                //DatabaseControl.addToTable(Integer.toString(taxNumIdentifier),TaxTypeToString(taxTypeIndicator),taxPercentTextField.getText(),taxTextField.getText(),incomeTextField.getText(),disposableIncomeTextField.getText());

            }
        });
        enterIntoTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel aTM=accessTable.getModel();
                DatabaseControl.addToTable(Integer.toString(taxNumIdentifier),TaxTypeToString(taxTypeIndicator),taxPercentTextField.getText(),taxTextField.getText(),incomeTextField.getText(),disposableIncomeTextField.getText());
                //accessTable.setModel(DatabaseControl.getTableData());
//                accessTable.setValueAt(Integer.toString(taxNumIdentifier),taxNumIdentifier,0);
//                accessTable.setValueAt(TaxTypeToString(taxTypeIndicator),taxNumIdentifier,1);
//                accessTable.setValueAt(taxPercentTextField.getText(),taxNumIdentifier,2);
//                accessTable.setValueAt(taxTextField.getText(),taxNumIdentifier,3);
//                accessTable.setValueAt(incomeTextField.getText(),taxNumIdentifier,4);
//                accessTable.setValueAt(disposableIncomeTextField.getText(),taxNumIdentifier,5);
                taxNumIdentifier++;
                Double summa = 0.0;
                for(int i = 0; i<accessTable.getRowCount();i++){
                    summa= summa + Double.parseDouble(String.valueOf( accessTable.getValueAt(i,5))) ;
                }
//                if (taxNumIdentifier<0){
//                    for(int i = accessTable)
//                }
                SumLabel.setText(Double.toString(summa));
            }
        });
        deleteFromTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseControl.delete(GetData(accessTable,accessTable.getSelectedRow(),1));
                taxNumIdentifier=taxNumIdentifier-1;
                for(int i=accessTable.getSelectedRow(); i<accessTable.getRowCount();i++){
                    accessTable.setValueAt(i,i,1);
                }
            }
        });
        exitProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame Frame = new JFrame("Exit");

                if (JOptionPane.showConfirmDialog(Frame,"confirm if you Want to Exit","Name of the Application or Title",
                        JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
        saveToDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<accessTable.getRowCount();i++){
                    DatabaseControl.update(String.valueOf(i),String.valueOf(accessTable.getValueAt(i,1)),String.valueOf(accessTable.getValueAt(i,2)),String.valueOf(accessTable.getValueAt(i,3)),String.valueOf(accessTable.getValueAt(i,4)),String.valueOf(accessTable.getValueAt(i,5)));
                }
            }
        });
    }


}
