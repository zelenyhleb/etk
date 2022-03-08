/*******************************************************************************
 * Copyright (c) 2022 Nikifor Fedorov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Nikifor Fedorov - initial API and implementation
 *******************************************************************************/
package ru.zelenyhleb.etk.ui.templates;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.ui.templates.AbstractTemplateSection;
import org.osgi.framework.Constants;

import ru.zelenyhleb.etk.core.base.Formatters;
import ru.zelenyhleb.etk.core.heuristic.QualifiedName;
import ru.zelenyhleb.etk.ui.wizards.bundle.ConfigurationPage;

public final class BaseBundleTemplate extends AbstractTemplateSection {

	private final ConfigurationPage configuration;
	private final Supplier<String> javaVersion;

	@SuppressWarnings("deprecation")
	public BaseBundleTemplate(Supplier<QualifiedName> name) {
		configuration = new ConfigurationPage(name);
		this.javaVersion = () -> getManifestHeader(Constants.BUNDLE_REQUIREDEXECUTIONENVIRONMENT).split("-")[1]; //$NON-NLS-1$
	}

	@Override
	public String getLabel() {
		return "Generate a base bundle"; //$NON-NLS-1$
	}

	@Override
	public WizardPage getPage(int pageIndex) {
		return configuration;
	}

	@Override
	public int getPageCount() {
		return 1;
	}

	@Override
	public String getUsedExtensionPoint() {
		return null;
	}

	@Override
	public IPluginReference[] getDependencies(String schemaVersion) {
		return new IPluginReference[] {};
	}

	@Override
	public void addPages(Wizard wizard) {
		wizard.addPage(configuration);
	}

	@Override
	public URL getTemplateLocation() {
		try {
			URL find = FileLocator.find(Platform.getBundle("ru.zelenyhleb.etk.templates"), new Path("base")); //$NON-NLS-1$ //$NON-NLS-2$
			URL fileURL = FileLocator.toFileURL(find);
			return fileURL;
		} catch (IOException e) {
			Platform.getLog(getClass()).error(e.getMessage());
		}
		return null;
	}

	@Override
	public String getReplacementString(String fileName, String key) {
		if (new BaseKeys().name().equals(key)) {
			return configuration.displayName();
		} else if (new BaseKeys().vendor().equals(key)) {
			return configuration.vendor();
		} else if (new BaseKeys().copyright().equals(key)) {
			return new Formatters().copyright(extractExtension(fileName)).apply(configuration.copyright());
		} else if (new BaseKeys().javaVersion().equals(key)) {
			return javaVersion.get();
		}
		return super.getReplacementString(fileName, key);
	}

	@Override
	public String[] getNewFiles() {
		return new String[] { "OSGI-INF/" }; //$NON-NLS-1$
	}

	@Override
	protected ResourceBundle getPluginResourceBundle() {
		return null;
	}

	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		setManifestHeader(Constants.BUNDLE_NAME, "%Bundle-Name"); //$NON-NLS-1$
		setManifestHeader(Constants.BUNDLE_VENDOR, "%Bundle-Vendor"); //$NON-NLS-1$
		setManifestHeader(Constants.BUNDLE_VERSION, new Formatters().version("MF").apply(configuration.version())); //$NON-NLS-1$
	}

	private String extractExtension(String fileName) {
		String[] split = fileName.split("\\."); //$NON-NLS-1$
		return split[split.length - 1];
	}

}