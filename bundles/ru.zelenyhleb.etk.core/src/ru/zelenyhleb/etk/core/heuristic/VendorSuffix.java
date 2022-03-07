package ru.zelenyhleb.etk.core.heuristic;

import java.util.function.Function;

public final class VendorSuffix implements Function<String, String> {

	@Override
	public String apply(String domain) {
		switch (domain) {
		case "de": //$NON-NLS-1$
			return "AG"; //$NON-NLS-1$
		default:
			return "Ltd."; //$NON-NLS-1$
		}
	}

}
