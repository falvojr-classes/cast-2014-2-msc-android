package br.com.cast.android.aula1.rest;

import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

@Rest(rootUrl = "http://10.11.21.235:8080/rest-app/rest/", converters = { MappingJacksonHttpMessageConverter.class })
public interface LoginRestClient extends RestClientSupport{

	@Post("/user/auth/{email}/{senha}")
	Boolean authenticate(String email, String senha);
}
