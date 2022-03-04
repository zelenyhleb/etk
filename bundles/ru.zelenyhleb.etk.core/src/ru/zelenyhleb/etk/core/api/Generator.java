package ru.zelenyhleb.etk.core.api;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

public interface Generator {

	void generate(IProject project, BundleConfiguration configuration, IProgressMonitor monitor);

}
