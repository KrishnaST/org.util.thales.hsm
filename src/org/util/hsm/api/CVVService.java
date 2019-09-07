package org.util.hsm.api;

import org.util.hsm.api.model.HSMResponse;
import org.util.nanolog.Logger;

public interface CVVService {

	
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
	public HSMResponse calculateCVV(final HSMConfig hsmConfig, final String pan, final String serviceCode, final String expiry, final String cvk1, final String cvk2, final Logger logger);
		

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
	public HSMResponse validateCVV(final HSMConfig hsmConfig, final String pan, final String expiry, final String serviceCode, final String cvk1, final String cvk2, final String cvv, Logger logger);
}
