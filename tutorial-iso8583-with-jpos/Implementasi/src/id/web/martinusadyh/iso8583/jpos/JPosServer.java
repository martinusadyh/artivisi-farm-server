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
 *  JPosServer.java
 *  
 *  Created on Sep 15, 2010, 8:35:35 AM
 */

package id.web.martinusadyh.iso8583.jpos;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class JPosServer implements ISORequestListener {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ISOException {
        String hostname = "localhost";
        int portNumber = 12345;
        
        // membuat sebuah packager
        ISOPackager packager = new GenericPackager("packager/iso93ascii.xml");
        // membuat channel
        ServerChannel channel = new ASCIIChannel(hostname, portNumber, packager);
        // membuat server
        ISOServer server = new ISOServer(portNumber, channel, null);
        server.addISORequestListener(new JPosServer());

        new Thread(server).start();

        System.out.println("Server siap menerima koneksi pada port [" + portNumber+"]");
    }

    public boolean process(ISOSource isoSrc, ISOMsg isoMsg) {
        try {
            System.out.println("Server menerima koneksi dari ["+((BaseChannel)isoSrc).getSocket().getInetAddress().getHostAddress()+"]");
            if (isoMsg.getMTI().equalsIgnoreCase("1800")) {
                    acceptNetworkMsg(isoSrc, isoMsg);
            }
        } catch (IOException ex) {
            Logger.getLogger(JPosServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ISOException ex) {
            Logger.getLogger(JPosServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void acceptNetworkMsg(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException {
        System.out.println("Accepting Network Management Request");
        ISOMsg reply = (ISOMsg) isoMsg.clone();
        reply.setMTI("1810");
        reply.set(39, "00");

        isoSrc.send(reply);
    }
}
