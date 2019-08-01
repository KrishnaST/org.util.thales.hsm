package org.util.thales.hsm;

import java.io.IOException;
import java.net.UnknownHostException;

import org.util.nanolog.Logger;
import org.util.thales.hsm.constants.BDKType;
import org.util.thales.hsm.constants.KSNDescriptor;
import org.util.thales.hsm.constants.PinBlockFormat;
import org.util.thales.hsm.model.HSMResponse;

public final class TranslationService {

	private final HSMService hsmService;

	//@formatter:off
	public TranslationService(final HSMService hsmService) {
		this.hsmService  = hsmService;
	}

	/**
	 * 
	 * @param pan12			: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param pinblock		: The source PIN block encrypted under the source ZPK.
	 * @param sourceFormat	: The format code for the source PIN block.
	 * @param sourceZPK		: The ZPK under which the PIN block is currently encrypted.
	 * @param targetFormat	: The format code for the destination PIN block.
	 * @param targetZPK		: Destination ZPK under which the PIN block is to be encrypted.
	 * @param logger
	 * @return				: The destination PIN block encrypted under the destination ZPK.
	 */
	public final HSMResponse fromZPKToZPK(final String pan12, final String pinblock, final PinBlockFormat sourceFormat, final String sourceZPK,
			final PinBlockFormat targetFormat, final String targetZPK, final Logger logger) {
		try {
			final String      command     = new StringBuilder().append("0000CC").append(sourceZPK).append(targetZPK).append(hsmService.hsmConfig.maximumPinLength)
											.append(pinblock).append(sourceFormat).append(targetFormat).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(10, 26);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	/**
	 * 
	 * @param pan12				: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param sourcePinBlock	: The source PIN block encrypted under the source TPK.
	 * @param sourceFormat		: The format code for the source PIN block.
	 * @param sourceTPK			: The TPK under which the PIN block is currently encrypted.
	 * @param targetFormat		: The format code for the destination PIN block.
	 * @param targetZPK			: Destination ZPK under which the PIN block is to be encrypted.
	 * @param logger
	 * @return					: The destination PIN block encrypted under the destination ZPK.
	 */
	public final HSMResponse fromTPKToZPK(final String pan12, final String sourcePinBlock, final PinBlockFormat sourceFormat, final String sourceTPK,
			final PinBlockFormat targetFormat, final String targetZPK, final Logger logger) {
		try {
			final String      command     = new StringBuilder().append("0000CA").append(sourceTPK).append(targetZPK).append(hsmService.hsmConfig.maximumPinLength)
											.append(sourcePinBlock).append(sourceFormat).append(targetFormat).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(10, 26);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	
	/**
	 * 
	 * @param pan12				: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param sourcePinBlock	: The source PIN block encrypted under the source TPK.
	 * @param sourceFormat		: The format code for the source PIN block.
	 * @param sourceTPK			: The TPK under which the PIN block is currently encrypted.
	 * @param sourceBDKType		: BDK1 Value '*' (X'2A). BDK2 Value '~' (X'7E).
	 * @param targetDescriptor	: 1st digit: Destination BDK Identifier Length (‘0’ – ‘F’) 2nd digit: Reserved. Should be ‘0’. 3rd digit: Device Identifier Length (‘0’ – ‘F’)
	 * @param targetKSN			: The Destination Key Serial Number.
	 * @param targetBDK			: BDK under which the PIN block is to be encrypted.
	 * @param targetFormat		: The format code for the destination PIN block.
	 * @param logger
	 * @return					: The destination PIN block encrypted under the destination BDK.
	 */
	public final HSMResponse fromTPKToBDK(final String pan12, final String sourcePinBlock, final PinBlockFormat sourceFormat, final String sourceTPK,
			final BDKType sourceBDKType, final KSNDescriptor targetDescriptor, final String targetKSN, final String targetBDK,
			final PinBlockFormat targetFormat, final Logger logger) {
		try {
			final String      command     = new StringBuilder().append("0000CA").append(sourceTPK).append(sourceBDKType).append(targetBDK)
											.append(targetDescriptor).append(targetKSN).append(hsmService.hsmConfig.maximumPinLength).append(sourcePinBlock)
											.append(sourceFormat).append(targetFormat).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(10, 26);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	
	/**
	 * 
	 * @param pan12				: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param sourcePinBlock	: The source PIN block encrypted under the source TPK.
	 * @param sourceFormat		: The format code for the source PIN block.
	 * @param sourceKSN			: The KSN supplied by the PIN pad.
	 * @param sourceBDKType		: BDK1 Value '*' (X'2A). BDK2 Value '~' (X'7E).
	 * @param sourceDescriptor	: 1st digit: Destination BDK Identifier Length (‘0’ – ‘F’) 2nd digit: Reserved. Should be ‘0’. 3rd digit: Device Identifier Length (‘0’ – ‘F’)
	 * @param sourceBDK			: Two types of BDK are supported: type-1 BDK and type-2 BDK.
	 * @param targetKSN			: The Destination Key Serial Number, supplied by the host.
	 * @param targeFormat		: The format code for the ‘Destination PIN Block’.
	 * @param targetBDKType		: BDK1 Value '*' (X'2A). BDK2 Value '~' (X'7E).
	 * @param targetDescriptor	: 1st digit: Destination BDK Identifier Length (‘0’ – ‘F’) 2nd digit: Reserved. Should be ‘0’. 3rd digit: Device Identifier Length (‘0’ – ‘F’)
	 * @param targetBDK			: Two types of BDK are supported: type-1 BDK and type-2 BDK.
	 * @param logger
	 * @return					: The PIN block encrypted under BDK.
	 */
	public final HSMResponse fromBDKToBDK(final String pan12, final String sourcePinBlock, final PinBlockFormat sourceFormat,final String sourceKSN, 
			final BDKType sourceBDKType, final KSNDescriptor sourceDescriptor, final String sourceBDK, final String targetKSN, final PinBlockFormat targeFormat,
			final BDKType targetBDKType, final KSNDescriptor targetDescriptor, final String targetBDK, final Logger logger) {
		try {
			final String      bdkTypeFlag = (sourceBDKType == BDKType.BDK2) ? sourceBDKType.toString() : "";
			final String      command     = new StringBuilder().append("0000G0").append(bdkTypeFlag).append(sourceBDK).append(targetBDKType).append(targetBDK)
					.append(sourceDescriptor).append(sourceKSN).append(targetDescriptor).append(targetKSN).append(sourcePinBlock).append(sourceFormat)
					.append(targeFormat).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(10, 26);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	
	/**
	 * 
	 * @param pan12				: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param sourcePinBlock	: The source PIN block encrypted under the source TPK.
	 * @param sourceFormat		: The format code for the source PIN block.
	 * @param sourceKSN			: The KSN supplied by the PIN pad.
	 * @param sourceDescriptor	: 1st digit: Destination BDK Identifier Length (‘0’ – ‘F’) 2nd digit: Reserved. Should be ‘0’. 3rd digit: Device Identifier Length (‘0’ – ‘F’)
	 * @param sourceBDKType		: BDK1 Value '*' (X'2A). BDK2 Value '~' (X'7E).
	 * @param sourceBDK			: Two types of BDK are supported: type-1 BDK and type-2 BDK.
	 * @param targetFormat		: The format code for the ‘Destination PIN Block’.
	 * @param targetZPK			: Destination ZPK under which the PIN block is to be encrypted.
	 * @param logger
	 * @return					: The destination PIN block encrypted under the destination ZPK.
	 */
	public final HSMResponse fromBDKToZPK(final String pan12, final String sourcePinBlock, final PinBlockFormat sourceFormat, final String sourceKSN,
			final KSNDescriptor sourceDescriptor, final BDKType sourceBDKType, final String sourceBDK, final PinBlockFormat targetFormat, final String targetZPK, final Logger logger) {
		try {
			final String      command     = new StringBuilder().append("0000G0").append(sourceBDKType).append(sourceBDK).append(targetZPK)
					.append(sourceDescriptor).append(sourceKSN).append(sourcePinBlock).append(sourceFormat).append(targetFormat).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(10, 26);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	
	/**
	 * 
	 * @param pan12				: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param sourcePinblock	: The source PIN block encrypted under the ZPK.
	 * @param sourceFormat		: The format code for the source PIN block.
	 * @param sourceZPK			: The ZPK under which the PIN block is currently encrypted.
	 * @param logger
	 * @return					: (L N) The PIN encrypted under the LMK.
	 */
	public final HSMResponse fromZPKToLMK(final String pan12, final String sourcePinblock, final PinBlockFormat sourceFormat, final String sourceZPK, final Logger logger) {
		try {
			final StringBuilder command     = new StringBuilder(70).append("0000JE").append(sourceZPK).append(sourcePinblock).append(sourceFormat).append(pan12);
			final String      response    	= hsmService.hsmConnect.send(command.toString(), logger);
			final HSMResponse hsmResponse 	= new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 8 + hsmService.hsmConfig.lengthOfPinLMK);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	
	/**
	 * 
	 * @param pan12				: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param sourcePinblock	: The source PIN block encrypted under the TPK.
	 * @param sourceFormat		: One of the valid PIN block format codes.
	 * @param sourceTPK			: The TPK under which the PIN block is currently encrypted.
	 * @param logger
	 * @return					: (L N) The PIN encrypted under the LMK.
	 */
	public final HSMResponse fromTPKToLMK(final String pan12, final String sourcePinblock, final PinBlockFormat sourceFormat, final String sourceTPK, final Logger logger) {
		try {
			final String      command     = new StringBuilder().append("0000JC").append(sourceTPK).append(sourcePinblock).append(sourceFormat).append(pan12).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(8, 8 + hsmService.hsmConfig.lengthOfPinLMK);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	/**
	 * 
	 * @param pan12				: The 12 right-most digits of the PAN/Token, excluding the check digit.
	 * @param pinlmk			: The PIN encrypted under the LMK.
	 * @param targetFormat		: One of the valid PIN block format codes.
	 * @param targetZPK			: The ZPK under which the PIN block is to be encrypted.
	 * @param logger
	 * @return					: The PIN block encrypted under the ZPK.
	 */
	public final HSMResponse fromLMKToZPK(final String pan12, final String pinlmk, final PinBlockFormat targetFormat, final String targetZPK, final Logger logger) {
		try {
			final String      command     = new StringBuilder().append("0000JG").append(targetZPK).append(targetFormat).append(pan12).append(pinlmk).toString();
			final String      response    = hsmService.hsmConnect.send(command, logger);
			final HSMResponse hsmResponse = new HSMResponse(response.substring(6, 8));
			if (hsmResponse.isSuccess) hsmResponse.value = response.substring(10, 26);
			return hsmResponse;
		} catch (Exception e) {logger.error(e);}
		return HSMResponse.IO;
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
		HSMService hsmService = new HSMService(hsmConfig);
		hsmService.translator().fromZPKToZPK("368320008173", "B58ADECA2C972098", PinBlockFormat.ISOFORMAT0, "UB27EC3FAB16D5D4D0DFCC5C3246776E3",
				PinBlockFormat.ISOFORMAT0, "U401770057601955CD7797A450474C4E1", Logger.CONSOLE);
		System.exit(0);
		//System.out.println(tpkToZPK("UA7D66FBBBA03A97341F88278119D48A6", "UF5E77E2E438FE6C7D07FD0020EFF8FEA", "8EF9682FDF6A57F1", PinBlockFormat.ANSIX98_FORMAT0,
		//PinBlockFormat.ANSIX98_FORMAT0, "4591500245884867"));
		//System.out.println(HexConversion("5;2160;6>582>767"));
		//System.out.println(tpkToLMK("U2B3DECFA7FE5B3F71412AA92126372A2", "5B2160B6E582E767", PinBlockFormat.ISOFORMAT0, "6528730000003261"));
		//System.out.println(PVV.calculatePVVLMK("6528730000003261", "71278", "U4F12B3A7123D83B504C9F06D899C5D6B", "1"));
		//translatePinBlock(KeyType.ZPK, KeyType.ZPK, "U401770057601955CD7797A450474C4E1", "U401770057601955CD7797A450474C4E1", "67093FF6F5C30631", PinBlockFormat.ISOFORMAT0, PinBlockFormat.ISOFORMAT0, "5413330089020029");
		//System.out.println(IBM3624.calculateOffsetUsingPinLMK(zpkToLMK("U401770057601955CD7797A450474C4E1", "67093FF6F5C30631", PinBlockFormat.ISOFORMAT0, "5413330089020029").substring(8), "5413330089020029", "4385B5DB5AEAF809"));
	}

}
