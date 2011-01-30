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
 *  CommentTableModel.java
 *  
 *  Created on Jan 30, 2011, 5:57:50 AM
 */

package com.artivisi.sample.tablepaging.ui;

import com.artivisi.sample.tablepaging.domain.hibernate.WPComment;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
public class CommentTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final String[] HEADER = new String[] {
        "#", "ID", "AUTHOR", "DATE", "CONTENT"
    };
    
    private List<WPComment> wPComments;

    public CommentTableModel(List<WPComment> wPComments) {
        this.wPComments = wPComments;
    }

    public int getRowCount() {
        return wPComments.size();
    }

    public int getColumnCount() {
        return HEADER.length;
    }

    @Override
    public String getColumnName(int column) {
        return HEADER[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        WPComment comment = wPComments.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex+1;
            case 1: return comment.getCommentID();
            case 2: return comment.getCommentAuthor();
            case 3: return comment.getCommentDate();
            case 4: return comment.getCommentContent();
            default: return "";
        }
    }
}
