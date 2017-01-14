package de.ite.admintool.view.presenter;

import java.util.List;

import de.ite.admintool.view.api.IContainerTabView;
import de.ite.client.dockercmds.api.IContainerCommands;
import de.ite.client.dockercmds.impl.ContainerCommands;
import de.ite.client.model.Container;

public class ContainerTabPreseneter {

	private IContainerTabView view;
	private IContainerCommands containerCommands;

	public ContainerTabPreseneter(IContainerTabView view) {
		this.view = view;

		containerCommands = new ContainerCommands();
		loadContainers();
	}

	public void loadContainers() {
		List<Container> containers = containerCommands.getAllContainers();
		this.view.setDisplay(containers);
	}

}
