// Prevayler, The Free-Software Prevalence Layer
// Copyright 2001-2006 by Klaus Wuestefeld
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.
//
// Prevayler is a trademark of Klaus Wuestefeld.
// See the LICENSE file for license details.

package org.prevayler.demos.scalability.jdbc;

import org.prevayler.demos.scalability.ScalabilityTestSubject;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class JDBCScalabilitySubject implements ScalabilityTestSubject {

    protected final String connectionURL;

    protected final String user;

    protected final String password;

    {
        System.gc();
    }

    protected JDBCScalabilitySubject(String jdbcDriverClassName, String connectionURL, String user, String password) {
        try {
            Class.forName(jdbcDriverClassName);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Exception loading JDBC driver class: " + jdbcDriverClassName);
        }

        this.connectionURL = connectionURL;
        this.user = user;
        this.password = password;
    }

    public String name() {
        return "JDBC";
    }

    public void replaceAllRecords(int records) {
        ((JDBCScalabilityConnection) createTestConnection()).replaceAllRecords(records);
    }

    protected Connection createConnection() {
        try {

            return DriverManager.getConnection(connectionURL, user, password);

        } catch (SQLException sqlx) {
            sqlx.printStackTrace();
            throw new RuntimeException("Exception while trying to connect: " + sqlx);
        }
    }

    public void reportResourcesUsed(PrintStream out) {
    }

}
