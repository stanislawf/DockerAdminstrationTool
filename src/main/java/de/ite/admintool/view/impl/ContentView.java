package de.ite.admintool.view.impl;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.ite.admintool.view.presenter.ContainerTabPreseneter;
import de.ite.admintool.view.presenter.ImageTabPresenter;
import de.ite.admintool.view.presenter.InfoPanelPresenter;

public class ContentView {

	private HorizontalLayout content;

	private ContainerTabPreseneter containerPresenter;

	public ContentView() {
		content = new HorizontalLayout();
		content.setStyleName("flexwrap");

		TabSheet tabs = new TabSheet();
		tabs.setHeight(480.0f, Unit.PIXELS);
		tabs.setWidth(550.0f, Unit.PIXELS);
		tabs.setStyleName("tabs");
		tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabs.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		tabs.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);

		ImageTabView imageTabView = new ImageTabView();
		ImageTabPresenter imagePresenter = new ImageTabPresenter(imageTabView);
		tabs.addTab(imageTabView.getImageTab()).setCaption("Images");

		InfoPanelView infoPanel = new InfoPanelView();
		InfoPanelPresenter infoPresenter = new InfoPanelPresenter(infoPanel);

		ContainerTabView containerTabView = new ContainerTabView();
		containerTabView.addListener(infoPresenter);

		containerPresenter = new ContainerTabPreseneter(containerTabView);
		tabs.addTab(containerTabView.getContainerTab(), "Containers");

		tabs.addSelectedTabChangeListener(new SelectedTabChangeListener() {

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				TabSheet tabSheet = event.getTabSheet();
				VerticalLayout tab = (VerticalLayout) tabSheet.getSelectedTab();
				String tabName = tabSheet.getTab(tab).getCaption();
				if (tabName.equals("Containers")) {
					containerPresenter.loadContainers();
				}
				if (tabName.equals("Images")) {
					infoPresenter.resetContainerInfo(content);
				}
			}
		});

		content.addComponent(tabs);

		content.addComponent(infoPanel.getInfoPanel());
	}

	public HorizontalLayout getContent() {
		return content;
	}

}
