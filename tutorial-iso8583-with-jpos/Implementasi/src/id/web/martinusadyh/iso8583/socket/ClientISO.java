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
 *  ClientISO.java
 *  
 *  Created on Sep 14, 2010, 1:52:31 AM
 */

package id.web.martinusadyh.iso8583.socket;

import id.web.martinusadyh.iso8583.helper.ISOUtil;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class ClientISO {

    private final static Integer PORT_SERVER = 12345;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket clientSocket = new Socket("localhost", PORT_SERVER);
        String networkRequest = buildNetworkReqMessage();

        PrintWriter outgoing = new PrintWriter(clientSocket.getOutputStream());
        InputStreamReader incoming = new InputStreamReader(clientSocket.getInputStream());

        System.out.println("Req. Msg ["+networkRequest+"]");

        outgoing.print(networkRequest);
        outgoing.flush();

        int data;
        StringBuffer sb = new StringBuffer();
        int counter = 0;
        // tambahan 4 karakter karena msg header adalah 4 digit msg length
        int lengthOfMsg = 4;
        while((data = incoming.read()) != 0) {
            counter++;
            sb.append((char) data);
            if (counter == 4) lengthOfMsg += Integer.valueOf(sb.toString());

            // klo panjang msg dari MTI sampai END OF MSG sama dengan nilai
            // header maka lanjutkan ke method processingMsg();
            if (lengthOfMsg == sb.toString().length()) {
                System.out.println("Rec. Msg ["+sb.toString()+"] len ["+sb.toString().length()+"]");
            }
        }

        outgoing.close();
        incoming.close();
        clientSocket.close();
    }

    private static String buildNetworkReqMessage() {
        StringBuilder networkReq = new StringBuilder();
        
        // MTI 1800
        networkReq.append("1800");
        // untuk request, DE yang aktif adalah DE[3,7,11,12,13,48 dan 70]
        String bitmapReq = ISOUtil.getHexaBitmapFromActiveDE(new int[] {3,7,11,12,13,48,70});
        networkReq.append(bitmapReq);
        // DE 3 processing code
        networkReq.append("000001");
        // DE 7 transmission date and time
        networkReq.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        // DE 11 system trace audit number
        networkReq.append("000001");
        // DE 12 local time transaction
        networkReq.append(new SimpleDateFormat("HHmmss").format(new Date()));
        // DE 13 local time transaction
        networkReq.append(new SimpleDateFormat("MMdd").format(new Date()));
        // DE 48 Additional Private Data
        final String clientID = "CLNT001";
        // length de 48
        String lengthBit48 = "";
        if (clientID.length() < 10) lengthBit48 = "00" + clientID.length();
        if (clientID.length() < 100 && clientID.length() >= 10) lengthBit48 = "0" + clientID.length();
        if (clientID.length() == 100) lengthBit48 = String.valueOf(clientID.length());
        networkReq.append(lengthBit48);
        networkReq.append(clientID);

        // DE 70 Network Information Code
        networkReq.append("001");

        // tambahkan 4 digit length of msg sbg header
        String msgHeader = "";
        if (networkReq.toString().length() < 10) msgHeader = "000" + networkReq.toString().length();
        if (networkReq.toString().length() < 100 && networkReq.toString().length() >= 10) msgHeader = "00" + networkReq.toString().length();
        if (networkReq.toString().length() < 1000 && networkReq.toString().length() >= 100) msgHeader = "0" + networkReq.toString().length();
        if (networkReq.toString().length() >= 1000) msgHeader = String.valueOf(networkReq.toString().length());

        StringBuilder finalNetworkReqMsg = new StringBuilder();
        finalNetworkReqMsg.append(msgHeader);
        finalNetworkReqMsg.append(networkReq.toString());
        
        return finalNetworkReqMsg.toString();
    }
}
