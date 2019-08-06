
module org.util.thales.hsm {

	requires transitive org.util.nanolog;

	exports org.util.hsm.api;
	exports org.util.hsm.api.util;
	exports org.util.hsm.api.model;
	exports org.util.hsm.api.constants;
	
	uses org.util.hsm.api.HSMService;

	provides org.util.hsm.api.HSMService with org.util.hsm.thales.ThalesHSMService, org.util.hsm.safenet.SafeNetHSMService;
}
