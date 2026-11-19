package br.edu.eseg.escala.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.edu.eseg.escala.dto.painel.DepartamentoDescobertoResponse;
import br.edu.eseg.escala.dto.painel.EspecialidadeResumoResponse;
import br.edu.eseg.escala.dto.painel.MedicosPorRegimeResponse;
import br.edu.eseg.escala.dto.painel.PlantonistaNoturnoResponse;
import br.edu.eseg.escala.dto.painel.PredioResumoResponse;
import br.edu.eseg.escala.dto.painel.RankingCargaResponse;
import br.edu.eseg.escala.dto.painel.ResumoDiaResponse;
import br.edu.eseg.escala.model.Alocacao;

public interface PainelRepository extends Repository<Alocacao, Long> {

    @Query("""
            select new br.edu.eseg.escala.dto.painel.MedicosPorRegimeResponse(
                   r.descricao, r.cargaHorariaSemanal, count(m.id))
            from Medico m
            join m.regime r
            group by r.id, r.descricao, r.cargaHorariaSemanal
            order by r.cargaHorariaSemanal, r.descricao
            """)
    List<MedicosPorRegimeResponse> medicosPorRegime();

    @Query("""
            select new br.edu.eseg.escala.dto.painel.EspecialidadeResumoResponse(
                   e.nome,
                   count(distinct m.id),
                   count(distinct a.medico.id),
                   coalesce(sum(t.duracaoHoras), 0L))
            from Especialidade e
            left join Medico m on m.especialidade = e
            left join Alocacao a on a.medico = m
            left join a.turno t
            group by e.id, e.nome
            order by coalesce(sum(t.duracaoHoras), 0L) desc, e.nome asc
            """)
    List<EspecialidadeResumoResponse> especialidades();
}
