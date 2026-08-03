package dev.java10x.desafio.config;

import dev.java10x.desafio.entity.Relogio;
import dev.java10x.desafio.enums.MaterialCaixa;
import dev.java10x.desafio.enums.TipoMovimento;
import dev.java10x.desafio.enums.TipoVidro;
import dev.java10x.desafio.repository.RelogioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class CarregadorDadosInicial {

    private final RelogioRepository repository;

    @Bean
    CommandLineRunner seedRelogios() {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            Instant agora = Instant.now();

            List<Relogio> relogios = List.of(

                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Rolex")
                            .modelo("Submariner Date")
                            .referencia("126610LN")
                            .tipoMovimento(TipoMovimento.QUARTZ)
                            .materialCaixa(MaterialCaixa.BRONZE)
                            .tipoVidro(TipoVidro.SAPPHIRE)
                            .resistenciaAguaM(300)
                            .diametroMm(41)
                            .lugToLugMm(48)
                            .espessuraMm(12)
                            .larguraLugMm(21)
                            .precoEmCentavos(8950000L)
                            .urlImagem("https://content.rolex.com/dam/2022/upright-bba-with-shadow/m126610ln-0001.png")
                            .criadoEm(agora)
                            .build(),

                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Omega")
                            .modelo("Speedmaster Professional Moonwatch")
                            .referencia("310.30.42.50.01.001")
                            .tipoMovimento(TipoMovimento.MANUAL)
                            .materialCaixa(MaterialCaixa.STEEL)
                            .tipoVidro(TipoVidro.SAPPHIRE)
                            .resistenciaAguaM(50)
                            .diametroMm(42)
                            .lugToLugMm(47)
                            .espessuraMm(13)
                            .larguraLugMm(20)
                            .precoEmCentavos(6350000L)
                            .urlImagem("https://www.omegawatches.com/media/catalog/product/o/m/omega-speedmaster-moonwatch-professional-31030425001001.png")
                            .criadoEm(agora)
                            .build(),

                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Seiko")
                            .modelo("Prospex Alpinist")
                            .referencia("SPB121J1")
                            .tipoMovimento(TipoMovimento.AUTOMATIC)
                            .materialCaixa(MaterialCaixa.BRONZE)
                            .tipoVidro(TipoVidro.SAPPHIRE)
                            .resistenciaAguaM(200)
                            .diametroMm(39)
                            .lugToLugMm(46)
                            .espessuraMm(13)
                            .larguraLugMm(20)
                            .precoEmCentavos(520000L)
                            .urlImagem("https://www.seikowatches.com/us-en/-/media/Images/Product--Image/All/Prospex/SPB121J1/SPB121J1.png")
                            .criadoEm(agora)
                            .build(),

                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Casio")
                            .modelo("G-Shock GA-2100")
                            .referencia("GA-2100-1A1DR")
                            .tipoMovimento(TipoMovimento.MANUAL)
                            .materialCaixa(MaterialCaixa.CERAMIC)
                            .tipoVidro(TipoVidro.MINERAL)
                            .resistenciaAguaM(200)
                            .diametroMm(45)
                            .lugToLugMm(48)
                            .espessuraMm(12)
                            .larguraLugMm(26)
                            .precoEmCentavos(89900L)
                            .urlImagem("https://www.casio.com/content/dam/casio/product-info/locales/us/en/timepiece/product/watch/G/GA/GA2/GA-2100-1A1/assets/GA-2100-1A1.png")
                            .criadoEm(agora)
                            .build(),

                    Relogio.builder()
                            .id(UUID.randomUUID())
                            .marca("Tissot")
                            .modelo("PRX Powermatic 80")
                            .referencia("T137.407.11.041.00")
                            .tipoMovimento(TipoMovimento.MANUAL)
                            .materialCaixa(MaterialCaixa.BRONZE)
                            .tipoVidro(TipoVidro.ACRYLIC)
                            .resistenciaAguaM(100)
                            .diametroMm(40)
                            .lugToLugMm(44)
                            .espessuraMm(11)
                            .larguraLugMm(12)
                            .precoEmCentavos(495000L)
                            .urlImagem("https://www.tissotwatches.com/media/catalog/product/t/1/t1374071104100.png")
                            .criadoEm(agora)
                            .build()

            );

            repository.saveAll(relogios);
        };
    }
}