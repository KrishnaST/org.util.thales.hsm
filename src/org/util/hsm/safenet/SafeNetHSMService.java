package org.util.hsm.safenet;

import org.util.hsm.api.CVVService;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.IBMService;
import org.util.hsm.api.KeyService;
import org.util.hsm.api.MACService;
import org.util.hsm.api.PVVService;
import org.util.hsm.api.ThalesService;
import org.util.hsm.api.TranslationService;

public final class SafeNetHSMService implements HSMService {

	private final KeyService keyService = new SafenetKeyService();

	@Override
	public String getName() {
		return "SAFENET";
	}

	@Override
	public final TranslationService translator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final CVVService cvv() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final KeyService key() {
		return keyService;
	}

	@Override
	public final ThalesService thales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final PVVService pvv() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final IBMService ibm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final boolean shutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final MACService mac() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final String getResponseDescription(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
