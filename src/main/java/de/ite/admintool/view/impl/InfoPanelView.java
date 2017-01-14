package de.ite.admintool.view.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.ite.admintool.view.api.IInfoPanelView;
import de.ite.admintool.view.api.IInfoPanelView.InfoPanelViewListener;
import de.ite.client.model.Container;

public class InfoPanelView implements IInfoPanelView, InfoPanelViewListener {

	private Panel infoPanel;
	private List<InfoPanelViewListener> listeners = new ArrayList<>();
	private Container container;
	private VerticalLayout detailInfo;

	public InfoPanelView() {
		infoPanel = new Panel("Information");
		infoPanel.setHeight(480.0f, Unit.PIXELS);
		infoPanel.setWidth(550.0f, Unit.PIXELS);
		infoPanel.addStyleName(ValoTheme.LAYOUT_CARD);
		infoPanel.setVisible(false);
	}

	@Override
	public void setDisplay(Container container) {
		this.container = container;
	}

	@Override
	public void addListener(InfoPanelViewListener listener) {
		listeners.add(listener);
	}

	@Override
	public void setSelectedContainer(Container container) {
		for (InfoPanelViewListener listener : listeners) {
			listener.setSelectedContainer(container);
		}
	}

	public Panel getInfoPanel() {
		return infoPanel;
	}

	@Override
	public void setVisible(boolean visible) {
		infoPanel.setVisible(visible);
	}

	@Override
	public void displayContainerInfo() {
		detailInfo = new VerticalLayout();
		detailInfo.setWidth(500, Unit.PIXELS);
		detailInfo.setMargin(true);
		detailInfo.setSpacing(true);

		HorizontalLayout containerLayout = new HorizontalLayout();
		Label containerLabel = new Label("Container name:");
		containerLabel.setStyleName("containerName");
		containerLabel.setWidth(200, Unit.PIXELS);
		containerLabel.setHeight(50, Unit.PIXELS);
		containerLayout.addComponent(containerLabel);

		Label containerName = new Label(container.getContainerName());
		containerName.setStyleName("containerName");
		containerName.setWidth(200, Unit.PIXELS);
		containerName.setHeight(50, Unit.PIXELS);
		containerLayout.addComponent(containerName);
		detailInfo.addComponent(containerLayout);

		HorizontalLayout imageLayout = new HorizontalLayout();
		Label imageLabel = new Label("Image name:");
		imageLabel.setStyleName("containerName");
		imageLabel.setWidth(200, Unit.PIXELS);
		imageLabel.setHeight(50, Unit.PIXELS);
		imageLayout.addComponent(imageLabel);

		Label imageName = new Label(container.getImage());
		imageName.setStyleName("containerName");
		imageName.setWidth(200, Unit.PIXELS);
		imageName.setHeight(50, Unit.PIXELS);
		imageLayout.addComponent(imageName);
		detailInfo.addComponent(imageLayout);

		HorizontalLayout createdLayout = new HorizontalLayout();
		Label createdOnLabel = new Label("Created on:");
		createdOnLabel.setStyleName("containerName");
		createdOnLabel.setWidth(200, Unit.PIXELS);
		createdOnLabel.setHeight(50, Unit.PIXELS);
		createdLayout.addComponent(createdOnLabel);

		Date created = new Date((long) container.getCreated() * 1000);
		DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
		String date = f.format(created);

		Label createdOn = new Label(date);
		createdOn.setStyleName("containerName");
		createdOn.setWidth(200, Unit.PIXELS);
		createdOn.setHeight(50, Unit.PIXELS);
		createdLayout.addComponent(createdOn);
		detailInfo.addComponent(createdLayout);

		HorizontalLayout portLayout = new HorizontalLayout();
		Label portLabel = new Label("State:");
		portLabel.setStyleName("containerName");
		portLabel.setWidth(200, Unit.PIXELS);
		portLabel.setHeight(50, Unit.PIXELS);
		portLayout.addComponent(portLabel);

		Label port = new Label(container.getState());
		port.setStyleName("containerName");
		port.setWidth(200, Unit.PIXELS);
		port.setHeight(50, Unit.PIXELS);
		portLayout.addComponent(port);
		detailInfo.addComponent(portLayout);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		Button execute = null;
		if ("exited".equals(container.getState()) || "created".equals(container.getState())) {
			execute = new Button("Start");
		} else if ("running".equals(container.getState())) {
			execute = new Button("Stop");
		}
		execute.addClickListener(new ExecuteClickListener(container));
		execute.addStyleName("controlButtons");

		Button delete = new Button("Delete");
		delete.addStyleName("controlButtons");
		delete.addClickListener(new DeleteClickListener(container));

		Button reinitialize = new Button("Reinitialize Container");
		reinitialize.addStyleName("controlButtons");
		reinitialize.addClickListener(new ReinitializeClickListener(container));
		if ("running".equals(container.getState())) {
			reinitialize.setEnabled(true);
		} else {
			reinitialize.setEnabled(false);
		}

		buttonLayout.addComponents(execute, delete, reinitialize);

		detailInfo.addComponent(buttonLayout);

		infoPanel.setContent(detailInfo);
	}

	@Override
	public void resetContainerInfo(HorizontalLayout layout) {
		Panel newPanel = new Panel("Information");
		newPanel.setHeight(480.0f, Unit.PIXELS);
		newPanel.setWidth(550.0f, Unit.PIXELS);
		newPanel.addStyleName(ValoTheme.LAYOUT_CARD);
		newPanel.setVisible(false);
		layout.replaceComponent(infoPanel, newPanel);
		infoPanel = newPanel;
	}

	@Override
	public void startContainer() {
		for (InfoPanelViewListener listener : listeners) {
			listener.startContainer();
		}
	}

	@Override
	public void stopContainer() {
		for (InfoPanelViewListener listener : listeners) {
			listener.stopContainer();
		}
	}

	@Override
	public void removeContainer() {
		for (InfoPanelViewListener listener : listeners) {
			listener.removeContainer();
		}
	}

	@Override
	public void reinitializeContainer() {
		for (InfoPanelViewListener listener : listeners) {
			listener.reinitializeContainer();
		}
	}

	public class ExecuteClickListener implements ClickListener {

		private Container cn;

		public ExecuteClickListener(Container container) {
			cn = container;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			Button button = event.getButton();
			if (button.getCaption().equals("Start")) {
				startContainer();
				button.setCaption("Stop");
			} else {
				stopContainer();
				button.setCaption("Start");
			}
		}

	}

	public class DeleteClickListener implements ClickListener {

		private Container cn;

		public DeleteClickListener(Container container) {
			cn = container;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			removeContainer();
		}
	}

	public class ReinitializeClickListener implements ClickListener {

		private Container cn;

		public ReinitializeClickListener(Container container) {
			cn = container;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			reinitializeContainer();
		}
	}
}
