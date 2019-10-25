package org.util.hsm.safenet;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.KeyService;
import org.util.hsm.api.SafenetHSMConnect;
import org.util.hsm.api.constants.BDKType;
import org.util.hsm.api.constants.KeyLength;
import org.util.hsm.api.constants.KeyScheme;
import org.util.hsm.api.constants.KeyScheme.ANSI;
import org.util.hsm.api.constants.KeyScheme.VARIANT;
import org.util.hsm.api.constants.KeyType;
import org.util.hsm.api.constants.MasterKeyType;
import org.util.hsm.api.model.GenKeyResponse;
import org.util.hsm.api.model.HSMResponse;
import org.util.nanolog.Logger;

public final class SafenetKeyService implements KeyService {

	@Override
	public HSMResponse calculateKCV(HSMConfig hsmConfig, String key, KeyLength keyLength, KeyType keyType, Logger logger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenKeyResponse generateKey(HSMConfig hsmConfig, KeyType keyType, VARIANT keyScheme, Logger logger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenKeyResponse generateExportKey(HSMConfig hsmConfig, KeyType keyType, VARIANT keyScheme, ANSI keySchemeExport, MasterKeyType masterKeyType,
			String mKeyLmk, Logger logger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenKeyResponse deriveIPEK(HSMConfig hsmConfig, String iksn, BDKType bdkType, String bdklmk, Logger logger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenKeyResponse deriveExportIPEK(HSMConfig hsmConfig, String iksn, BDKType bdkType, String bdklmk, MasterKeyType mKeyType, String mKeyLmk,
			Logger logger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenKeyResponse formKeyFromComponents(HSMConfig hsmConfig, KeyType keyType, KeyScheme keyScheme, Logger logger, String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final GenKeyResponse importKey(HSMConfig hsmConfig, KeyType keyType, VARIANT importScheme, String keyzmk, String zmklmk, Logger logger) {
		try {
			final String command = new StringBuilder().append("EE040300").append(zmklmk).append(keyType.safenetKeyType).append(String.format("%02X", keyzmk.length()/2)).append(keyzmk).toString();
			final String         response    = SafenetHSMConnect.send(hsmConfig, command.toString(), logger);
			final GenKeyResponse hsmResponse = new GenKeyResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) {
				final int keyLen = Integer.parseInt(response.substring(10, 12), 16);
				hsmResponse.keyUnderLMK = response.substring(12, 12 + keyLen*2);
				hsmResponse.kcv         = response.substring(12 + keyLen*2);
			}
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return GenKeyResponse.IO;
	}

	@Override
	public GenKeyResponse exportKey(HSMConfig hsmConfig, KeyType sourceKeyType, String keylmk, ANSI exportScheme, MasterKeyType mKeyType, String mKeylmk, Logger logger) {
		return null;
	}

}
