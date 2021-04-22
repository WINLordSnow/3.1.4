package yakov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import yakov.model.User;

import java.net.URI;

@SpringBootApplication
public class SpringBootCrudApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootCrudApplication.class, args);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://91.241.64.178:7081/api/users";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String sessionId1 = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);
		String sessionId = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0).split(";")[0].substring(11);
		System.out.println(response.getBody());
		HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add(HttpHeaders.COOKIE, sessionId1);
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//		map.add("id", "3");
//		map.add("name", "James");
//		map.add("lastName", "Brown");
//		map.add("age", "54");
//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		StringBuilder str = new StringBuilder("");
		HttpEntity<User> request = new HttpEntity<>(new User(3L,"James", "Brown", (byte) 35), headers);
		ResponseEntity<String> response1 = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		str.append(response1.getBody());

		HttpEntity<User> request2 = new HttpEntity<>(new User(3L,"Thomas", "Shelby", (byte) 35), headers);
		ResponseEntity<String> response2 = null;
		try {
			response2 = restTemplate.exchange(url, HttpMethod.PUT, request2, String.class);
		} catch (HttpClientErrorException e) {
			System.out.println(response2.getBody());
		}
		str.append(response2.getBody());

		HttpEntity<User> request4 = new HttpEntity<>(new User(3L,"James", "Brown", (byte) 35), headers);
		ResponseEntity<String> response4 = restTemplate.exchange(url + "/3", HttpMethod.DELETE, request4, String.class);
		str.append(response4.getBody());
		System.out.println(str);
	}

}
