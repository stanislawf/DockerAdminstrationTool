package de.ite.admintool.view.impl;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HeaderView {

	private VerticalLayout header;

	public HeaderView() {
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
