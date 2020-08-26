package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LeilaoTest {

    public static final double DELTA = 0.0001;
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario LEANDRO = new Usuario("Leandro");


    @Test
    public void dev_DevolverDescricao_QuandoRecebeDescricao() {
        //executar ação esperada
        String descricaoDevolvida = CONSOLE.getDescricao();

        //testar resultado esperado
       // assertEquals("Console",descricaoDevolvida);
        assertThat(descricaoDevolvida, is("Console"));
    }

    @Test
    public void dev_DevolverMaiorLance_QuandoRecebeApenasUmLance(){
        CONSOLE.propoe(new Lance(LEANDRO, 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        //assertEquals(200.0, maiorLanceDevolvido, 0.0001);
        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente(){
        CONSOLE.propoe(new Lance(LEANDRO, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Natália"),400.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(400.0, maiorLanceDevolvido, 0.0001);
    }
   /* @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente(){
        CONSOLE.propoe(new Lance(LEANDRO, 10000.0));
        CONSOLE.propoe(new Lance(new Usuario("Natália"), 9000.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();
        assertEquals(10000.0, maiorLanceDevolvido, 0.0001);
    }*/

    @Test
    public void dev_DevolverMenorLance_QuandoRecebeApenasUmLance(){
        CONSOLE.propoe(new Lance(LEANDRO, 200.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, 0.0001);
    }
    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente(){
        CONSOLE.propoe(new Lance(LEANDRO, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Natália"),400.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(300.0, menorLanceDevolvido, 0.0001);
    }
    /*
    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente(){
        CONSOLE.propoe(new Lance(LEANDRO, 10000.0));
        CONSOLE.propoe(new Lance(new Usuario("Natália"), 9000.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();
        assertEquals(9000.0, menorLanceDevolvido, 0.0001);
    }*/

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeExatosTresLances(){
        CONSOLE.propoe(new Lance(LEANDRO, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Natália"), 300.0));
        CONSOLE.propoe(new Lance(LEANDRO, 400.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        //assertEquals(3, tresMaioresLancesDevolvidos.size());
        //assertThat(tresMaioresLancesDevolvidos, hasSize(3));

        //assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
       /* assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(LEANDRO, 400.0)));
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);*/

        /*assertThat(tresMaioresLancesDevolvidos, contains(
                new Lance(LEANDRO, 400.0),
                new Lance(new Usuario("Natália"), 300.0),
                new Lance(LEANDRO, 200.0)
        ));*/

        assertThat(tresMaioresLancesDevolvidos, both(Matchers.<Lance>hasSize(3)).and(
                contains(new Lance(LEANDRO, 400.0),
                        new Lance(new Usuario("Natália"), 300.0),
                        new Lance(LEANDRO, 200.0))
        ));
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances(){
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance(){
        CONSOLE.propoe(new Lance(LEANDRO, 200.0));
        List<Lance> tresMaioresLancesDevolvidos =  CONSOLE.tresMaioresLances();

        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0,tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLances(){
        CONSOLE.propoe(new Lance(LEANDRO, 300.00));
        CONSOLE.propoe(new Lance(new Usuario("Natália"), 400.00));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }
    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisTresLances(){
        CONSOLE.propoe(new Lance(LEANDRO, 300.0));
        final Usuario NATALIA = new Usuario("Natália");

        CONSOLE.propoe(new Lance(NATALIA, 400.0));
        CONSOLE.propoe(new Lance(LEANDRO, 500.0));
        CONSOLE.propoe(new Lance(NATALIA, 600.0));

        final List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());
        assertEquals(600.0, tresMaioresLancesDevolvidosParaQuatroLances.
                get(0).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaQuatroLances.
                get(1).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidosParaQuatroLances.
                get(2).getValor(), DELTA);

        final List<Lance> tresMaioresLancesDevolvidosParaCincoLances =
                CONSOLE.tresMaioresLances();
        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());
        assertEquals(600.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(),DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(),DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(),DELTA);
    }
    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLances(){
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();
        assertEquals(0.0, maiorLanceDevolvido, DELTA);
    }
    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoForMenorQueOMaiorLance(){
        CONSOLE.propoe(new Lance(LEANDRO, 500.0));
        CONSOLE.propoe(new Lance(new Usuario("Natália"), 400.0));
    }
    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance(){
        CONSOLE.propoe(new Lance(LEANDRO, 500.0));
        CONSOLE.propoe(new Lance(LEANDRO, 600.0));
    }
    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void naoDeve_AdicionarLance_QuandoUsuarioDerCincoLances(){
        CONSOLE.propoe(new Lance(LEANDRO, 100.0));
        final Usuario NATALIA =new Usuario("Natália");
        CONSOLE.propoe(new Lance(NATALIA, 200.0));
        CONSOLE.propoe(new Lance(LEANDRO, 300.0));
        CONSOLE.propoe(new Lance(NATALIA, 400.0));
        CONSOLE.propoe(new Lance(LEANDRO, 500.0));
        CONSOLE.propoe(new Lance(NATALIA, 600.0));
        CONSOLE.propoe(new Lance(LEANDRO, 700.0));
        CONSOLE.propoe(new Lance(NATALIA, 800.0));
        CONSOLE.propoe(new Lance(LEANDRO, 900.0));
        CONSOLE.propoe(new Lance(NATALIA, 1000.0));

        CONSOLE.propoe(new Lance(LEANDRO, 1100.0));

    }
}