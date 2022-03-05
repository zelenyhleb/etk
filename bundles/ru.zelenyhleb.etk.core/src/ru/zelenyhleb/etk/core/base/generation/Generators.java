package ru.zelenyhleb.etk.core.base.generation;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import ru.zelenyhleb.etk.core.api.Generator;

public final class Generators implements Supplier<List<Generator>> {

	@Override
	public List<Generator> get() {
		List<Generator> generators = new LinkedList<>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("ru.zelenyhleb.etk.generators"); //$NON-NLS-1$
		for (IConfigurationElement element : elements) {
			try {
				generators.add((Generator) element.createExecutableExtension("class")); //$NON-NLS-1$
			} catch (Exception e) {
				Platform.getLog(getClass()).error(e.getMessage());
			} 
		}
		return generators;
	}

}
