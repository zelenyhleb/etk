package ru.zelenyhleb.etk.ui.wizards.bundle;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import ru.zelenyhleb.etk.core.base.BaseBundleConfiguration;
import ru.zelenyhleb.etk.core.base.CreateProjectOperation;
import ru.zelenyhleb.etk.core.heuristic.QualifiedName;

public final class CreateBundleWizard extends Wizard implements INewWizard {

	private final LocationPage locationPage = new LocationPage();
	private final ConfigurationPage configurationPage = new ConfigurationPage(
			() -> new QualifiedName(locationPage.getProjectName()));
	private final GenerationPage generationPage = new GenerationPage();

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// Do nothing
	}

	@Override
	public boolean performFinish() {
		IPath locationPath = locationPage.getLocationPath();
		Job.create("cet", new CreateProjectOperation(configuration(), locationPath, generationPage.enabledGenerators())).schedule(); //$NON-NLS-1$
		return true;
	}

	private BaseBundleConfiguration configuration() {
		return new BaseBundleConfiguration( //
				locationPage.getProjectName(), //
				configurationPage.displayName(), //
				configurationPage.vendor(), //
				configurationPage.copyright(), //
				configurationPage.version());
	}

	@Override
	public void addPages() {
		addPage(locationPage);
		addPage(configurationPage);
		addPage(generationPage);
	}

	@Override
	public boolean canFinish() {
		return locationPage.isPageComplete() && configurationPage.isPageComplete() && generationPage.isPageComplete();
	}

}
