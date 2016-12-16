package de.hft.stuttgart.admintool.ui.components;

import java.util.List;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.hft.client.dockercmds.api.IImageCommands;
import de.hft.client.dockercmds.impl.ImageCommands;
import de.hft.client.model.Image;

public class ImageTabUI {

	private VerticalLayout imageTab;

	public ImageTabUI() {
		imageTab = new VerticalLayout();
		imageTab.setMargin(true);
		imageTab.setSpacing(true);
		initTable();
	}

	private void initTable() {
		Table table = new Table();
		table.setSizeFull();

		// Define two columns for the built-in container
		table.addContainerProperty("Image-Name", String.class, null);
		IImageCommands imageCommands = new ImageCommands();
		List<Image> images = imageCommands.listImages();

		for (int index = 0; index < images.size(); index++) {
			Image image = images.get(index);
			table.addItem(new Object[] { image.getImageName() }, index);
		}
		imageTab.addComponent(table);
	}

	public VerticalLayout getImageTab() {
		return imageTab;
	}
}
