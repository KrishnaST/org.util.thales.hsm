package org.util.hsm.test;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.constants.KSNDescriptor;
import org.util.hsm.api.constants.PinBlockFormat;
import org.util.hsm.api.constants.PinKeyType;
import org.util.hsm.thales.ThalesHSMService;
import org.util.nanolog.Logger;

public class PVVtest {

	public static void main(String[] args) {

		HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
		HSMService hsmService = new ThalesHSMService();
		System.out.println(hsmService.pvv().calculatePVVUsingPin(hsmConfig, "102002021168", "1234", "U4F12B3A7123D83B504C9F06D899C5D6B", "1", Logger.CONSOLE));
		System.out.println(hsmService.pvv().calculatePVVUsingPinblock(hsmConfig, "102000221352", "7905D63257273DD3", PinBlockFormat.ANSIX98_FORMAT0,
				"U4F12B3A7123D83B504C9F06D899C5D6B", "1", PinKeyType.ZPK, "UB27EC3FAB16D5D4D0DFCC5C3246776E3", Logger.CONSOLE));
		;
		System.out.println(
				hsmService.pvv().validateDUKPTPin(hsmConfig, "333008902002", "0068", "1B83AA2E66BA0FCD", PinBlockFormat.ISOFORMAT0, "110000F15CAD880004D6",
						KSNDescriptor.KSNDES609, "U4F12B3A7123D83B504C9F06D899C5D6B", "1", "UB387DC23B416D398F17E431C3CB72B93", Logger.CONSOLE));
		System.out.println(hsmService.pvv().validateInterchangePin(hsmConfig, "102000221352", "9858", "7905D63257273DD3", PinBlockFormat.ANSIX98_FORMAT0,
				"U4F12B3A7123D83B504C9F06D899C5D6B", "1", "UB27EC3FAB16D5D4D0DFCC5C3246776E3", Logger.CONSOLE));
		;
		System.out.println(hsmService.pvv().validateTerminalPin(hsmConfig, "102000200004", "1685", "60BC4EC98BF9921A", PinBlockFormat.ISOFORMAT1,
				"U4F12B3A7123D83B504C9F06D899C5D6B", "1", "UB9322774320AB911EC740326592F688C", Logger.CONSOLE));
		;
		System.out.println(hsmService.pvv().changePVV(hsmConfig, "102000221352", "9858", "7905D63257273DD3", "7905D63257273DD3",
				"U4F12B3A7123D83B504C9F06D899C5D6B", PinBlockFormat.ANSIX98_FORMAT0, "1", PinKeyType.ZPK, "UB27EC3FAB16D5D4D0DFCC5C3246776E3", Logger.CONSOLE));

	}
}
