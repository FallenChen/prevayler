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

import org.prevayler.foundation.serialization.JavaSerializer;
import org.prevayler.foundation.serialization.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

public class DeepCopierTest extends TestCase {

    public void testNormal() {
        Object original = "foo";
        Object copy = DeepCopier.deepCopy(original, new JavaSerializer());

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    public void testParallel() throws IOException {
        Object original = "foo";
        Object copy = DeepCopier.deepCopyParallel(original, new JavaSerializer());

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    public void testParallelPathological() throws IOException {
        Object original = new Byte((byte) 17);

        Object copy = DeepCopier.deepCopyParallel(original, new Serializer() {

            public void writeObject(OutputStream stream, Object object) throws Exception {
                stream.write(((Byte) object).byteValue());
                stream.flush();

                Cool.sleep(10);

                // By this time the receiver has read an entire object; if
                // it doesn't wait for the actual end of the stream, the
                // following write
                // will get a "Read end dead" exception. Some real-life
                // serializers have this behavior
                // -- serialization may include a trailer, for example, that
                // deserialization
                // doesn't actually care about.

                stream.write(99);
            }

            public Object readObject(InputStream stream) throws Exception {
                return new Byte((byte) stream.read());
            }

        });

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

}
