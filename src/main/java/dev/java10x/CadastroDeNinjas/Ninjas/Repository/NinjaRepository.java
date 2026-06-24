package dev.java10x.CadastroDeNinjas.Ninjas.Repository;

import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository responsavel pelas operacoes de banco da entidade NinjaModel.
// Ao extender JpaRepository, recebe metodos prontos como findAll, findById, save e deleteById.
public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {
}
