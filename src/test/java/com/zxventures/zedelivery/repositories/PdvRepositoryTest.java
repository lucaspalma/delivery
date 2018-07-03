package com.zxventures.zedelivery.repositories;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.exceptions.AddressNotCoveredException;
import com.zxventures.zedelivery.models.PontoDeVenda;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PdvRepositoryTest {

	@Autowired
	private PdvRepository pdvRepository;
	
	@Test
	public void procuraPorTodosOsPontosDeVendaQueEstaoDentroDaAreaDeCobertura() throws ParseException {
		Point endereco = (Point) new WKTReader().read("POINT(55 55)");
		MultiPolygon areaCobertura = (MultiPolygon) new WKTReader().read("MultiPolygon(((50 50, 50 60, 60 60, 50 50)), ((70 70, 70 75, 75 75, 70 70)))");
		PontoDeVenda pontoDeVenda = new PontoDeVenda("Condado", "Bilbo", "01.584.441/0001-40", endereco, areaCobertura);
		pdvRepository.save(pontoDeVenda);
		
		Point endereco2 = (Point) new WKTReader().read("POINT(10 20)");
		MultiPolygon areaCobertura2= (MultiPolygon) new WKTReader().read("MultiPolygon(((80 80, 80 90, 90 90, 90 80, 80 80)), ((70 70, 70 75, 75 75, 70 70)))");
		PontoDeVenda pontoDeVenda2 = new PontoDeVenda("Grande montanha", "Gimli", "38.670.058/0001-15", endereco2, areaCobertura2);
		pdvRepository.save(pontoDeVenda2);
		
		Point endereco3 = (Point) new WKTReader().read("POINT(200 200)");
		MultiPolygon areaCobertura3= (MultiPolygon) new WKTReader().read("MultiPolygon(((120 120, 120 200, 200 200, 120 120)), ((130 130, 200 130, 200 200, 130 130)))");
		PontoDeVenda pontoDeVenda3 = new PontoDeVenda("Winter is coming", "Stark", "86.823.201/0001-04", endereco3, areaCobertura3);
		pdvRepository.save(pontoDeVenda3);
		
		Point enderecoEntrega = (Point) new WKTReader().read("POINT(71 71)");
		List<PontoDeVenda> pontosDeVenda = pdvRepository.searchPdvsThatCovergeThis(enderecoEntrega, PontoDeVenda.class);
		assertThat(pontosDeVenda, containsInAnyOrder(pontoDeVenda, pontoDeVenda2));
	}

	@Test
	public void consideraABordaDaAreaDeCoberturaQuandoProcurarPorUmPontoDeVenda() throws ParseException {
		Point endereco = (Point) new WKTReader().read("POINT(55 55)");
		MultiPolygon areaCobertura= (MultiPolygon) new WKTReader().read("MultiPolygon(((50 50, 50 60, 60 60, 50 50)), ((70 70, 70 75, 75 75, 70 70)))");
		PontoDeVenda pontoDeVenda = new PontoDeVenda("Condado", "Bilbo", "01.584.441/0001-40", endereco, areaCobertura);
		pdvRepository.save(pontoDeVenda);
		
		Point enderecoEntrega = (Point) new WKTReader().read("POINT(60 60)");
		List<PontoDeVenda> pontosDeVenda = pdvRepository.searchPdvsThatCovergeThis(enderecoEntrega, PontoDeVenda.class);
		assertThat(pontosDeVenda, contains(pontoDeVenda));
	}
	
	@Test
	public void dentreOsPdvsOrdenadosPelaDistanciaDaquelesQueEstaoMaisPertoDoMeuEndereco() throws ParseException, AddressNotCoveredException {
		MultiPolygon areaCobertura= (MultiPolygon) new WKTReader().read("MultiPolygon(((120 120, 120 200, 200 200, 120 120)), ((130 130, 200 130, 200 200, 130 130)))");
		Point enderecoLonge = (Point) new WKTReader().read("POINT(200 200)");
		PontoDeVenda pontoDeVendaLonge = new PontoDeVenda("Winter is coming", "Stark", "86.823.201/0001-04", enderecoLonge, areaCobertura);
		Point enderecoPerto = (Point) new WKTReader().read("POINT(150 150)");
		PontoDeVenda pontoDeVendaPerto = new PontoDeVenda("Fire and blood", "Targaryen", "62.407.723/0001-67", enderecoPerto, areaCobertura);
		pdvRepository.save(pontoDeVendaLonge);
		pdvRepository.save(pontoDeVendaPerto);
		
		Point enderecoEntrega = (Point) new WKTReader().read("POINT(160 160)");
		List<PontoDeVenda> pontosDeVenda = pdvRepository.searchPdvsThatCovergeThis(enderecoEntrega, PontoDeVenda.class);
		assertThat(pontosDeVenda, contains(pontoDeVendaPerto, pontoDeVendaLonge));
	}
	
}
