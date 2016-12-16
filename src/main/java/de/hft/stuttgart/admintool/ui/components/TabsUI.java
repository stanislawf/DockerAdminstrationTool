package de.hft.stuttgart.admintool.ui.components;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

public class TabsUI {

	private TabSheet tabs;

	public TabsUI() {
		tabs = new TabSheet();
		tabs.setHeight(480.0f, Unit.PIXELS);
		tabs.setWidth(550.0f, Unit.PIXELS);
		tabs.setStyleName("tabs");
		tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabs.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		tabs.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);

		ImageTabUI imageTab = new ImageTabUI();
		tabs.addTab(imageTab.getImageTab(), "Images");

		ContainerTabUI containerTab = new ContainerTabUI();
		tabs.addTab(containerTab.getContainerTab(), "Containers");
	}

	public TabSheet getTabs() {
		return tabs;
	}
}
