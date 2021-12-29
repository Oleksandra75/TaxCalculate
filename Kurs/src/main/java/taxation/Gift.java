package taxation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Gift implements TaxCalc{

    Double GiftPrice = 0.0;
    Double GiftTax = 0.0;
    Integer Affinity = 0;

    Double MinimalGiftPrice = 1625.0;

    public Gift(Double gftPrc, Integer affN){
        GiftPrice=gftPrc;
        Affinity=affN;
    }

    public void inputGiftProperties (Double gftPrc, Integer affN){
        GiftPrice=gftPrc;
        Affinity=affN;
    }

    @Override
    public double taxCalculate() {
        if (GiftPrice> MinimalGiftPrice) {
            switch (Affinity) {
                case 1:
                    GiftTax = 0.0;
                    break;
                case 2:
                    GiftTax = (GiftPrice-MinimalGiftPrice)*0.05;
                    break;
                case 3:
                    GiftTax = (GiftPrice-MinimalGiftPrice)*0.195;
                    break;
                default:
                    GiftTax = (GiftPrice-MinimalGiftPrice)*0.195;
                    break;
            }
        }

        BigDecimal IncomeBenefit = new BigDecimal(GiftTax).setScale(2, RoundingMode.HALF_UP);
        GiftTax = IncomeBenefit.doubleValue();
        return GiftTax;
    }

    @Override
    public double postTaxCalculate() {
        return GiftPrice-GiftTax;
    }
}
