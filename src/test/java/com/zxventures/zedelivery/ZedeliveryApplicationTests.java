package com.zxventures.zedelivery;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
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
					"         form : {" +
					"            tradingName : \\\"Bar do Bardo\\\"," +
					"            ownerName : \\\"Bardo\\\"," +
					"            document : \\\"66.881.980/0001-97\\\"," +
					"            address : {" + 
					"               type : \\\"Point\\\"" + 
					"               coordinates : [10,20]" + 
					"            }," +
					"            coverageArea: { " + 
					"                 type: \\\"MultiPolygon\\\", " + 
					"                 coordinates: [" + 
					"                    [[[30, 20], [45, 40], [10, 40], [30, 20]]], " + 
					"                    [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]" + 
					"                 ]" + 
					"            }" +
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

	@Test
	public void buscaUmPdvQueAtendaAMinhaLocalizacao() throws ParseException {
		Point endereco = (Point) new WKTReader().read("POINT(10 20)");
		MultiPolygon areaCobertura= (MultiPolygon) new WKTReader().read("MultiPolygon(((80 80, 80 90, 90 90, 90 80, 80 80)), ((70 70, 70 75, 75 75, 70 70)))");
		PontoDeVenda pontoDeVenda = new PontoDeVenda("Grande montanha", "Gimli", "38.670.058/0001-15", endereco, areaCobertura);
		pdvRepository.save(pontoDeVenda);
		
		String mutation =
				"{" +
				"   \"query\":\"query {" +
				"      searchPdv(" +
				"         lng : " + 85 + "," +
				"         lat : " + 85 + "," +
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
		String jsonResposta = "{\"data\":{\"searchPdv\":[{\"id\":\""+pontoDeVenda.getId()+"\",\"tradingName\":\"Grande montanha\",\"ownerName\":\"Gimli\",\"document\":\"38.670.058/0001-15\",\"address\":{\"type\":\"Point\",\"coordinates\":[10,20]},\"coverageArea\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[80,80],[80,90],[90,90],[90,80],[80,80]]],[[[70,70],[70,75],[75,75],[70,70]]]]}}]}}";

		ResponseEntity<String> postForEntity = restTemplate.postForEntity("/graphql", mutation, String.class);
		assertEquals(HttpStatus.OK, postForEntity.getStatusCode());
		assertEquals(jsonResposta, postForEntity.getBody());
	}
	
	@Test
	public void naBuscaUmPdvQueAtendeMinhaLocalizacaoConsideraTambemAsBordasDaAreaDeCobertura() throws ParseException {
		Point endereco = (Point) new WKTReader().read("POINT(55 55)");
		MultiPolygon areaCobertura= (MultiPolygon) new WKTReader().read("MultiPolygon(((50 50, 50 60, 60 60, 50 50)), ((70 70, 70 75, 75 75, 70 70)))");
		PontoDeVenda pontoDeVenda = new PontoDeVenda("Condado", "Bilbo", "01.584.441/0001-40", endereco, areaCobertura);
		pdvRepository.save(pontoDeVenda);
		
		String mutation =
				"{" +
				"   \"query\":\"query {" +
				"      searchPdv(" +
				"         lng : " + 50 + "," +
				"         lat : " + 50 + "," +
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
		String jsonResposta = "{\"data\":{\"searchPdv\":[{\"id\":\""+pontoDeVenda.getId()+"\",\"tradingName\":\"Condado\",\"ownerName\":\"Bilbo\",\"document\":\"01.584.441/0001-40\",\"address\":{\"type\":\"Point\",\"coordinates\":[55,55]},\"coverageArea\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[50,50],[50,60],[60,60],[50,50]]],[[[70,70],[70,75],[75,75],[70,70]]]]}}]}}";

		ResponseEntity<String> postForEntity = restTemplate.postForEntity("/graphql", mutation, String.class);
		assertEquals(HttpStatus.OK, postForEntity.getStatusCode());
		assertEquals(jsonResposta, postForEntity.getBody());
	}
	
	@Test
	public void naBuscaUmPdvQueAtendeMinhaLocalizacaoPegaOrdenadoDoMaisPertoParaOMaisLonge() throws ParseException {
		MultiPolygon areaCobertura= (MultiPolygon) new WKTReader().read("MultiPolygon(((120 120, 120 200, 200 200, 120 120)), ((130 130, 200 130, 200 200, 130 130)))");
		
		Point enderecoLonge = (Point) new WKTReader().read("POINT(200 200)");
		PontoDeVenda pontoDeVendaLonge = new PontoDeVenda("Winter is coming", "Stark", "86.823.201/0001-04", enderecoLonge, areaCobertura);
		pdvRepository.save(pontoDeVendaLonge);
		
		Point enderecoPerto = (Point) new WKTReader().read("POINT(150 150)");
		PontoDeVenda pontoDeVendaPerto = new PontoDeVenda("Fire and blood", "Targaryen", "62.407.723/0001-67", enderecoPerto, areaCobertura);
		pdvRepository.save(pontoDeVendaPerto);
		
		String mutation =
				"{" +
				"   \"query\":\"query {" +
				"      searchPdv(" +
				"         lng : " + 160 + "," +
				"         lat : " + 160 + "," +
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
		String jsonResposta = "{\"data\":{\"searchPdv\":[{\"id\":\""+pontoDeVendaPerto.getId()+"\",\"tradingName\":\"Fire and blood\",\"ownerName\":\"Targaryen\",\"document\":\"62.407.723/0001-67\",\"address\":{\"type\":\"Point\",\"coordinates\":[150,150]},\"coverageArea\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[120,120],[120,200],[200,200],[120,120]]],[[[130,130],[200,130],[200,200],[130,130]]]]}},{\"id\":\""+pontoDeVendaLonge.getId()+"\",\"tradingName\":\"Winter is coming\",\"ownerName\":\"Stark\",\"document\":\"86.823.201/0001-04\",\"address\":{\"type\":\"Point\",\"coordinates\":[200,200]},\"coverageArea\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[120,120],[120,200],[200,200],[120,120]]],[[[130,130],[200,130],[200,200],[130,130]]]]}}]}}";

		ResponseEntity<String> postForEntity = restTemplate.postForEntity("/graphql", mutation, String.class);
		assertEquals(HttpStatus.OK, postForEntity.getStatusCode());
		assertEquals(jsonResposta, postForEntity.getBody());
	}	
	
}
