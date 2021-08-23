package com.example.demo.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.Employee;

@Controller
public class WebController {

	@Autowired
	private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WebClient webClient;

	@GetMapping("/data")
	public String getData(Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
			Authentication authentication) { // @AuthenticationPrincipal OidcUser/OAuth2User

		/*
		 * Another way of getting authentication object - Authentication authentication
		 * = SecurityContextHolder.getContext().getAuthentication();
		 */

		System.out.println("Authentication  - " + authentication);

		OAuth2AuthenticationToken oAuthToken = (OAuth2AuthenticationToken) authentication;

		OAuth2AuthorizedClient oAuthClient = oAuth2AuthorizedClientService
				.loadAuthorizedClient(oAuthToken.getAuthorizedClientRegistrationId(), oAuthToken.getName());

		String accessToken = oAuthClient.getAccessToken().getTokenValue();
		System.out.println("accessToken -" + accessToken);

		String url = "http://localhost:8082/hello";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);

		HttpEntity httpClient = new HttpEntity(headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpClient, String.class);
		// WebClient example - String response =
		// webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
		// //bodyToMono(new ParameterizedTypeReference<List<Albums>>(){}
		System.out.println(response.getBody());

		Employee emp = Employee.builder().company("Cognizant").name("Parag").build();

		Employee emp1 = Employee.builder().company("Capgemini").name("Amey").build();
		model.addAttribute("emp", Arrays.asList(emp, emp1));

		return "index";
	}

}
