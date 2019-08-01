package org.util.thales.hsm.model;

public class GenKeyResponse extends HSMResponse {

	public static final GenKeyResponse IO = new GenKeyResponse("IO");

	public String keyUnderLMK;
	public String keyUnderMasterKey;
	public String kcv;

	public GenKeyResponse(String responseCode) {
		super(responseCode);
	}

	@Override
	public String toString() {
		return "GenKeyResponse [keyUnderLMK=" + keyUnderLMK + ", keyUnderMasterKey=" + keyUnderMasterKey + ", kcv=" + kcv + "]";
	}

}
