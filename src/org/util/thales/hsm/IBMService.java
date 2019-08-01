package org.util.thales.hsm;

import org.util.nanolog.Logger;
import org.util.thales.hsm.constants.KSNDescriptor;
import org.util.thales.hsm.constants.PinBlockFormat;
import org.util.thales.hsm.constants.PinKeyType;
import org.util.thales.hsm.internals.Strings;
import org.util.thales.hsm.internals.Utils;
import org.util.thales.hsm.model.HSMResponse;

public final class IBMService {

	private static final String DUKPT_MODE = "0";

	private final HSMService hsmService;

	public IBMService(final HSMService hsmService) {
		this.hsmService = hsmService;
	}

	//@formatter:off
	public final HSMResponse calculateOffsetUsingPin(final String pan12, final String pin, final String valdata, final String pvk, final Logger logger) {
		try {
			HSMResponse  BAResponse = hsmService.thales().encryptPinUnderLMK(pan12, pin, logger);
			if (BAResponse.isSuccess) {
				return calculateOffsetUsingPinLMK(pan12, BAResponse.value, valdata, pvk, logger);
			} else return BAResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
	

	public final HSMResponse calculateOffsetUsingPinLMK(final String pan12, final String pinlmk, final String valdata, final String pvk, final Logger logger) {
		try {
			final String      command     = new StringBuilder(70).append("0000DE").append(pvk).append(pinlmk).append(hsmService.hsmConfig.minimumPinLength)
											.append(pan12).append(hsmService.hsmConfig.decTab).append(valdata).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 12);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
	

	public final HSMResponse calculateOffsetUsingPinBlock(final String pan12, final String valdata, final String pinblock, final PinBlockFormat format,
			final PinKeyType pinKeyType, final String pinKey, final String pvk, final Logger logger) {
		try {
			final String command = new StringBuilder(118).append("0000BK").append(pinKeyType).append(pinKey).append(pvk).append(pinblock).append(format)
					.append(hsmService.hsmConfig.minimumPinLength).append(pan12).append(hsmService.hsmConfig.decTab).append(valdata).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 12);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}


	public final HSMResponse changePinOffset(final String pan12, final String valdata, final String pinblock, final PinBlockFormat format, final String offset, 
			final PinKeyType pinKeyType, final String pinKey, final String newPinblock, final String pvk, final Logger logger) {
		try {
			final String      command     = new StringBuilder(150).append("0000DU").append(pinKeyType).append(pinKey).append(pvk).append(pinblock).append(format)
					.append(hsmService.hsmConfig.minimumPinLength).append(pan12).append(hsmService.hsmConfig.decTab).append(valdata)
					.append(Strings.padRight(new StringBuilder(offset), 'F', 12)).append(newPinblock).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 12);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
	

	public final HSMResponse validateTerminalPin(final String pan12, final String valdata, final String pinblock,
			final PinBlockFormat format, final String offset, final String pvk, final String tpk, final Logger logger) {
		try {
			final String command = new StringBuilder().append("0000DA").append(tpk).append(pvk).append(hsmService.hsmConfig.maximumPinLength).append(pinblock)
					.append(format).append(hsmService.hsmConfig.minimumPinLength).append(pan12).append(hsmService.hsmConfig.decTab).append(valdata)
					.append(Strings.padRight(new StringBuilder(offset), 'F', 12)).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	public final HSMResponse validateInterchangePin(final String pan12, final String valdata, final String pinblock, final PinBlockFormat format, 
			final String offset, final String pvk, final String zpk, final Logger logger) {
		try {
			final String command = new StringBuilder().append("0000EA").append(zpk).append(pvk).append(hsmService.hsmConfig.maximumPinLength).append(pinblock)
					.append(format).append(hsmService.hsmConfig.minimumPinLength).append(pan12).append(hsmService.hsmConfig.decTab).append(valdata)
					.append(Strings.padRight(new StringBuilder(offset), 'F', 12)).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	
	public final HSMResponse validateDUKPTPin(final String pan12, final String valdata, final String pinblock, final PinBlockFormat format, final String offset,
			final String ksn, final KSNDescriptor descriptor, final String pvk, final String bdk, final Logger logger) {
		try {
			final String      command     = new StringBuilder(128).append("0000GO").append(DUKPT_MODE).append(bdk).append(pvk).append(descriptor).append(ksn)
					.append(pinblock).append(format).append(hsmService.hsmConfig.minimumPinLength).append(pan12).append(hsmService.hsmConfig.decTab)
					.append(valdata).append(Strings.padRight(new StringBuilder(offset), 'F', 12)).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	public static void main(String[] args) {
		
		HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
		HSMService hsmService = new HSMService(hsmConfig);
		hsmService.ibm().validateTerminalPin(Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"), "E57ECBA24533893C", PinBlockFormat.ANSIX98_FORMAT0, "1272", "4385B5DB5AEAF809", "U3DD674BF1A8603473A4A3DD8BE829A2F", Logger.CONSOLE);
		hsmService.ibm().validateDUKPTPin(Utils.getPAN12("0003330089020020"), Utils.getValidationData("0003330089020020"), "1B83AA2E66BA0FCD", PinBlockFormat.ISOFORMAT0, "5259", "110000F15CAD880004D6", KSNDescriptor.KSNDES609, "4385B5DB5AEAF809", "UB387DC23B416D398F17E431C3CB72B93", Logger.CONSOLE);
		hsmService.ibm().validateInterchangePin(Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"), "F8ADC16806B9CDFC", PinBlockFormat.ISOFORMAT0, "1395", "4385B5DB5AEAF809", "U0163EE5553AFBEDB2971BD3E1CDED378", Logger.CONSOLE);
		hsmService.ibm().calculateOffsetUsingPin(Utils.getPAN12("4135080750032932"), "1234", Utils.getValidationData("4135080750032932"), "4385B5DB5AEAF809", Logger.CONSOLE);
		hsmService.ibm().calculateOffsetUsingPinBlock(Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"), "F8ADC16806B9CDFC", PinBlockFormat.ISOFORMAT0, PinKeyType.ZPK, "U0163EE5553AFBEDB2971BD3E1CDED378", "4385B5DB5AEAF809", Logger.CONSOLE);
		hsmService.ibm().changePinOffset(Utils.getPAN12("4135080060005875"), Utils.getValidationData("4135080060005875"), "F8ADC16806B9CDFC", PinBlockFormat.ISOFORMAT0, "1395", PinKeyType.ZPK, "U0163EE5553AFBEDB2971BD3E1CDED378", "F8ADC16806B9CDFC", "4385B5DB5AEAF809", Logger.CONSOLE);
		/*
		changePinOffset(KeyType.ZPK, "U0163EE5553AFBEDB2971BD3E1CDED378", "4385B5DB5AEAF809", "F8ADC16806B9CDFC", PinBlockFormat.ISOFORMAT0, "4135080060005875", "1395", "F8ADC16806B9CDFC");
		*/
	}
	
}
