package ru.zelenyhleb.etk.core.api;

import java.util.function.Function;

public abstract class CopyrightFormat implements Function<String, String> {

	public final String apply(String raw) {
		if (raw.isEmpty() || raw.isBlank())
			return ""; //$NON-NLS-1$
		return format(raw);
	}

	protected abstract String format(String raw);

}
