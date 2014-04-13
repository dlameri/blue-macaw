package service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.helper.CustomTwitterListener;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class TwitterService {

	private final Logger logger = LoggerFactory.getLogger(TwitterService.class);

	private UserService userService;

	public TwitterService(UserService userService) {
		this.userService = userService;
	}

	public void startListener() {
		logger.info("Inicializando o o listener do twitter");
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		twitterStream.addListener(new CustomTwitterListener(this::messageReceived));
		twitterStream.filter(filterByTrack("#bluemacaw", "bluemacaw"));
		
		logger.info("Listener inicializado");
	}
	
	private void messageReceived(String user) {
		if (userService.notExists(user)) {
			logger.info("Usuário " + user + " não recebeu ainda a mensagem.");
			if (sendResponse(user)) {
				userService.store(user);
			}
		}
	}
	
	private boolean sendResponse(String user) {
		try {
			logger.info("Enviando mensagem para o usuário:" + user);
			updateStatus(user + " Obrigado por falar comigo!!!");
			
			return true;
		} catch (Exception ex) {
			logger.error("Não foi possível enviar a resposta", ex);
		}
		
		return false;
	}
	
	private void updateStatus(String message) throws TwitterException {
		Status status = TwitterFactory.getSingleton().updateStatus(message);
		
		logger.info("Status enviado: " + status.getText());
	}

	private FilterQuery filterByTrack(String... track) {
		FilterQuery query = new FilterQuery();
		query.track(track);
		
		logger.info("Tracking inicializado por: " + Arrays.asList(track).stream().reduce("", (i,j) -> i + ", " + j));
		
		return query;
	}

}
