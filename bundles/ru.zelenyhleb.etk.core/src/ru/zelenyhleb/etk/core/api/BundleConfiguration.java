package ru.zelenyhleb.etk.core.api;

public interface BundleConfiguration {

	String display();

	String vendor();

	String full();

	String version();

	String copyright(CopyrightFormat format);
	
}
