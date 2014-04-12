package service;

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

	private UserService userService;

	public TwitterService(UserService userService) {
		this.userService = userService;
	}

	public void startListener() {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		twitterStream.addListener(new CustomTwitterListener(this::messageReceived));
		twitterStream.filter(filterByTrack("#bluemacaw", "bluemacaw"));
	}
	
	private void messageReceived(String user) {
		if (userService.notExists(user)) {
			if (sendResponse(user)) {
				userService.store(user);
			}
		}
	}
	
	private boolean sendResponse(String user) {
		try {
			updateStatus(user + " Obrigado por falar comigo!!!");
			
			return true;
		} catch (Exception ex) {
			System.out.println("Não foi possível enviar a resposta");
			ex.printStackTrace();
		}
		
		return false;
	}
	
	private void updateStatus(String message) throws TwitterException {
		Status status = TwitterFactory.getSingleton().updateStatus(message);
		
		System.out.println("Successfully updated the status to [" + status.getText() + "].");
	}

	private FilterQuery filterByTrack(String... track) {
		FilterQuery query = new FilterQuery();
		query.track(track);
		
		return query;
	}

}
