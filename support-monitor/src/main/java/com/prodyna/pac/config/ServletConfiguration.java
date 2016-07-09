package com.prodyna.pac.config;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
/**
 * 
 * Mapping of turbine aggregation urls to some more user friendly urls
 * Check turbine aggregate configuratin, for default cluster metric configs
 * 
 * @author bjoern
 *
 */
@Controller
public class ServletConfiguration extends SpringBootServletInitializer {

	@Autowired
	private LoadBalancerClient loadBalancer;

	/**
	 * For base url use default cluster
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView gotoNextPage(HttpServletRequest request,
			HttpServletResponse response) {

		ServiceInstance votingService = loadBalancer.choose("support-monitor");

		String streamingURL = votingService.getUri().toString()
				+ "/turbine.stream?cluster=default";
		String encodedURL = URLEncoder.encode(streamingURL);
		String targetURI = "/hystrix/monitor?stream=" + encodedURL;

		ModelMap model = new ModelMap();
		return new ModelAndView(new RedirectView(targetURI, true), model);
	}

}
