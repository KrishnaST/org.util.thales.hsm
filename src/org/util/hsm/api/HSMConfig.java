package org.util.hsm.api;


public class HSMConfig {

	public final String host;
	public final int    port;
	public String       minimumPinLength = "04";
	public String       maximumPinLength = "12";
	public String       decTab           = "0123456789012345";
	public int          lengthOfPinLMK   = 5;
	
	public HSMConfig(final String host, final int port) {
		this.host = host;
		this.port = port;
	}

	public static final Builder newBuilder(final String host, final int port) {
		return new Builder(host, port);
	}
	
	public static final Builder newBuilder(final HSMConfig hsmConfig) {
		return new Builder(hsmConfig);
	}
	
	public static class Builder {
		
		private final HSMConfig hsmConfig;
		
		private Builder(final String host, final int port) {
			hsmConfig = new HSMConfig(host, port);
		}
		
		private Builder(final HSMConfig hsmConfig) {
			this.hsmConfig = new HSMConfig(hsmConfig.host, hsmConfig.port);
			this.hsmConfig.decTab = hsmConfig.decTab;
			this.hsmConfig.lengthOfPinLMK = hsmConfig.lengthOfPinLMK;
			this.hsmConfig.maximumPinLength = hsmConfig.maximumPinLength;
			this.hsmConfig.minimumPinLength = hsmConfig.minimumPinLength;
		}
		
		public final Builder withMinimumPinLength(final String minimumPinLength) {
			hsmConfig.minimumPinLength = minimumPinLength;
			return this;
		}
		
		public final Builder withMaximumPinLength(final String maximumPinLength) {
			hsmConfig.maximumPinLength = maximumPinLength;
			return this;
		}
		
		public final Builder withDecimalizationTable(final String decTab) {
			hsmConfig.decTab = decTab;
			return this;
		}
		
		public final Builder withLengthOfPinLMK(final int lengthOfPinLMK) {
			hsmConfig.lengthOfPinLMK = lengthOfPinLMK;
			return this;
		}
		
		public final HSMConfig build() {
			return hsmConfig;
		}
	}
	
	
}
