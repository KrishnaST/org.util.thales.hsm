package org.util.thales.hsm;

public class HSMConfig {

	public final String host;
	public final int    port;
	public String       minimumPinLength = "04";
	public String       maximumPinLength = "12";
	public String       decTab           = "0123456789012345";
	public int          lengthOfPinLMK   = 5;

	public HSMConfig(String host, int port) {
		this.host = host;
		this.port = port;
	}

}
