package taxation;


import java.math.RoundingMode;
import java.math.BigDecimal;

public class Income implements TaxCalc{

    Double Income;

    Double TaxRate = 19.5;

    Double Tax;

    public Income(Double inputIncome){
        Income = inputIncome;
    }

    @Override
    public double taxCalculate() {
        BigDecimal TaxBD = new BigDecimal(Income*(TaxRate/100)).setScale(2, RoundingMode.HALF_UP);
        Tax = TaxBD.doubleValue();
        return Tax;
    }

    public double taxCalculate(Double TaxRate){
        BigDecimal TaxBD = new BigDecimal(Income*(TaxRate/100)).setScale(2, RoundingMode.HALF_UP);
        Tax = TaxBD.doubleValue();
        return Tax;
    }


    @Override
    public double postTaxCalculate() {
        return Income - Income*(TaxRate/100);
    }
}
