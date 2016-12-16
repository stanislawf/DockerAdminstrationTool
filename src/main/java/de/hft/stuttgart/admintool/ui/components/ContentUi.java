package de.hft.stuttgart.admintool.ui.components;

import com.vaadin.ui.VerticalLayout;

public class ContentUi {

	private VerticalLayout content;

	public ContentUi() {
		content = new VerticalLayout();
		content.setStyleName("flexwrap");

		TabsUI tabs = new TabsUI();

		content.addComponent(tabs.getTabs());

		// InfoUI info = new InfoUI();
		// content.addComponent(info.getInfoPanel());

	}

	public VerticalLayout getContent() {
		return content;
	}

}
