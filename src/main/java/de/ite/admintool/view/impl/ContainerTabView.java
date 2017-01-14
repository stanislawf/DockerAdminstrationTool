package de.ite.admintool.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.ite.admintool.view.api.IContainerTabView;
import de.ite.admintool.view.api.IInfoPanelView;
import de.ite.admintool.view.api.IInfoPanelView.InfoPanelViewListener;
import de.ite.client.model.Container;

public class ContainerTabView implements IContainerTabView, InfoPanelViewListener {

	private Table containerTable;
	private VerticalLayout containerTab;
	private List<Container> containers = new ArrayList<>();
	private List<InfoPanelViewListener> listeners = new ArrayList<>();
	private TableValueChangeListener listener;

	public ContainerTabView() {

		containerTab = new VerticalLayout();
		containerTab.setMargin(true);
		containerTab.setSpacing(true);

		containerTable = new Table();
		containerTable.setSizeFull();

		containerTable.addContainerProperty("Container-Name", String.class, "");
		listener = new TableValueChangeListener();
		containerTable.addValueChangeListener(listener);
		containerTab.addComponent(containerTable);
	}

	@Override
	public void setSelectedContainer(Container container) {
		for (InfoPanelViewListener listener : listeners) {
			listener.setSelectedContainer(container);
		}
	}

	@Override
	public void setDisplay(List<Container> containers) {
		if (containers != null) {
			fillContainerTable(containers);
			this.containers = containers;
		}
	}

	private void fillContainerTable(List<Container> containers) {
		containerTable.removeAllItems();
		for (int index = 0; index < containers.size(); index++) {
			Container container = containers.get(index);
			containerTable.addItem(new Object[] { container.getContainerName() }, index);
		}
	}

	@Override
	public void addListener(InfoPanelViewListener listener) {
		listeners.add(listener);
	}

	public VerticalLayout getContainerTab() {
		return containerTab;
	}

	private class TableValueChangeListener implements ValueChangeListener {

		@Override
		public void valueChange(ValueChangeEvent event) {
			if (event.getProperty().getValue() != null) {
				int index = (int) event.getProperty().getValue();
				Container selectedContainer = containers.get(index);
				setSelectedContainer(selectedContainer);
			}
		}
	}

	@Override
	public void startContainer() {
	}

	@Override
	public void stopContainer() {
	}

	@Override
	public void removeContainer() {
	}

	@Override
	public void reinitializeContainer() {
	}
}