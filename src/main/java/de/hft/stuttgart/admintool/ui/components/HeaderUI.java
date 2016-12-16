package de.hft.stuttgart.admintool.ui.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HeaderUI {

	private VerticalLayout header;

	public HeaderUI() {
		header = new VerticalLayout();

		Label title = new Label("Docker Administration Tool");
		title.setStyleName("header");
		title.setResponsive(true);
		header.addComponent(title);

	}

	public VerticalLayout getTitlebar() {
		return header;
	}
}
