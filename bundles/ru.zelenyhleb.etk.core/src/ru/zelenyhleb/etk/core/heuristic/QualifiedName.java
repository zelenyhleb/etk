package ru.zelenyhleb.etk.core.heuristic;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class QualifiedName {

	private final String[] segments;
	private final DefaultConfiguration configuration;

	public QualifiedName(String qualified) {
		this.segments = qualified.split("\\."); //$NON-NLS-1$
		this.configuration = new DefaultConfiguration();
	}

	public String qualified() {
		return Stream.of(segments).collect(Collectors.joining(".")); //$NON-NLS-1$
	}

	public String name() {
		if (segments.length <= 1)
			return configuration.display();
		else if (segments.length < 3)
			return Stream.of(segments).map(this::capitalize).collect(Collectors.joining(" ")); //$NON-NLS-1$
		else
			return Stream.of(Arrays.copyOfRange(segments, 2, segments.length)) //
					.map(this::capitalize) //
					.collect(Collectors.joining(" ")); //$NON-NLS-1$
	}

	public String vendor() {
		if (segments.length < 2)
			return configuration.vendor();
		else
			return capitalize(segments[1]);
	}

	private String capitalize(String raw) {
		String first = String.valueOf(raw.charAt(0));
		return raw.replaceFirst(first, first.toUpperCase());
	}

}
