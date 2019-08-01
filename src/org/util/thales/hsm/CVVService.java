package org.util.thales.hsm;

import org.util.nanolog.Logger;
import org.util.thales.hsm.model.HSMResponse;

public class CVVService {

	private final HSMService hsmService;

	//@formatter:off
	public CVVService(final HSMService hsmService) {
		this.hsmService  = hsmService;
	}
	
	/**
	 * 
	 * @param pan			: The primary account number for the card.
	 * @param serviceCode	: The card service code CVV1(000), ICVV(999), CVV2(custom).
	 * @param expiry		: The card expiration date.
	 * @param cvk1 			: The CVK A key used to calculate the CVV/CVC.
	 * @param cvk2 			: The CVK B key used to calculate the CVV/CVC.
	 * @param logger		: The logger object.
	 * @return				: The Card Verification Value/Code.
	 */
	public final HSMResponse calculateCVV(final String pan, final String serviceCode, final String expiry, final String cvk1, final String cvk2, final Logger logger) {
		try {
			final String      command     = new StringBuilder(60).append("0000CW").append(cvk1).append(cvk2).append(pan).append(";").append(expiry).append(serviceCode).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 11);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	/**
	 * 
	 * @param pan			: The primary account number for the card.
	 * @param serviceCode	: The card service code CVV1(000), ICVV(999), CVV2(custom).
	 * @param expiry		: The card expiration date.
	 * @param cvk1 			: The CVK A key used to calculate the CVV/CVC.
	 * @param cvk2 			: The CVK B key used to calculate the CVV/CVC.
	 * @param cvv			: CVV for verification.
	 * @param logger		: The logger object.
	 * @return
	 */
	public final HSMResponse validateCVV(final String pan, final String expiry, final String serviceCode, final String cvk1, final String cvk2, final String cvv, Logger logger) {
		try {
			final StringBuilder command     = new StringBuilder().append("0000CY").append(cvk1).append(cvk2).append(cvv).append(pan).append(";").append(expiry).append(serviceCode);
			final String        response    = hsmService.hsmConnect.send(command.toString(), logger);
			final HSMResponse   hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
}
