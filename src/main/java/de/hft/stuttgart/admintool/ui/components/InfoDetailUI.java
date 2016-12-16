package de.hft.stuttgart.admintool.ui.components;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class InfoDetailUI {

	private VerticalLayout detailInfo;

	public InfoDetailUI() {
		detailInfo = new VerticalLayout();
		detailInfo.setWidth(500, Unit.PIXELS);
		detailInfo.setMargin(true);
		detailInfo.setSpacing(true);
		detailInfo.addComponent(new Label("HUHU", ContentMode.HTML));
	}

	public VerticalLayout getDetailInfo() {
		return detailInfo;
	}


}
