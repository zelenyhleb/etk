package ru.zelenyhleb.etk.ui.wizards.bundle;

import java.util.Optional;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public final class ConfigurationPage extends WizardPage {

	private Text displayName;
	private Text version;
	private Text vendor;
	private Text copyright;

	protected ConfigurationPage() {
		super("Create project"); //$NON-NLS-1$
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		displayName = new Text(container, SWT.NONE);
		version = new Text(container, SWT.NONE);
		vendor = new Text(container, SWT.NONE);
		copyright = new Text(container, SWT.MULTI);
		setControl(container);
	}

	public String displayName() {
		return extractText("name", displayName); //$NON-NLS-1$
	}

	public String version() {
		return extractText("0.1.0", version); //$NON-NLS-1$
	}

	public String vendor() {
		return extractText("you", vendor); //$NON-NLS-1$
	}

	public String copyright() {
		return extractText("", copyright); //$NON-NLS-1$
	}

	private String extractText(String other, Text text) {
		return Optional.ofNullable(text).map(Text::getText).orElse(other);
	}

}
