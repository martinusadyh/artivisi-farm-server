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
 *  TablePagingServiceJDBC.java
 *  
 *  Created on Jan 30, 2011, 9:03:45 PM
 */

package com.artivisi.sample.tablepaging.service.impl.jdbc;

import com.artivisi.sample.tablepaging.domain.hibernate.WPComment;
import com.artivisi.sample.tablepaging.service.TablePagingService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class TablePagingServiceJDBC implements TablePagingService {

    private final String COUNT_QRY = "select count(*) from wp_comments";
    private final String FIND_ALL_QRY = "select * from wp_comments limit ?,?";

    private PreparedStatement preparedCount;
    private PreparedStatement preparedFindAll;

    private Connection connection;

    public TablePagingServiceJDBC(Connection con) throws SQLException {
        this.connection = con;
        preparedCount = connection.prepareStatement(COUNT_QRY);
        preparedFindAll = connection.prepareStatement(FIND_ALL_QRY);
    }

    public List<WPComment> findAllComment(Integer pageNumber, Integer rowsPerPage) {
        try {
//            System.out.println("pn " + pageNumber + " rp " + rowsPerPage);
            System.out.println("prm 1 " + (rowsPerPage*(pageNumber-1)));
            List<WPComment> listWP = new ArrayList<WPComment>();
            preparedFindAll.setInt(1, (rowsPerPage*(pageNumber-1)));
            preparedFindAll.setInt(2, rowsPerPage);
            ResultSet rs = preparedFindAll.executeQuery();
            while (rs.next()) {
                WPComment comment = new WPComment();
                comment.setCommentID(rs.getInt("comment_ID"));
                comment.setCommentAuthor(rs.getString("comment_author"));
                comment.setCommentDate(rs.getDate("comment_date"));
                comment.setCommentContent(rs.getString("comment_content"));
                listWP.add(comment);
            }
            return listWP;
        } catch (SQLException ex) {
            Logger.getLogger(TablePagingServiceJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Integer countComments() {
        try {
            Integer totalRows = 0;
            ResultSet rs = preparedCount.executeQuery();
            while (rs.next()) {
                totalRows = rs.getInt("count(*)");
            }
            return totalRows;
        } catch (SQLException ex) {
            Logger.getLogger(TablePagingServiceJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
