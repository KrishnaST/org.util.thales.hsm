
module org.util.thales.hsm {

	requires transitive org.util.nanolog;
	
	exports org.util.hsm.api;
	exports org.util.hsm.api.model;
	exports org.util.hsm.api.constants;
	
	provides org.util.hsm.api.HSMService with org.util.hsm.thales.ThalesHSMService;
}