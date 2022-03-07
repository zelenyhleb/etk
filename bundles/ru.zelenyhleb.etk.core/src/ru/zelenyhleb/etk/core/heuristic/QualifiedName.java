package ru.zelenyhleb.etk.core.heuristic;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class QualifiedName {

	private static final String SPACE = " "; //$NON-NLS-1$
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
			return Stream.of(segments).map(this::capitalize).collect(Collectors.joining(SPACE));
		else
			return Stream.of(Arrays.copyOfRange(segments, 2, segments.length)) //
					.map(this::capitalize) //
					.collect(Collectors.joining(SPACE));
	}

	public String vendor() {
		if (segments.length >= 2) {
			return capitalize(segments[1]) //
					.concat(SPACE) //
					.concat(new VendorSuffix().apply(segments[0]));
		} else {
			return configuration.vendor();
		}
	}

	private String capitalize(String raw) {
		String first = String.valueOf(raw.charAt(0));
		return raw.replaceFirst(first, first.toUpperCase());
	}

}
