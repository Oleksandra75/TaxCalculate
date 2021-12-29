package taxation;

import javafx.beans.property.Property;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PropertyTax implements TaxCalc {

    Double PropertyTax = 0.0;
    Double TaxRate = 1.5;
    Double Metrage = 0.0;
    Integer PropertyType = 1;

    public void inputMetrage(Double inMetrg){
        Metrage = inMetrg;
    }

    public void changePropertyType(Integer chgType){
        PropertyType = 1;
    }

    public PropertyTax(double Metrage, Integer PropertyType){
        inputMetrage(Metrage);
        changePropertyType(PropertyType);
    }

    @Override
    public double taxCalculate() {
        Double OverMetrage = 0.0;
        switch (PropertyType) {
            case 1:
                OverMetrage = Metrage - 60;
                if (OverMetrage <=0){
                    OverMetrage = 0.0;
                }
                break;
            case 2:
                OverMetrage = Metrage - 120;
                if (OverMetrage <=0){
                    OverMetrage = 0.0;
                }
                break;
            default:
                OverMetrage = Metrage - 180;
                if (OverMetrage <=0){
                    OverMetrage = 0.0;
                }
                break;
        }
        BigDecimal TaxBD = new BigDecimal(OverMetrage*(TaxRate/100)*60).setScale(2, RoundingMode.HALF_UP);// взято за основу власності у Львіській ТГ; оподаткування іде з метра квадратного кожної ділянки більшо
        PropertyTax = TaxBD.doubleValue();
        return PropertyTax;
    }

    public double taxCalculate(Double Metrage, Integer PropertyType) {
        Double OverMetrage = 0.0;
        switch (PropertyType) {
            case 0:
                OverMetrage = Metrage - 60;
                if (OverMetrage <=0){
                    OverMetrage = 0.0;
                }
                break;
            case 1:
                OverMetrage = Metrage - 120;
                if (OverMetrage <=0){
                    OverMetrage = 0.0;
                }
                break;
            case 2:
                OverMetrage = Metrage - 180;
                if (OverMetrage <=0){
                    OverMetrage = 0.0;
                }
                break;
        }
        BigDecimal TaxBD = new BigDecimal(OverMetrage*(TaxRate/100)).setScale(2, RoundingMode.HALF_UP);// взято за основу власності у Львіській ТГ; оподаткування іде з метра квадратного кожної ділянки більшо
        PropertyTax = TaxBD.doubleValue();
        return PropertyTax;
    }

    @Override
    public double postTaxCalculate() {
        return -PropertyTax;
    }
}
