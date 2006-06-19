// Prevayler, The Free-Software Prevalence Layer
// Copyright 2001-2006 by Klaus Wuestefeld
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.
//
// Prevayler is a trademark of Klaus Wuestefeld.
// See the LICENSE file for license details.

package org.prevayler;

import java.io.IOException;

public class PrevaylerIOException extends IOException {

    private static final long serialVersionUID = 1L;

    public PrevaylerIOException() {
        super();
    }

    public PrevaylerIOException(String message) {
        super(message);
    }

    public PrevaylerIOException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }

    public PrevaylerIOException(Throwable cause) {
        super();
        initCause(cause);
    }

}
