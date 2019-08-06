package org.util.thales.hsm.test;

import java.io.IOException;
import java.net.UnknownHostException;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.constants.PinBlockFormat;
import org.util.hsm.thales.ThalesHSMService;
import org.util.nanolog.Logger;

public class TranslationTest {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
		HSMService hsmService = new ThalesHSMService();
		hsmService.translator().fromZPKToZPK(hsmConfig, "368320008173", "B58ADECA2C972098", PinBlockFormat.ISOFORMAT0, "UB27EC3FAB16D5D4D0DFCC5C3246776E3",
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
