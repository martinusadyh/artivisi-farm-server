/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package id.web.martinusadyh.iso8583.helper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class DecimalHexBinaryConverterTest {

    private final String[] decimalNumber = new String[101];
    private final String[] hexaNumber = new String[] {
        "0","1","2","3","4","5",
        "6","7","8","9","A","B",
        "C","D","E","F"
    };
    
    private final String[] binaryNumber = new String[] {
        "0000","0001","0010","0011","0100","0101",
        "0110","0111","1000","1001","1010","1011",
        "1100","1101","1110","1111"
    };

    public DecimalHexBinaryConverterTest() {
        initDecimalNumber();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    private void initDecimalNumber() {
        for (int i=0;i<=100;i++) {
            if (i<10) decimalNumber[i] = "00"+i;
            if (i>=10 && i<100) decimalNumber[i] = "0"+i;
            if (i>=100) decimalNumber[i] = String.valueOf(i);
        }
    }

    /**
     * Test of decimalToHexa method, of class DecimalHexBinaryConverter.
     */
    @Test
    public void testDecimalToHexa() {
        System.out.println("decimalToHexa");
        for (int i=0; i<decimalNumber.length;i++) {
            System.out.println("Decimal ["+decimalNumber[i]+"] "
                    + "Hexa ["+DecimalHexBinaryConverter.decimalToHexa(Integer.valueOf(decimalNumber[i])) + "]");
        }
    }

    /**
     * Test of decimalToBinary method, of class DecimalHexBinaryConverter.
     */
    @Test
    public void testDecimalToBinary() {
        System.out.println("decimalToBinary");
        for (int i=0; i<decimalNumber.length;i++) {
            System.out.println("Decimal ["+decimalNumber[i]+"] "
                    + "Binary ["+DecimalHexBinaryConverter.decimalToBinary(Integer.valueOf(decimalNumber[i])) + "]");
        }
    }

    /**
     * Test of binaryToDecimal method, of class DecimalHexBinaryConverter.
     */
    @Test
    public void testBinaryToDecimal() {
        System.out.println("binaryToDecimal");
        for (int i=0; i<binaryNumber.length;i++) {
            System.out.println("Binary ["+binaryNumber[i]+"] "
                    + "Decimal ["+DecimalHexBinaryConverter.binaryToDecimal(binaryNumber[i]) + "]");
        }
    }

    /**
     * Test of binaryToHexa method, of class DecimalHexBinaryConverter.
     */
    @Test
    public void testBinaryToHexa() {
        System.out.println("binaryToHexa");
        for (int i=0; i<binaryNumber.length;i++) {
            System.out.println("Binary ["+binaryNumber[i]+"] "
                    + "Hexa ["+DecimalHexBinaryConverter.binaryToHexa(binaryNumber[i]) + "]");
        }
    }

    /**
     * Test of hexaToDecimal method, of class DecimalHexBinaryConverter.
     */
    @Test
    public void testHexaToDecimal() {
        System.out.println("hexaToDecimal");
        for (int i=0; i<hexaNumber.length;i++) {
            System.out.println("Hexa ["+hexaNumber[i]+"] "
                    + "Decimal ["+DecimalHexBinaryConverter.hexaToDecimal(hexaNumber[i]) + "]");
        }
    }

    /**
     * Test of hexaToBinary method, of class DecimalHexBinaryConverter.
     */
    @Test
    public void testHexaToBinary() {
        System.out.println("hexaToBinary");
        for (int i=0; i<hexaNumber.length;i++) {
            System.out.println("Hexa ["+hexaNumber[i]+"] "
                    + "Binary ["+DecimalHexBinaryConverter.hexaToBinary(hexaNumber[i]) + "]");
        }
    }
}