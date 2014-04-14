package service;

import java.io.File;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.helper.CustomTwitterListener;
import twitter4j.FilterQuery;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class TwitterService {

	private final Logger logger = LoggerFactory.getLogger(TwitterService.class);

	private UserService userService;
	private ImageService imageService;

	public TwitterService(UserService userService, ImageService imageService) {
		this.userService = userService;
		this.imageService = imageService;
	}

	public void startListener() {
		logger.info("Inicializando o o listener do twitter");
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		twitterStream.addListener(new CustomTwitterListener(this::messageReceived));
		twitterStream.filter(filterByTrack("#bluemacaw", "bluemacaw"));
		
		logger.info("Listener inicializado");
	}
	
	private void messageReceived(User user) {
		if (userService.notExists(user)) {
			logger.info("Usuário " + user.getScreenName() + " não recebeu ainda a mensagem.");
			if (sendResponse(user)) {
				userService.store(user);
			}
		}
	}
	
	private boolean sendResponse(User user) {
		try {
			logger.info("Enviando mensagem para o usuário:" + user.getScreenName());
			File file = imageService.generateImage(user.getName());
			updateStatus(file, "@" + user.getScreenName() + " Obrigado por falar comigo!!!");
			
			return true;
		} catch (Exception ex) {
			logger.error("Não foi possível enviar a resposta", ex);
		}
		
		return false;
	}
	
	private void updateStatus(File file, String message) throws TwitterException {
		StatusUpdate status = new StatusUpdate(message);
	    status.setMedia(file);
		
	    TwitterFactory.getSingleton().updateStatus(status);
	    
		logger.info("Status enviado: " + status.getStatus());
	}

	private FilterQuery filterByTrack(String... track) {
		FilterQuery query = new FilterQuery();
		query.track(track);
		
		logger.info("Tracking inicializado por: " + Arrays.asList(track).stream().reduce("", (i,j) -> i + ", " + j));
		
		return query;
	}

}
