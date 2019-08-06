package org.util.hsm.api;

public interface HSMService {

	public abstract TranslationService translator();

	public abstract CVVService cvv();

	public abstract KeyService key();

	public abstract ThalesService thales();

	public abstract PVVService pvv();

	public abstract IBMService ibm();
}
