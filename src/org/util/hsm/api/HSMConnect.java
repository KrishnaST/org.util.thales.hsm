package org.util.hsm.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.util.nanolog.Logger;

public class HSMConnect {

	//@formatter:off
	public static final String send(final HSMConfig hsmConfig, final String command, final Logger logger) throws UnknownHostException, IOException {
		logger.trace("hsm command", command);
		try(Socket sc = new Socket(hsmConfig.host, hsmConfig.port);
			DataInputStream din = new DataInputStream(sc.getInputStream());
			DataOutputStream dos = new DataOutputStream(sc.getOutputStream())) {
			sc.setSoTimeout(5000);
			dos.writeUTF(command);
			dos.flush();
			final String response = din.readUTF();
			logger.trace("hsm response", response);
			return response;
		}
	}
}