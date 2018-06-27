package com.zxventures.zedelivery.actions;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.exceptions.AddressNotCoveredException;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;


@RunWith(SpringRunner.class)
public class SearchPdvActionTest {

	@MockBean
	public PdvRepository pdvRepository;
	private SearchPdvAction searchPdvAction;
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		this.searchPdvAction = new SearchPdvAction(pdvRepository);
	}
	
	@Test
	public void dentreTodosOsPdvsPossiveisRetornaAqueleQueEstaMaisPertoDoMeuEndereco() throws ParseException, AddressNotCoveredException {
		MultiPolygon areaCobertura= (MultiPolygon) new WKTReader().read("MultiPolygon(((120 120, 120 200, 200 200, 120 120)), ((130 130, 200 130, 200 200, 130 130)))");
		Point enderecoLonge = (Point) new WKTReader().read("POINT(200 200)");
		PontoDeVenda pontoDeVendaLonge = new PontoDeVenda("Winter is coming", "Stark", "86.823.201/0001-04", enderecoLonge, areaCobertura);
		Point enderecoPerto = (Point) new WKTReader().read("POINT(150 150)");
		PontoDeVenda pontoDeVendaPerto = new PontoDeVenda("Fire and blood", "Targaryen", "62.407.723/0001-67", enderecoPerto, areaCobertura);
		when(pdvRepository.searchPdvsThatCovergeThis(Mockito.any(Point.class))).thenReturn(asList(pontoDeVendaLonge, pontoDeVendaPerto));
		PontoDeVenda closestPdv = searchPdvAction.searchClosestPdv(160.0, 160.0);
		assertEquals(pontoDeVendaPerto, closestPdv);
	}
	
	
	@Test
	public void seNaoTemNenhumPdvQueAtendaMinhaRegiaoInformaOErro() throws ParseException, AddressNotCoveredException {
		when(pdvRepository.searchPdvsThatCovergeThis(Mockito.any(Point.class))).thenReturn(new ArrayList<>());
		exception.expect(AddressNotCoveredException.class);
		exception.expectMessage("There is not any pdv to attend your address");
		searchPdvAction.searchClosestPdv(50.0, 50.0);
	}
}
