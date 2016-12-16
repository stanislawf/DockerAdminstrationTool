package de.hft.stuttgart.admintool.ui.components;

import java.util.List;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hft.client.dockercmds.api.IContainerCommands;
import de.hft.client.dockercmds.impl.ContainerCommands;
import de.hft.client.model.Container;

public class ContainerTabUI {

	private VerticalLayout containerTab;

	public ContainerTabUI() {
		containerTab = new VerticalLayout(new Label("All Container", ContentMode.HTML));
		containerTab.setMargin(true);
		containerTab.setSpacing(true);
		initTable();
	}

	private void initTable() {
		Table table = new Table();
		table.setSizeFull();

		// Define two columns for the built-in container
		table.addContainerProperty("Container-Name", String.class, null);
		IContainerCommands containerCmds = new ContainerCommands();
		List<Container> containers = containerCmds.getAllContainers();

		for (int index = 0; index < containers.size(); index++) {
			Container container = containers.get(index);
			table.addItem(new Object[] { container.getContainerName() }, index);
		}
		containerTab.addComponent(table);
	}

	public VerticalLayout getContainerTab() {
		return containerTab;
	}
}
