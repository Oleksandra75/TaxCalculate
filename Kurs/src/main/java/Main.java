import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.*;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
    private static void init(){
        System.out.println("Success init");

    }

    public static void main(String[] args) {

        TaxWindow MainWindow = new TaxWindow();
        MainWindow.setContentPane(MainWindow.MainWin);
        MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow.setVisible(true);
        MainWindow.pack();
        //MainWindow.
        System.out.println("Success main");
    }

}
