package org.util.hsm.test;

import java.io.IOException;
import java.net.UnknownHostException;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.constants.PinBlockFormat;
import org.util.hsm.thales.ThalesHSMService;
import org.util.nanolog.Logger;

public class TranslationTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
		HSMService hsmService = new ThalesHSMService();
		hsmService.translator().fromZPKToZPK(hsmConfig, "368320008173", "B58ADECA2C972098", PinBlockFormat.ISOFORMAT0, "UB27EC3FAB16D5D4D0DFCC5C3246776E3",
				PinBlockFormat.ISOFORMAT0, "U401770057601955CD7797A450474C4E1", Logger.CONSOLE);
	}
}
