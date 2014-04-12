package controller;

import service.TwitterService;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;

@Resource
public class IndexController {

	private TwitterService twitterService;
	
	public IndexController(TwitterService twitterService) {
		this.twitterService = twitterService;
	}
	
	@Get("/")
	public void index() {
		twitterService.startListener();
	}
}
