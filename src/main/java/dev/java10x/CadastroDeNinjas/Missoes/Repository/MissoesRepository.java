package dev.java10x.CadastroDeNinjas.Missoes.Repository;

import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository responsavel pelas operacoes de banco da entidade MissoesModel.
// Ao extender JpaRepository, recebe metodos prontos como findAll, findById, save e deleteById.
public interface MissoesRepository extends JpaRepository<MissoesModel, Long> {
}
