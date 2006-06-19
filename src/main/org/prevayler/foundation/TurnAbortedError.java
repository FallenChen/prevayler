// Prevayler, The Free-Software Prevalence Layer
// Copyright 2001-2006 by Klaus Wuestefeld
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.
//
// Prevayler is a trademark of Klaus Wuestefeld.
// See the LICENSE file for license details.

package org.prevayler.foundation;

import org.prevayler.PrevalenceError;

/**
 * Thrown by a {@link Turn} when its pipeline has been
 * {@link Turn#abort(String,Throwable) aborted} due to an unrecoverable error
 * condition.
 */
public class TurnAbortedError extends PrevalenceError {

    private static final long serialVersionUID = 1L;

    public TurnAbortedError() {
        super();
    }

    public TurnAbortedError(String message) {
        super(message);
    }

    public TurnAbortedError(String message, Throwable cause) {
        super(message, cause);
    }

    public TurnAbortedError(Throwable cause) {
        super(cause);
    }

}
