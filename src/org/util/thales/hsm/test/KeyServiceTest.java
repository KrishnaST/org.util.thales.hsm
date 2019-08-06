package org.util.thales.hsm.test;

import org.util.hsm.api.HSMConfig;
import org.util.hsm.api.HSMService;
import org.util.hsm.api.constants.BDKType;
import org.util.hsm.api.constants.KeyScheme;
import org.util.hsm.api.constants.KeyType;
import org.util.hsm.api.constants.MasterKeyType;
import org.util.hsm.api.constants.KeyScheme.ANSI;
import org.util.hsm.api.constants.KeyScheme.VARIANT;
import org.util.hsm.api.model.GenKeyResponse;
import org.util.hsm.thales.ThalesHSMService;
import org.util.nanolog.Logger;

public class KeyServiceTest {

	public static HSMConfig  hsmConfig  = new HSMConfig("10.100.5.21", 6046);
	public static HSMService hsmService = new ThalesHSMService();

	public static void generateKeyTest() {
		GenKeyResponse response = hsmService.key().generateKey(hsmConfig, KeyType.ZPK, KeyScheme.VARIANT.SINGLE_LEN, Logger.CONSOLE);
		System.out.println("isSuccess : " + response.isSuccess);
		System.out.println("responseCode : " + response.responseCode);
		System.out.println("keyUnderLMK : " + response.keyUnderLMK);
		System.out.println("kcv : " + response.kcv);
	}

	public static void generateExportKey() {
		GenKeyResponse response = hsmService.key().generateExportKey(hsmConfig,KeyType.ZPK, VARIANT.TRIPPLE_LEN, ANSI.TRIPPLE_LEN, MasterKeyType.ZMK,
				"U7BCEFD84EB855C60CB45F036F2FA63F1", Logger.CONSOLE);
		System.out.println("isSuccess : " + response.isSuccess);
		System.out.println("responseCode : " + response.responseCode);
		System.out.println("keyUnderLMK : " + response.keyUnderLMK);
		System.out.println("keyUnderMasterKey : " + response.keyUnderMasterKey);
		System.out.println("kcv : " + response.kcv);
	}

	public static void deriveIPEKTest() {
		GenKeyResponse response = hsmService.key().deriveIPEK(hsmConfig,"110000F15CAD88F", BDKType.BDK1, "UB387DC23B416D398F17E431C3CB72B93", Logger.CONSOLE);
		System.out.println("isSuccess : " + response.isSuccess);
		System.out.println("keyUnderLMK : " + response.keyUnderLMK);
		System.out.println("kcv : " + response.kcv);
	}

	public static void deriveExportIPEKTest() {
		GenKeyResponse response = hsmService.key().deriveExportIPEK(hsmConfig,"110000F15CAD880", BDKType.BDK1, "UB387DC23B416D398F17E431C3CB72B93", MasterKeyType.ZMK,
				"U7BCEFD84EB855C60CB45F036F2FA63F1", Logger.CONSOLE);
		System.out.println("isSuccess : " + response.isSuccess);
		System.out.println("keyUnderLMK : " + response.keyUnderLMK);
		System.out.println("keyUnderMasterKey : " + response.keyUnderMasterKey);
		System.out.println("kcv : " + response.kcv);
	}
	
	
	public static void importKeyTest() {
		final GenKeyResponse response = hsmService.key().importKey(hsmConfig,KeyType.ZPK, VARIANT.DOUBLE_LEN, "X116A0DBF3E7521D9E392D698AEB20AC6", "U869393350325267BA5CE86B9283A4291", Logger.CONSOLE);
		System.out.println("isSuccess : " + response.isSuccess);
		System.out.println("keyUnderLMK : " + response.keyUnderLMK);
		System.out.println("keyUnderMasterKey : " + response.keyUnderMasterKey);
		System.out.println("kcv : " + response.kcv);

	}
	
	public static void exportKeyTest() {
		final GenKeyResponse response = hsmService.key().exportKey(hsmConfig,KeyType.MK_AC, "U6C58184333A1628A1EF4F0C3B9C41D37", ANSI.DOUBLE_LEN, MasterKeyType.ZMK, "U869393350325267BA5CE86B9283A4291",  Logger.CONSOLE);
		System.out.println("isSuccess : " + response.isSuccess);
		System.out.println("keyUnderLMK : " + response.keyUnderLMK);
		System.out.println("keyUnderMasterKey : " + response.keyUnderMasterKey);
		System.out.println("kcv : " + response.kcv);

	}


	public static void main(String[] args) {
		exportKeyTest();
	}
}
