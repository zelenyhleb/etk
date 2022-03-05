package ru.zelenyhleb.etk.core.heuristic;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.CopyrightFormat;

public final class DefaultConfiguration implements BundleConfiguration {

	@Override
	public String display() {
		return "Bundle"; //$NON-NLS-1$
	}

	@Override
	public String vendor() {
		return "You"; //$NON-NLS-1$
	}

	@Override
	public String full() {
		return "com.example.bundle"; //$NON-NLS-1$
	}

	@Override
	public String version() {
		return "0.1.0"; //$NON-NLS-1$
	}

	@Override
	public String copyright(CopyrightFormat format) {
		return ""; //$NON-NLS-1$
	}
}
