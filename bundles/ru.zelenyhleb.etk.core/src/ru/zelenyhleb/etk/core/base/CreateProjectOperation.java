package ru.zelenyhleb.etk.core.base;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import ru.zelenyhleb.etk.core.api.BundleConfiguration;
import ru.zelenyhleb.etk.core.api.Generator;

public final class CreateProjectOperation implements ICoreRunnable {

	private final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	private final BundleConfiguration configuration;
	private final List<Generator> generators;
	private final IPath location;

	public CreateProjectOperation(BundleConfiguration configuration, IPath location, List<Generator> generators) {
		this.configuration = configuration;
		this.generators = generators;
		this.location = location;
	}

	@Override
	public void run(IProgressMonitor monitor) throws CoreException {
		IProject project = root.getProject(configuration.full());
		IProjectDescription description = root.getWorkspace().newProjectDescription(configuration.full());
		description.setNatureIds(natures());
		description.setBuildSpec(specs(description));
		description.setLocation(location.append(configuration.full()));
		project.create(description, monitor);
		project.open(monitor);
		createContent(project, monitor);
	}

	private void createContent(IProject project, IProgressMonitor monitor) {
		generators.forEach(g -> g.generate(project, configuration, monitor));
	}

	private String[] natures() {
		return new String[] { "org.eclipse.pde.PluginNature", "org.eclipse.jdt.core.javanature" }; //$NON-NLS-1$//$NON-NLS-2$
	}

	private ICommand[] specs(IProjectDescription description) {
		return Stream.of("org.eclipse.jdt.core.javabuilder", //$NON-NLS-1$
				"org.eclipse.pde.ManifestBuilder", //$NON-NLS-1$
				"org.eclipse.pde.SchemaBuilder") //$NON-NLS-1$
				.map(id -> command(description, id)) //
				.collect(Collectors.toList()) //
				.toArray(new ICommand[0]);
	}

	private ICommand command(IProjectDescription description, String id) {
		ICommand command = description.newCommand();
		command.setBuilderName(id);
		return command;
	}
}
