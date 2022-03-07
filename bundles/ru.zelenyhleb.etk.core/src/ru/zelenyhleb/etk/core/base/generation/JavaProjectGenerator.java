package ru.zelenyhleb.etk.core.base.generation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.Generator;

public final class JavaProjectGenerator implements Generator {

	@Override
	public void generate(IProject project, BundleConfiguration configuration, IProgressMonitor monitor) {
		try {
			Bundle bundle = Platform.getBundle("ru.zelenyhleb.etk.templates"); //$NON-NLS-1$
			String path = FileLocator.toFileURL(FileLocator.resolve(FileLocator.find(bundle, new Path("settings")))) //$NON-NLS-1$
					.getPath();
			File file = new File(path);
			try (FileInputStream input = new FileInputStream(file)) {
				project.getFile(".classpath").create(input, false, monitor); //$NON-NLS-1$
			} catch (CoreException e) {
				Platform.getLog(getClass()).log(e.getStatus());
			}
		} catch (IOException e) {
			Platform.getLog(getClass()).error(e.getMessage());
		}

	}

	@Override
	public String name() {
		return ".classpath file"; //$NON-NLS-1$
	}

	@Override
	public boolean enabledByDefault() {
		return true;
	}

}
