package org.util.hsm.test;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.constants.KSNDescriptor;
import org.util.hsm.api.constants.PinBlockFormat;
import org.util.hsm.api.constants.PinKeyType;
import org.util.hsm.api.util.Utils;
import org.util.hsm.thales.ThalesHSMService;
import org.util.nanolog.Logger;

public class IBMTest {

	public static void main(String[] args) {
		HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
		HSMService hsmService = new ThalesHSMService();
		hsmService.ibm().calculateOffsetUsingPin(hsmConfig, Utils.getPAN12("6077990020000011"), "9211", Utils.getValidationData("6077990020000011"),
				"4385B5DB5AEAF809", Logger.CONSOLE);
		hsmService.ibm().validateTerminalPin(hsmConfig, Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"), "E57ECBA24533893C",
				PinBlockFormat.ANSIX98_FORMAT0, "1272", "4385B5DB5AEAF809", "U3DD674BF1A8603473A4A3DD8BE829A2F", Logger.CONSOLE);
		hsmService.ibm().validateDUKPTPin(hsmConfig, Utils.getPAN12("0003330089020020"), Utils.getValidationData("0003330089020020"), "1B83AA2E66BA0FCD",
				PinBlockFormat.ISOFORMAT0, "5259", "110000F15CAD880004D6", KSNDescriptor.KSNDES609, "4385B5DB5AEAF809", "UB387DC23B416D398F17E431C3CB72B93",
				Logger.CONSOLE);
		hsmService.ibm().validateInterchangePin(hsmConfig, Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"), "F8ADC16806B9CDFC",
				PinBlockFormat.ISOFORMAT0, "1395", "4385B5DB5AEAF809", "U0163EE5553AFBEDB2971BD3E1CDED378", Logger.CONSOLE);
		
		hsmService.ibm().calculateOffsetUsingPinBlock(hsmConfig, Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"),
				"F8ADC16806B9CDFC", PinBlockFormat.ISOFORMAT0, PinKeyType.ZPK, "U0163EE5553AFBEDB2971BD3E1CDED378", "4385B5DB5AEAF809", Logger.CONSOLE);
		hsmService.ibm().changePinOffset(hsmConfig, Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"), "F8ADC16806B9CDFC",
				PinBlockFormat.ISOFORMAT0, "1395", PinKeyType.ZPK, "U0163EE5553AFBEDB2971BD3E1CDED378", "F8ADC16806B9CDFC", "4385B5DB5AEAF809", Logger.CONSOLE);
	}

}
