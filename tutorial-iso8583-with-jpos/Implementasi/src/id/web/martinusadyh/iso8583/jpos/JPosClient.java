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
 *  JPosClient.java
 *  
 *  Created on Sep 15, 2010, 8:50:44 AM
 */

package id.web.martinusadyh.iso8583.jpos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMUX;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISORequest;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class JPosClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ISOException {
        String hostname = "localhost";
        int portNumber = 12345;

        // membuat sebuah packager
        ISOPackager packager = new GenericPackager("packager/iso93ascii.xml");
        // membuat channel
        ASCIIChannel channel = new ASCIIChannel(hostname, portNumber, packager);

        ISOMUX isoMux = new ISOMUX(channel) {
            @Override
            protected String getKey(ISOMsg m) throws ISOException {
                return super.getKey(m);
            }
        };

        new Thread(isoMux).start();

        // bikin network request
        ISOMsg networkReq = new ISOMsg();
        networkReq.setMTI("1800");
        networkReq.set(3, "123456");
        networkReq.set(7, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        networkReq.set(11, "000001");
        networkReq.set(12, new SimpleDateFormat("HHmmss").format(new Date()));
        networkReq.set(13, new SimpleDateFormat("MMdd").format(new Date()));
        networkReq.set(48, "Tutorial ISO 8583 Dengan Java");
        networkReq.set(70, "001");

        ISORequest req = new ISORequest(networkReq);
        isoMux.queue(req);
        
        ISOMsg reply = req.getResponse(50*1000);
        if (reply != null) {
            System.out.println("Req ["+new String(networkReq.pack()) + "]");
            System.out.println("Res ["+new String(reply.pack()) + "]");
        }
    }
}
