package service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class ImageService {

	public File generateImage(String user) throws IOException {
		final BufferedImage image = getRandomImage();

		Graphics g = image.getGraphics();
		g.setFont(g.getFont().deriveFont(30f));
		g.drawString(user, 100, 100);
		g.dispose();
		
		File file = File.createTempFile("generated_file", user);

		ImageIO.write(image, "png", file);
		
		return file;
	}

	private BufferedImage getRandomImage() throws MalformedURLException,
			IOException {
		return ImageIO.read(new URL("http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png"));
	}
}
