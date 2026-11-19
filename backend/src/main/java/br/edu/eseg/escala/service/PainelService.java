package br.edu.eseg.escala.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.escala.dto.painel.DepartamentoDescobertoResponse;
import br.edu.eseg.escala.dto.painel.EspecialidadeResumoResponse;
import br.edu.eseg.escala.dto.painel.MedicosPorRegimeResponse;
import br.edu.eseg.escala.dto.painel.PlantonistaNoturnoResponse;
import br.edu.eseg.escala.dto.painel.PredioResumoResponse;
import br.edu.eseg.escala.dto.painel.RankingCargaResponse;
import br.edu.eseg.escala.dto.painel.ResumoDiaResponse;
import br.edu.eseg.escala.repository.PainelRepository;

@Service
@Transactional(readOnly = true)
public class PainelService {

    private final PainelRepository painel;

    public PainelService(PainelRepository painel) {
        this.painel = painel;
    }

    public List<MedicosPorRegimeResponse> medicosPorRegime() {
        return painel.medicosPorRegime();
    }

    public List<EspecialidadeResumoResponse> especialidades() {
        return painel.especialidades();
    }

    public List<PredioResumoResponse> predios() {
        return painel.predios();
    }

    public List<RankingCargaResponse> rankingCarga(Integer limite) {
        return painel.rankingCarga(PageRequest.of(0, limite));
    }

    public List<DepartamentoDescobertoResponse> departamentosDescobertos(Integer dia) {
        return painel.departamentosDescobertos(dia);
    }

    public List<PlantonistaNoturnoResponse> plantonistasNoturnos(Integer minimo) {
        return painel.plantonistasNoturnos(minimo);
    }

    public List<ResumoDiaResponse> dias() {
        Map<Integer, ResumoDiaResponse> porDia = painel.dias().stream()
                .collect(Collectors.toMap(ResumoDiaResponse::diaSemana, Function.identity()));
        List<ResumoDiaResponse> resultado = new ArrayList<>();
        for (int dia = 1; dia <= 7; dia++) {
            resultado.add(porDia.getOrDefault(dia, new ResumoDiaResponse(dia, 0L, 0L, 0L)));
        }
        return resultado;
    }
}
