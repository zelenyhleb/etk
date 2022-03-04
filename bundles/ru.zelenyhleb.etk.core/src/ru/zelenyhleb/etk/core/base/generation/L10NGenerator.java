package ru.zelenyhleb.etk.core.base.generation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.Generator;
import ru.zelenyhleb.etk.core.base.copyright.PropertiesCopyright;

public final class L10NGenerator implements Generator {

	@Override
	public void generate(IProject project, BundleConfiguration configuration, IProgressMonitor monitor) {
		try (InputStream stream = new ByteArrayInputStream(content(configuration).getBytes(StandardCharsets.UTF_8))) {
			IFolder osgiInf = project.getFolder("OSGI-INF"); //$NON-NLS-1$
			osgiInf.create(false, true, new NullProgressMonitor());
			IFolder l10n = osgiInf.getFolder("l10n"); //$NON-NLS-1$
			l10n.create(false, true, new NullProgressMonitor());
			IFile file = l10n.getFile("bundle.properties"); //$NON-NLS-1$
			file.create(stream, false, monitor);
		} catch (CoreException e) {
			Platform.getLog(getClass()).log(e.getStatus());
		} catch (IOException e) {
			Platform.getLog(getClass()).error(e.getMessage());
		}
	}

	private String content(BundleConfiguration configuration) {
		return String.format("%sBundle-Vendor = %s\nBundle-Name = %s", //$NON-NLS-1$
				configuration.copyright(new PropertiesCopyright()), configuration.vendor(), configuration.display());
	}

}
