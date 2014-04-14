package service.helper;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

public class CustomTwitterListener implements StatusListener {

	private Consumer<? super User> action;
	
	private final Logger logger = LoggerFactory.getLogger(CustomTwitterListener.class);
	
	public CustomTwitterListener(Consumer<? super User> action) {
		this.action = action;
	}

	@Override
	public void onStatus(Status status) {		
		User user = status.getUser();
		
		logger.info("Mensagem recebida:" + status.getText());
		logger.info("Usuário que enviou a mensagem:" + user.getScreenName());
		logger.info("Nome completo que do usuário da mensagem:" + user.getName());
		
		action.accept(user);
	}
	
	@Override
	public void onException(Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice){}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {}

	@Override
	public void onStallWarning(StallWarning warning) {}
}
