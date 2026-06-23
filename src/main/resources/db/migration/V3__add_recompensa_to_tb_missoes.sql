-- V3 evolui a tabela de missoes sem mexer nas migrations antigas.
ALTER TABLE tb_missoes
ADD COLUMN recompensa VARCHAR(100);
