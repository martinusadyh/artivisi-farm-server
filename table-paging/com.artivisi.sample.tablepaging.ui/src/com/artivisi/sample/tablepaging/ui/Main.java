/*
 *  Copyright (c) 2011 Martinus Ady H <martinus@artivisi.com>.
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
 *  Main.java
 *  
 *  Created on Jan 30, 2011, 5:44:10 AM
 */

package com.artivisi.sample.tablepaging.ui;

import com.artivisi.sample.tablepaging.service.TablePagingService;
import com.artivisi.sample.tablepaging.service.impl.jdbc.TablePagingServiceJDBC;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class Main {

    private static TablePagingService tablePagingService;
    private static Boolean jdbcMode = Boolean.FALSE;

    public static TablePagingService getTablePagingService() {
        return tablePagingService;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws SQLException {
        if (!jdbcMode) {
            ApplicationContext appCtx = new ClassPathXmlApplicationContext(
                    "classpath:applicationContext.xml");

            tablePagingService = (TablePagingService) appCtx.getBean("tablePagingService");
        } else {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("admin");
            dataSource.setDatabaseName("table_paging");
            dataSource.setServerName("localhost");
            dataSource.setPortNumber(3306);
            
            tablePagingService = new TablePagingServiceJDBC(dataSource.getConnection());
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }
                new MainForm().setVisible(true);
            }
        });
    }
}
