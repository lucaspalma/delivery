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

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZedeliveryApplicationTests {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private PdvRepository pdvRepository;

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
					"         coverageArea: { " + 
					"              type: \\\"MultiPolygon\\\", " + 
					"              coordinates: [" + 
					"                 [[[30, 20], [45, 40], [10, 40], [30, 20]]], " + 
					"                 [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]" + 
					"              ]" + 
					"         }" +
					"      ) {" +
					"         tradingName" +
					"         ownerName" +
					"         document" +
					"         address {" +
					"            type" +
					"            coordinates" +
					"         }" +
					"         coverageArea {" + 
					"            type" + 
					"            coordinates" + 
					"         }" +
					"      }" +
					"   }\"" +
					"}";
		String jsonResposta = "{\"data\":{\"newPdv\":{\"tradingName\":\"Bar do Bardo\",\"ownerName\":\"Bardo\",\"document\":\"66.881.980/0001-97\",\"address\":{\"type\":\"Point\",\"coordinates\":[10,20]},\"coverageArea\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[30,20],[45,40],[10,40],[30,20]]],[[[15,5],[40,10],[10,20],[5,10],[15,5]]]]}}}}";

		ResponseEntity<String> postForEntity = restTemplate.postForEntity("/graphql", mutation, String.class);
		assertEquals(HttpStatus.OK, postForEntity.getStatusCode());
		assertEquals(jsonResposta, postForEntity.getBody());
	}
	
	@Test
	public void filtraUmPdvBaseadoNoId() throws ParseException {
		Point endereco = (Point) new WKTReader().read("POINT(10 20)");
		MultiPolygon areaCobertura= (MultiPolygon) new WKTReader().read("MultiPolygon(((30 20, 45 40, 10 40, 30 20)), ((15 5, 40 10, 5 10, 15 5)))");
		PontoDeVenda pontoDeVenda = new PontoDeVenda("Três flechas", "Legolas", "34.567.928/0001-74", endereco, areaCobertura);
		pdvRepository.save(pontoDeVenda);
		
		String mutation =
				"{" +
				"   \"query\":\"query {" +
				"      findPdv(" +
				"         id : " + pontoDeVenda.getId() +
				"      ) {" +
				"         id, " +
				"         tradingName" +
				"         ownerName" +
				"         document" +
				"         address {" +
				"            type" +
				"            coordinates" +
				"         }" +
				"         coverageArea {" + 
				"            type" + 
				"            coordinates" + 
				"         }" +
				"      }" +
				"   }\"" +
				"}";
		String jsonResposta = "{\"data\":{\"findPdv\":{\"id\":\""+pontoDeVenda.getId()+"\",\"tradingName\":\"Três flechas\",\"ownerName\":\"Legolas\",\"document\":\"34.567.928/0001-74\",\"address\":{\"type\":\"Point\",\"coordinates\":[10,20]},\"coverageArea\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[30,20],[45,40],[10,40],[30,20]]],[[[15,5],[40,10],[5,10],[15,5]]]]}}}}";

		ResponseEntity<String> postForEntity = restTemplate.postForEntity("/graphql", mutation, String.class);
		assertEquals(HttpStatus.OK, postForEntity.getStatusCode());
		assertEquals(jsonResposta, postForEntity.getBody());
	}

}
