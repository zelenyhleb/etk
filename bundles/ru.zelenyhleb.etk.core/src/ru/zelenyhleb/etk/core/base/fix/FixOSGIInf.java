package ru.zelenyhleb.etk.core.base.fix;

import java.util.function.Consumer;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;

public final class FixOSGIInf implements Consumer<IProject> {

	@Override
	public void accept(IProject project) {
		fixFolderStructure(project);
	}

	private void fixFolderStructure(IProject root) {
		IFolder osgiInf = root.getFolder("OSGI-INF"); //$NON-NLS-1$
		createFolder(osgiInf);
		IFolder l10n = osgiInf.getFolder("l10n"); //$NON-NLS-1$
		createFolder(l10n);
		l10n.getFile("bundle.properties"); //$NON-NLS-1$
	}
	
	

	private void createFolder(IFolder l10n) {
		if (!l10n.exists()) {
			try {
				l10n.create(false, true, new NullProgressMonitor());
			} catch (CoreException e) {
				Platform.getLog(getClass()).log(e.getStatus());
			}
		}
	}

}
