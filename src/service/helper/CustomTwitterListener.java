package service.helper;

import java.util.function.Consumer;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class CustomTwitterListener implements StatusListener {

	private Consumer<? super String> action;
	
	public CustomTwitterListener(Consumer<? super String> action) {
		this.action = action;
	}

	@Override
	public void onStatus(Status status) {
		action.accept("@" + status.getUser().getScreenName());
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
