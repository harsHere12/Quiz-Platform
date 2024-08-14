package UserQuizManagement.demoUserQuiz.Utils.Beans;

import UserQuizManagement.demoUserQuiz.Entity.User;
import UserQuizManagement.demoUserQuiz.Utils.Exceptions.UnauthorizedUser;
import org.junit.Assert;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;

import static org.junit.Assert.assertEquals;

public class Beans {
    @Bean
    public static <D,T> D map(final T entity , Class<D> that){
        ModelMapper modelMapper = new ModelMapper() ;
        return modelMapper.map(entity,that) ;
    }

    @Bean
    public static User checkAuthorisation(Object accessToken) throws AuthenticationException, UnauthorizedUser {

        System.out.println(accessToken);
        RestTemplate restTemplate = new RestTemplate() ;
        String resource = "http://10.113.113.44:8087/users" ;

        HttpHeaders httpHeaders = new HttpHeaders() ;
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization","Bearer "+accessToken.toString());

        HttpEntity <String> request = new HttpEntity<> (httpHeaders) ;

        try {
            ResponseEntity<User> response = restTemplate.exchange(resource, HttpMethod.GET, request, User.class);
            System.out.println(response.getBody());
            if(!response.getStatusCode().equals(HttpStatus.OK)) throw new AuthenticationException("User not authenticated");
            return response.getBody() ;

        }
        catch (Exception e) {
            throw new UnauthorizedUser("User not authenticated") ;
        }


    }



}
