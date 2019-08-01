package org.util.thales.hsm.test;

import org.util.nanolog.Logger;
import org.util.thales.hsm.HSMConfig;
import org.util.thales.hsm.HSMService;

public class CVVTest {

	public static HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
	public static HSMService hsmService = new HSMService(hsmConfig);
	
	public static void main(String[] args) {
		hsmService.cvv().calculateCVV("6084070020000310", "000", "2105", "E035EB860AB1D806", "CD2ABFC49162BC1B", Logger.CONSOLE);
	}
}

