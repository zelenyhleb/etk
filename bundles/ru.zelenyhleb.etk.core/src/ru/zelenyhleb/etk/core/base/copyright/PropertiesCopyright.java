package ru.zelenyhleb.etk.core.base.copyright;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.zelenyhleb.etk.core.api.CopyrightFormat;

public final class PropertiesCopyright extends CopyrightFormat {

	@Override
	protected String format(String raw) {
		StringBuilder builder = new StringBuilder();
		builder.append("###############################################################################\n"); //$NON-NLS-1$
		builder.append(Stream.of(raw.split("\n")).map(line -> "# " + line).collect(Collectors.joining("\n"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		builder.append("\n###############################################################################\n"); //$NON-NLS-1$
		return builder.toString();
	}

}
