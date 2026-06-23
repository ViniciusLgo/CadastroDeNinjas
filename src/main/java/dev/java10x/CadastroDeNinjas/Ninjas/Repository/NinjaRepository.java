package dev.java10x.CadastroDeNinjas.Ninjas.Repository;

import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {
}
