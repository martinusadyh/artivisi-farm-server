/*
 *  Copyright (c) 2010 Martinus Ady H <martinus@artivisi.com>
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
 *  ISOUtil.java
 *
 *  Created on Sep 14, 2010, 1:55:31 AM
 */
package id.web.martinusadyh.iso8583.helper;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class ISOUtil {
    
    /** Mencari panjang dari Bitmap 16 karakter atau 32 karakter, jika bit pertama
     * nilai-nya == 1 (active/TRUE) maka secondary bitmap active dan otomatis
     * panjang bitmap menjadi 32 karakter.
     * @param originalMsg message asli ISO beserta dengan MTI-nya
     * @return panjang bitmap yang harus di potong / diambil.
     */
    public static Integer findLengthOfBitmap(String originalMsg) {
        // ambil 1 digit bitmap setelah MTI
        String bitPertama = originalMsg.substring(4,5);
        int panjangBitmap = 0;
        // cek nilai binary-nya
        if (DecimalHexBinaryConverter.hexaToBinary(bitPertama).substring(0, 1).equalsIgnoreCase("1")) {
            panjangBitmap = 32;
        } else {
            panjangBitmap = 16;
        }

        return panjangBitmap;
    }

    public static String getHexaBitmapFromActiveDE(int[] activeDE) {
        StringBuilder finalHexaBitmap = new StringBuilder();
        StringBuilder binaryBitmapForReply = new StringBuilder();

        boolean secondarBitmapActive = false;
        int panjangBitmap = 16;
        // pengecekan secondary bitmap
        for (int i=0; i<activeDE.length;i++) {
            if (activeDE[i] > 64) {
                secondarBitmapActive = true;
                panjangBitmap = 32;
            }
        }

        // x4 untuk mendapatkan jumlah seluruh data elemen
        panjangBitmap *= 4;
        int counterBitmap=0;
        String active = "";
        for (int i=0;i<panjangBitmap; i++) {
            counterBitmap++;
            active = "0";
            for (int j=0; j<activeDE.length; j++) {
                if (counterBitmap == activeDE[j]) active = "1";
            }

            binaryBitmapForReply.append(active);
        }

        // karena secondary bitmap active, bit pertama ganti jadi 1
        if (secondarBitmapActive) {
            binaryBitmapForReply = new StringBuilder("1"+binaryBitmapForReply.toString().substring(1, binaryBitmapForReply.length()));
        }

        char[] binaryBitmapChar = binaryBitmapForReply.toString().toCharArray();
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<binaryBitmapChar.length;i++) {
            sb.append(binaryBitmapChar[i]);
            counter++;

            if (counter == 4) {
                finalHexaBitmap.append(DecimalHexBinaryConverter.binaryToHexa(sb.toString()));
                sb = new StringBuilder();
                counter=0;
            }
        }

        return finalHexaBitmap.toString();
    }

    public static String findMTI(String originalMsg) {
        return originalMsg.substring(0, 4);
    }

    public static String findBinaryBitmapFromHexa(String hexaBitmap) {
        StringBuilder binaryBitmap = new StringBuilder();
        char[] rawBitmap = hexaBitmap.toCharArray();
        for (int i=0; i<rawBitmap.length; i++) {
            binaryBitmap.append(DecimalHexBinaryConverter.hexaToBinary(String.valueOf(rawBitmap[i])));
        }

        return binaryBitmap.toString();
    }

    public static String findActiveDE(String binaryBitmap) {
        StringBuilder activeDE = new StringBuilder();
        char[] charBinaryBitmap = binaryBitmap.toCharArray();
        int counter = 0;
        for (int i=0;i<charBinaryBitmap.length;i++) {
            counter++;
            if (String.valueOf(charBinaryBitmap[i]).equals("1")) activeDE.append(String.valueOf(counter) + ";");
        }

        return activeDE.toString();
    }
}
