package org.util.thales.hsm;

import org.util.nanolog.Logger;
import org.util.thales.hsm.constants.KSNDescriptor;
import org.util.thales.hsm.constants.PinBlockFormat;
import org.util.thales.hsm.constants.PinKeyType;
import org.util.thales.hsm.model.HSMResponse;

public class PVVService {

	private final HSMService hsmService;

	private static final String DUKPT_MODE = "0";

	//@formatter:off
	public PVVService(final HSMService hsmService) {
		this.hsmService = hsmService;
	}

	public final HSMResponse calculatePVVUsingPin(final String pan12, final String pin, final String pvk, final String pvki, final Logger logger) {
		try {
			final HSMResponse BAResponse = hsmService.thales().encryptPinUnderLMK(pan12, pin, logger);
			if (!BAResponse.isSuccess) return BAResponse;
			final String      command     = new StringBuilder("0000DG").append(pvk).append(BAResponse.value).append(pan12).append(pvki).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
	
	//@formatter:on
	public final HSMResponse calculatePVVUsingPinblock(final String pan12, final String pinblock, final PinBlockFormat format, final String pvk,
			final String pvki, final PinKeyType pinKeyType, final String pinKey, final Logger logger) {
		try {
			final String      command     = new StringBuilder("0000FW").append(pinKeyType).append(pinKey).append(pvk).append(pinblock).append(format)
											.append(pan12).append(pvki).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	//@formatter:off
	public final HSMResponse validateDUKPTPin(final String pan12, final String pvv, final String pinBlock, final PinBlockFormat format,
			final String ksn, final KSNDescriptor ksnDescriptor, final String pvk, final String pvki, final String bdk, final Logger logger) {
		try {
			final String command  = new StringBuilder(128).append("0000GQ").append(DUKPT_MODE).append(bdk).append(pvk).append(ksnDescriptor)
									.append(ksn).append(pinBlock).append(format).append(pan12).append(pvki).append(pvv).toString();
			final String response = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	
	public final HSMResponse validateInterchangePin(final String pan12, final String pvv, final String pinBlock, final PinBlockFormat format,
			final String pvk, final String pvki, final String zpk, final Logger logger) {
		try {
			final String command = new StringBuilder("0000EC").append(zpk).append(pvk).append(pinBlock).append(format).append(pan12).append(pvki)
								  .append(pvv).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}


	public final HSMResponse validateTerminalPin(final String pan12, final String pvv, final String pinBlock, final PinBlockFormat format, final String pvk,
			final String pvki, final String tpk, final Logger logger) {
		try {
			final String command = new StringBuilder("0000DC").append(tpk).append(pvk).append(pinBlock).append(format).append(pan12).append(pvki)
									.append(pvv).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	public final HSMResponse changePVV(final String pan12, final String pvv, final String pinBlock, final String newPinBlock, final String pvk,
			final PinBlockFormat format, final String pvki, final PinKeyType pinKeyType, final String pinKey, final Logger logger) {
		try {
			final String      command     = new StringBuilder(128).append("0000CU").append(pinKeyType).append(pinKey).append(pvk).append(pinBlock)
											.append(format).append(pan12).append(pvki).append(pvv).append(newPinBlock).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if(hsmResponse.isSuccess) hsmResponse.value = response.substring(8,12);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}


	public static void main(String[] args) {
		HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
		HSMService hsmService = new HSMService(hsmConfig);
		//System.out.println(hsmService.pvv().calculatePVVUsingPin("102002021168", "1234", "U4F12B3A7123D83B504C9F06D899C5D6B", "1", Logger.CONSOLE));
		System.out.println(hsmService.pvv().calculatePVVUsingPinblock("102000221352", "7905D63257273DD3", PinBlockFormat.ANSIX98_FORMAT0, "U4F12B3A7123D83B504C9F06D899C5D6B", "1", PinKeyType.ZPK, "UB27EC3FAB16D5D4D0DFCC5C3246776E3", Logger.CONSOLE));;
		//System.out.println(hsmService.pvv().validateDUKPTPin("333008902002", "0068", "1B83AA2E66BA0FCD", PinBlockFormat.ISOFORMAT0, "110000F15CAD880004D6", KSNDescriptor.KSNDES609, "U4F12B3A7123D83B504C9F06D899C5D6B", "1", "UB387DC23B416D398F17E431C3CB72B93", Logger.CONSOLE));
		//System.out.println(hsmService.pvv().validateInterchangePin("102000221352", "9858", "7905D63257273DD3", PinBlockFormat.ANSIX98_FORMAT0, "U4F12B3A7123D83B504C9F06D899C5D6B", "1", "UB27EC3FAB16D5D4D0DFCC5C3246776E3", Logger.CONSOLE));;
		//System.out.println(hsmService.pvv().validateTerminalPin("102000200004", "1685", "60BC4EC98BF9921A", PinBlockFormat.ISOFORMAT1, "U4F12B3A7123D83B504C9F06D899C5D6B", "1", "UB9322774320AB911EC740326592F688C", Logger.CONSOLE));;
		//System.out.println(hsmService.pvv().changePVV("102000221352", "9858", "7905D63257273DD3", "7905D63257273DD3", "U4F12B3A7123D83B504C9F06D899C5D6B", PinBlockFormat.ANSIX98_FORMAT0, "1", PinKeyType.ZPK, "UB27EC3FAB16D5D4D0DFCC5C3246776E3", Logger.CONSOLE));
	}

}
