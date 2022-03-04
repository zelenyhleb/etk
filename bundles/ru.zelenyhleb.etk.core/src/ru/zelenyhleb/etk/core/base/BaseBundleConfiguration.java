package ru.zelenyhleb.etk.core.base;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.CopyrightFormat;

public final class BaseBundleConfiguration implements BundleConfiguration {

	private final String fullName;
	private final String vendor;
	private final String displayName;
	private final String copyright;
	private final String version;

	public BaseBundleConfiguration(String fullName, String displayName, String vendor, String copyright,
			String version) {
		this.fullName = fullName;
		this.vendor = vendor;
		this.displayName = displayName;
		this.copyright = copyright;
		this.version = version;
	}

	@Override
	public String display() {
		return displayName;
	}

	@Override
	public String vendor() {
		return vendor;
	}

	@Override
	public String full() {
		return fullName;
	}

	@Override
	public String version() {
		return version + ".qualifier"; //$NON-NLS-1$
	}

	@Override
	public String copyright(CopyrightFormat format) {
		return format.apply(copyright);
	}

}
