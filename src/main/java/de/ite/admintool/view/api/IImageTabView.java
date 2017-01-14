package de.ite.admintool.view.api;

import java.util.List;

import de.ite.client.model.Image;

public interface IImageTabView {

	public void setDisplay(List<Image> images);

	public interface ImageTabViewListener {
		public void createContainer(String name, String port) throws InterruptedException;

		public void setSelectedImage(Image image);
	}

	public void addListener(ImageTabViewListener listener);


}
