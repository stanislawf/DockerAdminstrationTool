package de.hft.stuttgart.admintool.ui.components;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

public class InfoUI {

	private Panel infoPanel;

	public InfoUI() {
		infoPanel = new Panel("Information");
		infoPanel.setHeight(480.0f, Unit.PIXELS);
		infoPanel.setWidth(650.0f, Unit.PIXELS);
		infoPanel.addStyleName(ValoTheme.LAYOUT_CARD);

		InfoDetailUI detailInfo = new InfoDetailUI();
		infoPanel.setContent(detailInfo.getDetailInfo());
	}

	public Panel getInfoPanel() {
		return infoPanel;
	}
}
