package ru.zelenyhleb.etk.ui.wizards.bundle;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ru.zelenyhleb.etk.core.heuristic.QualifiedName;

public final class ConfigurationPage extends WizardPage implements IPageChangedListener {

	private Text displayName;
	private Text version;
	private Text vendor;
	private Text copyright;
	private final ModifyListener validate = e -> validate();
	private final Supplier<QualifiedName> name;

	protected ConfigurationPage(Supplier<QualifiedName> name) {
		super("Create project"); //$NON-NLS-1$
		this.name = name;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(container);
		Group configuration = new Group(container, SWT.NONE);
		configuration.setText("Bundle configuration"); //$NON-NLS-1$
		GridLayoutFactory.swtDefaults().numColumns(3).equalWidth(true).applyTo(configuration);
		GridDataFactory grab = GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false);
		Label displayNameLabel = new Label(configuration, SWT.NONE);
		displayNameLabel.setText("Bundle name:"); //$NON-NLS-1$
		displayName = new Text(configuration, SWT.BORDER);
		displayName.addModifyListener(validate);
		grab.span(2, 1).applyTo(displayName);
		Label versionLabel = new Label(configuration, SWT.NONE);
		versionLabel.setText("Version:"); //$NON-NLS-1$
		version = new Text(configuration, SWT.BORDER);
		version.addModifyListener(validate);
		grab.span(2, 1).applyTo(version);
		Label vendorLabel = new Label(configuration, SWT.NONE);
		vendorLabel.setText("Vendor:"); //$NON-NLS-1$
		vendor = new Text(configuration, SWT.BORDER);
		vendor.addModifyListener(validate);
		grab.span(2, 1).applyTo(vendor);
		Label copyrightLabel = new Label(configuration, SWT.NONE);
		copyrightLabel.setText("Copyright:"); //$NON-NLS-1$
		copyright = new Text(configuration, SWT.MULTI | SWT.BORDER);
		grab.grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(configuration);
		grab.applyTo(copyright);
		setControl(container);
		validate();
		Optional.of(getContainer()) //
				.filter(IPageChangeProvider.class::isInstance) //
				.map(IPageChangeProvider.class::cast) //
				.ifPresent(p -> p.addPageChangedListener(this));
	}

	public String displayName() {
		return extractText(displayName, name.get().name());
	}

	public String version() {
		return extractText(version, "0.1.0"); //$NON-NLS-1$
	}

	public String vendor() {
		return extractText(vendor, name.get().vendor());
	}

	public String copyright() {
		return extractText(copyright, ""); //$NON-NLS-1$
	}

	private String extractText(Text text, String other) {
		return Optional.ofNullable(text).map(Text::getText).filter(s -> !s.isEmpty()).orElse(other);
	}

	private void validate() {
		setErrorMessage(null);
		if (displayName.getText().isEmpty() || displayName.getText().isBlank()) {
			setErrorMessage("Name has to be non-empty"); //$NON-NLS-1$
			setPageComplete(false);
			return;
		}
		if (vendor.getText().isEmpty() || vendor.getText().isBlank()) {
			setErrorMessage("Vendor has to be non-empty"); //$NON-NLS-1$
			setPageComplete(false);
			return;
		}
		if (version.getText().isEmpty() || version.getText().isBlank()) {
			setErrorMessage("Version is empty and will be automatically set to 0.1.0.qualifier"); //$NON-NLS-1$
		}
		setPageComplete(true);
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		if(event.getSelectedPage().getClass().equals(this.getClass())) {
			displayName.setText(name.get().name());
			version.setText(version());
			vendor.setText(vendor());			
		}
	}

}
