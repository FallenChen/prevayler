// Prevayler, The Free-Software Prevalence Layer
// Copyright 2001-2006 by Klaus Wuestefeld
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.
//
// Prevayler is a trademark of Klaus Wuestefeld.
// See the LICENSE file for license details.

package org.prevayler.implementation;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.foundation.FileIOTest;

import java.io.IOException;
import java.io.Serializable;

public class PrevaylerFactoryTest extends FileIOTest {

    private static final Serializable POJO = new Serializable() {
        private static final long serialVersionUID = -8661727552300291738L;
    };

    public void testTransientPrevaylerCreation() {
        Prevayler prevayler = PrevaylerFactory.createTransientPrevayler(POJO);
        assertEquals(POJO, prevayler.prevalentSystem());
    }

    public void testSnapshotPrevaylerCreation() throws IOException, ClassNotFoundException {
        Prevayler prevayler = PrevaylerFactory.createPrevayler(POJO, _testDirectory);
        assertEquals(POJO, prevayler.prevalentSystem());
    }

    public void testCheckpointPrevaylerCreation() {
        Prevayler prevayler = PrevaylerFactory.createCheckpointPrevayler(POJO, _testDirectory);
        assertEquals(POJO, prevayler.prevalentSystem());
    }

}
