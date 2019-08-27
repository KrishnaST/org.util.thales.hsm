package org.util.hsm.safenet;

import org.util.hsm.api.CVVService;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.IBMService;
import org.util.hsm.api.KeyService;
import org.util.hsm.api.MACService;
import org.util.hsm.api.PVVService;
import org.util.hsm.api.ThalesService;
import org.util.hsm.api.TranslationService;

public class SafeNetHSMService implements HSMService {

	@Override
	public String getName() {
		return "SAFENET";
	}

	@Override
	public TranslationService translator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CVVService cvv() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyService key() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThalesService thales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PVVService pvv() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBMService ibm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MACService mac() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResponseDescription(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
