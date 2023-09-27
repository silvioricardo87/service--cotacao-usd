package com.silvioricardo.cotacaodolar.adapter.out.db.mongo;

import com.silvioricardo.cotacaodolar.domain.entities.CotacaoDolarEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CotacoesDolarRepository extends MongoRepository<CotacaoDolarEntity, String> {

  Optional<CotacaoDolarEntity> findByDataHoraCotacao(String dataHoraCotacao);

}
