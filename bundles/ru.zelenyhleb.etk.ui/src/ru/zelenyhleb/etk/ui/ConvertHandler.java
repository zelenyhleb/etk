package ru.zelenyhleb.etk.ui;

import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import ru.zelenyhleb.etk.core.base.ConvertProject;

public final class ConvertHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Optional.ofNullable(HandlerUtil.getCurrentSelection(event)) //
				.filter(IStructuredSelection.class::isInstance) //
				.map(IStructuredSelection.class::cast) //
				.filter(selection -> !selection.isEmpty())//
				.map(IStructuredSelection::getFirstElement) //
				.filter(IProject.class::isInstance) //
				.map(IProject.class::cast) //
				.ifPresent(new ConvertProject());
		return null;
	}

}
