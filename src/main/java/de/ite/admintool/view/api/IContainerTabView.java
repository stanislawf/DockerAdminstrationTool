package de.ite.admintool.view.api;

import java.util.List;

import de.ite.admintool.view.api.IInfoPanelView.InfoPanelViewListener;
import de.ite.client.model.Container;

public interface IContainerTabView {

	public void setDisplay(List<Container> container);

	public void addListener(InfoPanelViewListener listener);

}
