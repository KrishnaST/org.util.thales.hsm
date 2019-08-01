package org.util.thales.hsm.constants;


public enum KeyType{
	ZMK("000"),
	ZPK("001"),
	TMK("002"),
	TPK("002"),
	PVK("002"),
	TAK("003"),
	WWK("006"),
	ZAK("006"),
	BDK1("009"),
	ZEK("00A"),
	DEK("00B"),
	MK_AC("109"),
	MK_SMI("209"),
	IPEK("302"),
	MK_SMC("309"),
	TEK("30B"),
	CVK("402"),
	CSCK("402"),
	MK_DAC("409"),
	MK_DN("509"),
	BDK2("609"),
	MK_CVC3("709"),
	BDK3("809");
	
	private String keyType;
	
	private KeyType(String keyType) {
		this.keyType = keyType;
	}
	
	public String toString() {
		return keyType;
	}
}
