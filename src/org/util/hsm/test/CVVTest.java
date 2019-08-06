package org.util.hsm.test;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.HSMService;
import org.util.hsm.thales.ThalesHSMService;
import org.util.nanolog.Logger;

public class CVVTest {

	public static HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
	public static HSMService hsmService = new ThalesHSMService();

	public static void main(String[] args) {
		hsmService.cvv().calculateCVV(hsmConfig, "6084070020000310", "000", "2105", "E035EB860AB1D806", "CD2ABFC49162BC1B", Logger.CONSOLE);
	}
}
