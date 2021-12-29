package taxation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyTransfer implements TaxCalc{
    Double Transfer;

    Double TaxRate = 19.5;

    Double Tax;

    public MoneyTransfer(Double inputTransfer){
        Transfer = inputTransfer;
    }

    @Override
    public double taxCalculate() {
        BigDecimal TaxBD = new BigDecimal(Transfer*(TaxRate/100)).setScale(2, RoundingMode.HALF_UP);
        Tax = TaxBD.doubleValue();
        return Tax;
    }

    @Override
    public double postTaxCalculate() {
        return Transfer - Tax;
    }
}
