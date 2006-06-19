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

/**
 * Tells the time.
 * 
 * @see Prevayler
 */
public interface Clock {

    /**
     * Tells the time.
     * 
     * @return A Date greater or equal to the one returned by the last call to
     *         this method. If the time is the same as the last call, the SAME
     *         Date object is returned rather than a new, equal one.
     */
    public java.util.Date time();

}
