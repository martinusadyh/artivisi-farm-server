/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package id.web.martinusadyh.iso8583.helper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class ISOUtilTest {

    public ISOUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void findActiveDE() {
        String msg = "0800822000000000000004000000000000000000000000000000001";
        final int panjangBitmap = ISOUtil.findLengthOfBitmap(msg);
        String hexaBitmap = msg.substring(4, 4+panjangBitmap);
        
        // hitung bitmap
        String binaryBitmap = ISOUtil.findBinaryBitmapFromHexa(hexaBitmap);
        String[] activeDE = ISOUtil.findActiveDE(binaryBitmap).split(";");

        for (String string : activeDE) {
            System.out.println("DE [" + string + "]");
        }
    }


}