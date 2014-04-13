package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.TwitterService;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
@Path("/")
public class IndexController {

	private final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private TwitterService twitterService;
	
	public IndexController(TwitterService twitterService) {
		this.twitterService = twitterService;
	}
	
	@Get("/")
	public void index() {
		logger.info("Chamada URL inicial");
		twitterService.startListener();
	}
}
