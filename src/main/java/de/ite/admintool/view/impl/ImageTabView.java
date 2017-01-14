package de.ite.admintool.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.ite.admintool.view.api.IImageTabView;
import de.ite.admintool.view.api.IImageTabView.ImageTabViewListener;
import de.ite.client.model.Image;

@SuppressWarnings("serial")
public class ImageTabView extends CustomComponent implements IImageTabView, ImageTabViewListener {

	private Table imageTable;
	private List<ImageTabViewListener> listeners = new ArrayList<ImageTabViewListener>();
	private VerticalLayout imageTab;
	private PopupView popup;
	private PopupTextFieldContent popupContent;

	public ImageTabView() {

		imageTab = new VerticalLayout();
		imageTab.setMargin(true);
		imageTab.setSpacing(true);

		imageTable = new Table();
		imageTable.setSizeFull();

		imageTable.addContainerProperty("Image-Name", String.class, null);

		popupContent = new PopupTextFieldContent();
		popup = new PopupView("", popupContent);
		popup.setHideOnMouseOut(false);
		popup.setSizeFull();

		imageTab.addComponent(popup);
		imageTab.addComponent(imageTable);
	}

	@Override
	public void setDisplay(List<Image> images) {
		if (images != null) {
			fillImageTable(images);
			addButtonColumn(images);
		}
	}

	private void addButtonColumn(List<Image> images) {
		imageTable.addGeneratedColumn("", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Button createContainer = new Button("Create Container");
				createContainer.setStyleName("controlButtons");
				createContainer.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						int index = (int) itemId;
						Image selectedImage = images.get(index);
						setSelectedImage(selectedImage);
						System.out.println("test");
						popup.setPopupVisible(true);
					}
				});
				return createContainer;
			}
		});
	}

	private void fillImageTable(List<Image> images) {
		for (int index = 0; index < images.size(); index++) {
			Image image = images.get(index);
			imageTable.addItem(new Object[] { image.getImageName() }, index);
		}
	}

	@Override
	public void addListener(ImageTabViewListener listener) {
		listeners.add(listener);
	}

	@Override
	public void createContainer(String name, String port) throws InterruptedException {
		for (ImageTabViewListener listener : listeners) {
			System.out.println(listener.getClass());
			listener.createContainer(name, port);
		}
	}

	@Override
	public void setSelectedImage(Image image) {
		for (ImageTabViewListener listener : listeners) {
			listener.setSelectedImage(image);
		}
	}

	public VerticalLayout getImageTab() {
		return imageTab;
	}

	private class CreateContainerClickListener implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			Component component = event.getComponent();
			System.out.println(component.getCaption());
			HasComponents parent2 = component.getParent();
			System.out.println(popupContent.getContainerName());
			try {
				createContainer(popupContent.getContainerName(), popupContent.getPort());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			popupContent.resetFields();
			popup.setPopupVisible(false);
		}
	}

	private class PopupTextFieldContent extends VerticalLayout {

		private TextField containerName;
		private TextField port;

		public PopupTextFieldContent() {
			containerName = new TextField("Please provide a container name:");
			addComponent(containerName);
			port = new TextField("Please provide a port:");
			addComponent(port);
			Button create = new Button("Create");
			create.addClickListener(new CreateContainerClickListener());
			addComponent(create);
			setWidth(250f, Unit.PIXELS);
			setHeight(250f, Unit.PIXELS);
		}

		public String getPort() {
			return port.getValue();
		}


		public String getContainerName() {
			return containerName.getValue();
		}

		public void resetFields() {
			containerName = new TextField("Please provide a container name:");
			port = new TextField("Please provide a port:");
		}

	}

}
