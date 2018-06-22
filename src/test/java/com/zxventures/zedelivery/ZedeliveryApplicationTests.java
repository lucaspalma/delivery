package com.zxventures.zedelivery;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZedeliveryApplicationTests {

	@Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void consigoCadastrarUmPdvPassandoTodosOsDadosObrigatorios() {
		String mutation =
					"{" +
					"   \"query\" : \"mutation {" +
					"      newPdv(" +
					"         tradingName : \\\"Bar do Bardo\\\"," +
					"         ownerName : \\\"Bardo\\\"," +
					"         document : \\\"66.881.980/0001-97\\\"," +
					"         address : {" + 
					"            type : \\\"Point\\\"" + 
					"            coordinates : [10,20]" + 
					"         }," +
					"      ) {" +
					"         tradingName" +
					"         ownerName" +
					"         document" +
					"         address {" +
					"            type" +
					"            coordinates" +
					"         }" +
					"      }" +
					"   }\"" +
					"}";
		String jsonResposta = "{\"data\":{\"newPdv\":{\"tradingName\":\"Bar do Bardo\",\"ownerName\":\"Bardo\",\"document\":\"66.881.980/0001-97\",\"address\":{\"type\":\"Point\",\"coordinates\":[10, 20]}}}}";

		ResponseEntity<String> postForEntity = restTemplate.postForEntity("/graphql", mutation, String.class);
		assertEquals(HttpStatus.OK, postForEntity.getStatusCode());
		assertEquals(jsonResposta, postForEntity.getBody());
	}

}
