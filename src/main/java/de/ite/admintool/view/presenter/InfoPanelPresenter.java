package de.ite.admintool.view.presenter;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import de.ite.admintool.view.api.IInfoPanelView;
import de.ite.admintool.view.api.IInfoPanelView.InfoPanelViewListener;
import de.ite.client.dockercmds.api.IContainerCommands;
import de.ite.client.dockercmds.impl.ContainerCommands;
import de.ite.client.model.Container;

public class InfoPanelPresenter implements InfoPanelViewListener {

	private Container selectedContainer;
	private IInfoPanelView panelView;
	private IContainerCommands containerCommands;

	public InfoPanelPresenter(IInfoPanelView view) {
		this.panelView = view;
		this.panelView.addListener(this);
		this.containerCommands = new ContainerCommands();
	}

	@Override
	public void setSelectedContainer(Container container) {
		this.selectedContainer = container;
		this.panelView.setVisible(true);
		this.panelView.setDisplay(container);
		this.panelView.displayContainerInfo();
	}

	public void resetContainerInfo(HorizontalLayout layout) {
		this.panelView.resetContainerInfo(layout);
		this.panelView.setVisible(false);
		this.panelView.setDisplay(null);
	}

	@Override
	public void startContainer() {
		containerCommands.startContainer(selectedContainer.getContainerName());
		if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
			Notification.show("Container " + selectedContainer.getContainerName() + " has been successfully started");
			redisplayInfo();
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
			Notification.show("Container " + selectedContainer.getContainerName() + " is already running",
					Type.TRAY_NOTIFICATION);
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NOT_FOUND) {
			Notification.show("Container " + selectedContainer.getContainerName() + " is not available",
					Type.TRAY_NOTIFICATION);
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			Notification.show("The connection to the server is lost. Please try again later", Type.ERROR_MESSAGE);
		}

	}

	@Override
	public void stopContainer() {
		containerCommands.stopContainer(selectedContainer.getContainerName());
		if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
			Notification.show("Container " + selectedContainer.getContainerName() + " has been successfully stopped");
			redisplayInfo();
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
			Notification.show("Container " + selectedContainer.getContainerName() + " is already stopped",
					Type.TRAY_NOTIFICATION);
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NOT_FOUND) {
			Notification.show("Container " + selectedContainer.getContainerName() + " is not available",
					Type.TRAY_NOTIFICATION);
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			Notification.show("The connection to the server is lost. Please try again later", Type.ERROR_MESSAGE);
		}
	}

	@Override
	public void removeContainer() {
		containerCommands.deleteContainer(selectedContainer.getContainerName());
		if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
			Page.getCurrent().reload();
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NOT_FOUND) {
			Notification.show("Container " + selectedContainer.getContainerName() + " is not available",
					Type.TRAY_NOTIFICATION);
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			Notification.show("The connection to the server is lost. Please try again later", Type.ERROR_MESSAGE);
		}
	}

	@Override
	public void reinitializeContainer() {
		containerCommands.deleteContainer(selectedContainer.getContainerName());
		if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
			containerCommands.createContainer(selectedContainer.getImage(), selectedContainer.getContainerName(),
					getPort());
			if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				redisplayInfo();
				Notification.show(
						"Container " + selectedContainer.getContainerName() + " has been successfully reinitialized");
			}
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			Notification.show("The connection to the server is lost. Please try again later", Type.ERROR_MESSAGE);
		}
	}

	public void redisplayInfo() {
		List<Container> allContainers = containerCommands.getAllContainers();
		for (Container container : allContainers) {
			if (container.getId().equals(selectedContainer.getId())
					|| (container.getContainerName().equals(selectedContainer.getContainerName()))) {
				setSelectedContainer(container);
			}
		}
	}

	private String getPort() {
		List<Object> ports = selectedContainer.getPorts();
		String port = null;
		HashMap<String, Object> portInfos = (HashMap<String, Object>) ports.get(0);
		port = portInfos.get("PublicPort").toString();
		return port;
	}

}
