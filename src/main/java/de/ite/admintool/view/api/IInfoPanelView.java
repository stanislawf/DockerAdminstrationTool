package de.ite.admintool.view.api;

import com.vaadin.ui.HorizontalLayout;

import de.ite.client.model.Container;

public interface IInfoPanelView {

	public void setDisplay(Container container);

	public interface InfoPanelViewListener {
		public void setSelectedContainer(Container container);

		public void startContainer();

		public void stopContainer();

		public void removeContainer();

		public void reinitializeContainer();
	}

	public void addListener(InfoPanelViewListener listener);

	public void setVisible(boolean visible);

	public void displayContainerInfo();

	public void resetContainerInfo(HorizontalLayout layout);
}
