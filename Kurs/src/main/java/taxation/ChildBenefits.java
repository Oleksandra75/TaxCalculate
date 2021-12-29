package taxation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChildBenefits implements TaxCalc{

    Integer KidsAmount = 0;
    Double BenefitPrice = 3350.0;
    Double IncomeBenefitLevel = 0.0;

    public void changeKidsAmount(Integer amt){
        KidsAmount=amt;
    }

    public ChildBenefits(Integer amt) {
        KidsAmount=amt;
    }

    @Override
    public double taxCalculate() {
        BigDecimal IncomeBenefit = new BigDecimal(BenefitPrice*KidsAmount).setScale(2, RoundingMode.HALF_UP);
        IncomeBenefitLevel = IncomeBenefit.doubleValue();
        return -IncomeBenefitLevel;
    }

    @Override
    public double postTaxCalculate() {
        Double j=taxCalculate();
        return +IncomeBenefitLevel;
    }
}
