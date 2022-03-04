package ru.zelenyhleb.etk.ui.wizards.bundle;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.Generator;
import ru.zelenyhleb.etk.core.base.BaseBundleConfiguration;
import ru.zelenyhleb.etk.core.base.CreateProjectOperation;
import ru.zelenyhleb.etk.core.base.generation.L10NGenerator;
import ru.zelenyhleb.etk.core.base.generation.SettingsGenerator;

public final class CreateBundleWizard extends Wizard implements INewWizard {

	private final LocationPage locationPage = new LocationPage();

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// Do nothing
	}

	@Override
	public boolean performFinish() {
		BundleConfiguration configuration = new BaseBundleConfiguration(locationPage.getProjectName(), "name", "vendor", "copyright", "version"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
		IPath locationPath = locationPage.getLocationPath();
		Job.create("cet", new CreateProjectOperation(configuration, locationPath, generators())).schedule(); //$NON-NLS-1$
		return true;
	}

	private List<Generator> generators() {
		return Arrays.asList(new SettingsGenerator(), new L10NGenerator());
	}

	@Override
	public void addPages() {
		addPage(locationPage);
	}

}
