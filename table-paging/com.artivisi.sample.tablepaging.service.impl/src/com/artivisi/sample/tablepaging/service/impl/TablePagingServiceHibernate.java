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
 *  TablePagingServiceHibernate.java
 *
 *  Created on Jan 30, 2011, 5:17:30 AM
 */
package com.artivisi.sample.tablepaging.service.impl;

import com.artivisi.sample.tablepaging.domain.hibernate.WPComment;
import com.artivisi.sample.tablepaging.service.TablePagingService;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martinus Ady H <martinus@artivisi.com>
 */
@Service("tablePagingService")
@Transactional
public class TablePagingServiceHibernate implements TablePagingService {

    @Autowired private SessionFactory sessionFactory;

    public List<WPComment> findAllComment(Integer pageNumber, Integer rowsPerPage) {
        return sessionFactory.getCurrentSession()
                .createQuery("from WPComment wp")
                .setFirstResult(rowsPerPage*(pageNumber-1))
                .setMaxResults(rowsPerPage)
                .list();
    }

    public Integer countComments() {
        Long totalRow = (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(*) from WPComment wp")
                .uniqueResult();

        return totalRow.intValue();
    }
}
