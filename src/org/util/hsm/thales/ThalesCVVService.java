package org.util.hsm.thales;

import org.util.hsm.api.CVVService;
import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.ThalesHSMConnect;
import org.util.hsm.api.model.HSMResponse;
import org.util.nanolog.Logger;

public final class ThalesCVVService implements CVVService {

	//@formatter:off
	public final HSMResponse calculateCVV(final HSMConfig hsmConfig, final String pan, final String serviceCode, final String expiry, final String cvk1, final String cvk2, final Logger logger) {
		try {
			final String      command     = new StringBuilder(60).append("0000CW").append(cvk1).append(cvk2).append(pan).append(";").append(expiry).append(serviceCode).toString();
			final String      response    = ThalesHSMConnect.send(hsmConfig, command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 11);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	public final HSMResponse validateCVV(final HSMConfig hsmConfig, final String pan, final String expiry, final String serviceCode, final String cvk1, final String cvk2, final String cvv, Logger logger) {
		try {
			final StringBuilder command     = new StringBuilder().append("0000CY").append(cvk1).append(cvk2).append(cvv).append(pan).append(";").append(expiry).append(serviceCode);
			final String        response    = ThalesHSMConnect.send(hsmConfig, command.toString(), logger);
			final HSMResponse   hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}
}
