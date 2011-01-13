/*
 *  Copyright (c) 2010 Martinus Ady H <martinus@artivisi.com>.
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  o Neither the name of the <ORGANIZATION> nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *  OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  DecimalHexBinaryConverter.java
 *
 *  Created on Sep 14, 2010, 1:59:31 AM
 */
package id.web.martinusadyh.iso8583.helper;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class DecimalHexBinaryConverter {

    public static String decimalToHexa(Integer decimalNumber) {
        return Integer.toHexString(decimalNumber);
    }

    public static String decimalToBinary(Integer decimalNumber) {
        StringBuilder binaryNumber = new StringBuilder();
        StringBuilder sbBinary = new StringBuilder();
        String binaryString = Integer.toBinaryString(decimalNumber);
        char[] binary = binaryString.toCharArray();
        int counter = 0;
        // ambil dari index karakter terakhir
        for (int i=binary.length-1; i>=0; i--) {
            counter++;
            sbBinary.append(binary[i]);
            // reset counter ke nol jika berhasil mengambil 4 digit karakter
            if (counter == 4) counter = 0;
        }

        // 4 adalah panjang karakter tiap blok di binary
        // ex: dec [100] == binary [0110 0100]
        for (int i=0; i<4-counter; i++) {
            if (counter > 0) sbBinary.append("0");
        }

        // sekarang dibalik
        for (int i=sbBinary.length()-1; i>=0;i--) {
            binaryNumber.append(sbBinary.toString().charAt(i));
        }

        return binaryNumber.toString();
    }

    public static Integer binaryToDecimal(String binaryNumber) {
        return Integer.parseInt(binaryNumber, 2);
    }

    public static String binaryToHexa(String binaryNumber) {
        return decimalToHexa(binaryToDecimal(binaryNumber));
    }

    public static Integer hexaToDecimal(String hexaNumber) {
        return Integer.parseInt(hexaNumber, 16);
    }

    public static String hexaToBinary(String hexaNumber) {
        return decimalToBinary(hexaToDecimal(hexaNumber));
    }
}
