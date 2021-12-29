package taxation;

import java.math.RoundingMode;
import java.text.DecimalFormat;


public interface TaxCalc {
    abstract double taxCalculate ();
    double postTaxCalculate ();
}
