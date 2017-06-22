/* 
 * @(#)transactionManager.java    Created on 2013-10-23
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package dynamic_data_source.transaction;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-23 下午8:10:18 $
 */
public class TransactionManager extends DataSourceTransactionManager {
    private static final long serialVersionUID = -4952638923724346237L;

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
        super.doCommit(status);
        // System.out.println("事务提交");

    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
        super.doRollback(status);
        System.out.println("事务回滚");
    }

}
