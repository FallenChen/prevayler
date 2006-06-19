// Prevayler, The Free-Software Prevalence Layer
// Copyright 2001-2006 by Klaus Wuestefeld
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.
//
// Prevayler is a trademark of Klaus Wuestefeld.
// See the LICENSE file for license details.

package org.prevayler.demos.demo2.business.transactions;

import org.prevayler.demos.demo2.business.Account;
import org.prevayler.demos.demo2.business.Bank;

import java.util.Date;

abstract class AccountTransaction extends BankTransaction {

    private long _accountNumber;

    AccountTransaction() {
    } // Necessary for Skaringa XML serialization. This would normally be
        // private, but must be package visible (or protected) in this case in
        // order for subclasses to supply private default constructors and
        // actually compile.

    protected AccountTransaction(Account account) {
        _accountNumber = account.number();
    }

    protected Object executeAndQuery(Bank bank, Date timestamp) throws Exception {
        executeAndQuery(bank.findAccount(_accountNumber), timestamp);
        return null;
    }

    protected abstract void executeAndQuery(Account account, Date timestamp) throws Exception;
}