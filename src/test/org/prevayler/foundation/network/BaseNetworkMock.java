// Prevayler, The Free-Software Prevalence Layer
// Copyright 2001-2006 by Klaus Wuestefeld
//
// This library is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.
//
// Prevayler is a trademark of Klaus Wuestefeld.
// See the LICENSE file for license details.

package org.prevayler.foundation.network;

import org.prevayler.foundation.Cool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseNetworkMock {
    protected final Map _serverSocketByPort = new HashMap();

    protected Permit _permit = new Permit();

    protected void crashIfNotLocal(String serverIpAddress) {
        if (!serverIpAddress.equals("localhost"))
            throw new IllegalArgumentException("Only localhost connections are supported by the NetworkMock.");
    }

    public void crash() {
        _permit.expire();
    }

    public void recover() {
        _permit = new Permit();
    }

    protected ObjectServerSocketMock findServer(int serverPort) {
        return (ObjectServerSocketMock) _serverSocketByPort.get(new Integer(serverPort));
    }

    protected ObjectSocket startClient(int serverPort) throws IOException {
        ObjectServerSocketMock server = findServer(serverPort);
        if (server == null)
            throw new IOException("No server is listening on this port.");
        try {
            return server.openClientSocket();
        } catch (IOException e) {
            Cool.sleep(5);
            return server.openClientSocket(); // TODO Eliminate this retry
                                                // because client must try and
                                                // reconnect anyway.
        }
    }

    protected ObjectServerSocket startServer(int serverPort) throws IOException {
        ObjectServerSocketMock old = findServer(serverPort);
        if (old != null)
            throw new IOException("Port already in use.");

        ObjectServerSocketMock result = new ObjectServerSocketMock(_permit);
        _serverSocketByPort.put(new Integer(serverPort), result);

        return result;

    }
}
