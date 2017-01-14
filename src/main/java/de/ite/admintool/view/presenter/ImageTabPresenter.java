package de.ite.admintool.view.presenter;

import java.net.HttpURLConnection;
import java.util.List;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import de.ite.admintool.view.api.IImageTabView;
import de.ite.admintool.view.api.IImageTabView.ImageTabViewListener;
import de.ite.client.dockercmds.api.IContainerCommands;
import de.ite.client.dockercmds.api.IImageCommands;
import de.ite.client.dockercmds.impl.ContainerCommands;
import de.ite.client.dockercmds.impl.ImageCommands;
import de.ite.client.model.Image;

public class ImageTabPresenter implements ImageTabViewListener {

	private IImageTabView view;
	private IImageCommands imageCommands;
	private IContainerCommands containerCommands;
	private Image selectedImage;

	public ImageTabPresenter(IImageTabView view) {
		this.view = view;

		imageCommands = new ImageCommands();
		containerCommands = new ContainerCommands();
		List<Image> images = imageCommands.listImages();

		this.view.setDisplay(images);
		this.view.addListener(this);
	}

	@Override
	public void createContainer(String name, String port) throws InterruptedException {
		containerCommands.createContainer(selectedImage.getImageName(), name, port);
		if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_CREATED) {
			Notification.show("Container " + name + " has been successfully created");
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_CONFLICT) {
			Notification.show("A container with the name " + name + " already exist", Type.WARNING_MESSAGE);
		} else if (containerCommands.getStatusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			Notification.show("The connection to the server is lost. Please try again later", Type.ERROR_MESSAGE);
		}
	}

	@Override
	public void setSelectedImage(Image image) {
		this.selectedImage = image;
	}

}
