package taxation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FinancialAid implements TaxCalc{

    Double FinancialAid=0.0;

    public FinancialAid(Double Aid){
        FinancialAid=Aid;
    }

    @Override
    public double taxCalculate() {
        return 0;
    }

    @Override
    public double postTaxCalculate() {
        BigDecimal IncomeBenefit = new BigDecimal(FinancialAid).setScale(2, RoundingMode.HALF_UP);
        FinancialAid = IncomeBenefit.doubleValue();
        return FinancialAid;
    }
}
