package org.util.hsm.thales;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.HSMConnect;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.IBMService;
import org.util.hsm.api.constants.KSNDescriptor;
import org.util.hsm.api.constants.PinBlockFormat;
import org.util.hsm.api.constants.PinKeyType;
import org.util.hsm.api.internals.Strings;
import org.util.hsm.api.model.HSMResponse;
import org.util.nanolog.Logger;

public final class ThalesIBMService implements IBMService {

	private final HSMService service;
	
	public ThalesIBMService(final HSMService service) {
		this.service = service;
	}
	//@formatter:off
	private static final String DUKPT_MODE = "0";
	
	@Override
	public HSMResponse calculateOffsetUsingPin(HSMConfig hsmConfig, String pan12, String pin, String valdata, String pvk, Logger logger) {
		try {
			HSMResponse  BAResponse = service.thales().encryptPinUnderLMK(hsmConfig, pan12, pin, logger);
			if (BAResponse.isSuccess) {
				return calculateOffsetUsingPinLMK(hsmConfig, pan12, BAResponse.value, valdata, pvk, logger);
			} else return BAResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	@Override
	public HSMResponse calculateOffsetUsingPinLMK(HSMConfig hsmConfig, String pan12, String pinlmk, String valdata, String pvk, Logger logger) {
		try {
			final String      command     = new StringBuilder(70).append("0000DE").append(pvk).append(pinlmk).append(hsmConfig.minimumPinLength)
											.append(pan12).append(hsmConfig.decTab).append(valdata).toString();
			final String      response    = HSMConnect.send(hsmConfig, command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 12);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	@Override
	public HSMResponse calculateOffsetUsingPinBlock(HSMConfig hsmConfig, String pan12, String valdata, String pinblock, PinBlockFormat format,
			PinKeyType pinKeyType, String pinKey, String pvk, Logger logger) {
		try {
			final String command = new StringBuilder(118).append("0000BK").append(pinKeyType).append(pinKey).append(pvk).append(pinblock).append(format)
					.append(hsmConfig.minimumPinLength).append(pan12).append(hsmConfig.decTab).append(valdata).toString();
			final String      response    = HSMConnect.send(hsmConfig, command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 12);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	@Override
	public HSMResponse changePinOffset(HSMConfig hsmConfig, String pan12, String valdata, String pinblock, PinBlockFormat format, String offset,
			PinKeyType pinKeyType, String pinKey, String newPinblock, String pvk, Logger logger) {
		try {
			final String      command     = new StringBuilder(150).append("0000DU").append(pinKeyType).append(pinKey).append(pvk).append(pinblock).append(format)
					.append(hsmConfig.minimumPinLength).append(pan12).append(hsmConfig.decTab).append(valdata)
					.append(Strings.padRight(new StringBuilder(offset), 'F', 12)).append(newPinblock).toString();
			final String      response    = HSMConnect.send(hsmConfig, command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 12);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	@Override
	public HSMResponse validateTerminalPin(HSMConfig hsmConfig, String pan12, String valdata, String pinblock, PinBlockFormat format, String offset, String pvk,
			String tpk, Logger logger) {
		try {
			final String command = new StringBuilder().append("0000DA").append(tpk).append(pvk).append(hsmConfig.maximumPinLength).append(pinblock)
					.append(format).append(hsmConfig.minimumPinLength).append(pan12).append(hsmConfig.decTab).append(valdata)
					.append(Strings.padRight(new StringBuilder(offset), 'F', 12)).toString();
			final String      response    = HSMConnect.send(hsmConfig, command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	@Override
	public HSMResponse validateInterchangePin(HSMConfig hsmConfig, String pan12, String valdata, String pinblock, PinBlockFormat format, String offset,
			String pvk, String zpk, Logger logger) {
		try {
			final String command = new StringBuilder().append("0000EA").append(zpk).append(pvk).append(hsmConfig.maximumPinLength).append(pinblock)
					.append(format).append(hsmConfig.minimumPinLength).append(pan12).append(hsmConfig.decTab).append(valdata)
					.append(Strings.padRight(new StringBuilder(offset), 'F', 12)).toString();
			final String      response    = HSMConnect.send(hsmConfig, command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	@Override
	public HSMResponse validateDUKPTPin(HSMConfig hsmConfig, String pan12, String valdata, String pinblock, PinBlockFormat format, String offset, String ksn,
			KSNDescriptor descriptor, String pvk, String bdk, Logger logger) {
		try {
			final String      command     = new StringBuilder(128).append("0000GO").append(DUKPT_MODE).append(bdk).append(pvk).append(descriptor).append(ksn)
					.append(pinblock).append(format).append(hsmConfig.minimumPinLength).append(pan12).append(hsmConfig.decTab)
					.append(valdata).append(Strings.padRight(new StringBuilder(offset), 'F', 12)).toString();
			final String      response    = HSMConnect.send(hsmConfig, command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

}
