package ru.zelenyhleb.etk.ui.wizards.bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import ru.zelenyhleb.etk.core.api.Generator;
import ru.zelenyhleb.etk.core.base.generation.Generators;

public final class GenerationPage extends WizardPage implements IPageChangedListener {

	private Map<Button, Generator> checks = new HashMap<>();

	protected GenerationPage() {
		super(GenerationPage.class.getName());
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(container);
		Group checks = new Group(container, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(checks);
		checks.setText("Additional project settings"); //$NON-NLS-1$
		GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(checks);
		generators().forEach(e -> createCheck(checks, e));
		setControl(container);
		Optional.of(getContainer()) //
				.filter(IPageChangeProvider.class::isInstance) //
				.map(IPageChangeProvider.class::cast) //
				.ifPresent(p -> p.addPageChangedListener(this));
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		if (event.getSelectedPage().getClass().equals(GenerationPage.class)) {
			setPageComplete(true);
		}
	}

	public List<Generator> enabledGenerators() {
		return checks.entrySet().stream() //
				.filter(e -> e.getKey().getSelection()) //
				.map(Map.Entry::getValue) //
				.collect(Collectors.toList());
	}

	private void createCheck(Composite parent, Generator entry) {
		Button check = new Button(parent, SWT.CHECK);
		check.setSelection(entry.enabledByDefault());
		check.setText(String.format("Generate %s", entry.name())); //$NON-NLS-1$
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(check);
		checks.put(check, entry);
	}

	private List<Generator> generators() {
		return new Generators().get();
	}

}
