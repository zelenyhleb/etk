package ru.zelenyhleb.etk.core.base.generation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.Generator;

public final class SettingsGenerator implements Generator {

	@Override
	public void generate(IProject project, BundleConfiguration configuration, IProgressMonitor monitor) {
		IFolder folder = project.getFolder(".settings"); //$NON-NLS-1$
		try {
			folder.create(false, true, monitor);
			Bundle bundle = Platform.getBundle("ru.zelenyhleb.etk.templates"); //$NON-NLS-1$
			String path = FileLocator.toFileURL(FileLocator.resolve(FileLocator.find(bundle, new Path("settings")))) //$NON-NLS-1$
					.getPath();
			File settings = new File(path);
			Stream.of(settings.listFiles()).forEach(file -> settingsFile(folder, file, monitor));
		} catch (CoreException e) {
			Platform.getLog(getClass()).log(e.getStatus());
		} catch (IOException e) {
			Platform.getLog(getClass()).error(e.getMessage());
		}
	}

	private void settingsFile(IFolder folder, File file, IProgressMonitor monitor) {
		IFile destination = folder.getFile(file.getName());
		try (FileInputStream input = new FileInputStream(file)) {
			destination.create(input, false, monitor);
		} catch (IOException e) {
			Platform.getLog(getClass()).error(e.getMessage());
		} catch (CoreException e) {
			Platform.getLog(getClass()).log(e.getStatus());
		}
	}

	@Override
	public String name() {
		return "Default settings"; //$NON-NLS-1$
	}

	@Override
	public boolean enabledByDefault() {
		return true;
	}

}
