package org.util.thales.hsm;

public class HSMService {

	protected final HSMConfig  hsmConfig;
	protected final HSMConnect hsmConnect;

	private final TranslationService translatorService;
	private final CVVService         cvvService;
	private final KeyService         keyService;
	private final ThalesService      thalesService;
	private final PVVService         pvvService;
	private final IBMService         ibmService;

	public HSMService(final HSMConfig hsmConfig) {
		this.hsmConfig         = hsmConfig;
		this.hsmConnect        = new HSMConnect(hsmConfig);
		this.translatorService = new TranslationService(this);
		this.cvvService        = new CVVService(this);
		this.keyService        = new KeyService(this);
		this.thalesService     = new ThalesService(this);
		this.pvvService        = new PVVService(this);
		this.ibmService        = new IBMService(this);
	}

	public final TranslationService translator() {
		return translatorService;
	}

	public final CVVService cvv() {
		return cvvService;
	}

	public final KeyService key() {
		return keyService;
	}

	public final ThalesService thales() {
		return thalesService;
	}

	public final PVVService pvv() {
		return pvvService;
	}
	
	public final IBMService ibm() {
		return ibmService;
	}

}
