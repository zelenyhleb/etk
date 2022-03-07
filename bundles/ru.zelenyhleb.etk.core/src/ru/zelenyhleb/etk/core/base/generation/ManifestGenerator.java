package ru.zelenyhleb.etk.core.base.generation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.Generator;

public final class ManifestGenerator implements Generator {

	@Override
	public void generate(IProject project, BundleConfiguration configuration, IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public String name() {
		return "MANIFEST.MF"; //$NON-NLS-1$
	}

	@Override
	public boolean enabledByDefault() {
		return true;
	}

}
