package org.util.thales.hsm;

import org.util.nanolog.Logger;
import org.util.thales.hsm.constants.PinBlockFormat;
import org.util.thales.hsm.internals.Strings;
import org.util.thales.hsm.model.HSMResponse;

public class ThalesService {

	private final HSMService hsmService;

	//@formatter:off
	public ThalesService(final HSMService hsmService) {
		this.hsmService  = hsmService;
	}

	/**
	 * 
	 * @param pan12		: The 12 right-most digits of the account number, excluding the check digit.
	 * @param pin		: The clear text PIN left-justified and padded with X'F to the maximum PIN length L. 
	 * 					  Value set with the CS (Configure Security) console command (range 5 – 13).
	 * @param logger
	 * @return
	 */
	public final HSMResponse encryptPinUnderLMK(final String pan12, final String pin, final Logger logger) {
		try {
			String            pinPadded   = Strings.padRight(new StringBuilder(pin), 'F', hsmService.hsmConfig.lengthOfPinLMK);
			String            command     = new StringBuilder(23).append("0000BA").append(pinPadded).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	public final HSMResponse encryptPinblockUnderLMK(final String pan12, final String pinblock, final String tpk, final PinBlockFormat format, final Logger logger) {
		try {
			String            command     = new StringBuilder(23).append("0000JC").append(tpk).append(pinblock).append(format).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 8 + hsmService.hsmConfig.lengthOfPinLMK);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
	
	public final HSMResponse decryptPinUnderLMK(final String pan12, final String pinlmk, final Logger logger) {
		try {
			String            command     = new StringBuilder(23).append("0000NG").append(pan12).append(pinlmk).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 8 + hsmService.hsmConfig.lengthOfPinLMK).replaceAll("F", "");
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
	
}
