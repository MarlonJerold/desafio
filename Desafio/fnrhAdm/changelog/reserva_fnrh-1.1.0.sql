create or replace view bpreceptor.reserva_fnrh as
select res.idreservasfront
     , res.numreserva
     , hot.idhotel as hotelid
     , res.statusreserva  -- 1.CONFIRMADA; 2.CHECK-IN; 3.CHECK-OUT
     , mov.principal
     , con.designacao
     , hos.nome as nome_hospede
     , hos.sobrenome as sobrenome_hospede
     , pho.razaosocial as razaosocial_pessoa
     , pho.nome as nome_pessoa
     , pfi.datanasc as datanascimento
     , pho.email
     -- telefones da pessoa em consulta anexa
     , prof.descricao as profissao -- não integrar de volta para o CM
     , pfi.idnacionalidade as idpais_nacionalidade -- nulo se não houver informação de nacionalidade no CM
     , pais_nac.codiso as codiso_nacionalidade
     , pfi.sexo as genero -- M ou F
     -- documento da pessoa em consulta anexa
     , endres.idendereco as idendereco_res
     , endres.bairro as bairro_res
     , endres.logradouro as logradouro_res
     , endres.numero as numero_res
     , endres.complemento as complemento_res
     , paisres.idpais as idpais_res
     , paisres.codiso as codiso_res
     , endres.codestado as uf_res
     , cidres.idcidades as idcidade_res
     , cidres.codmunicipioibge as codibge_res
     , endres.cep as cep_res
     , he.idhistoricoestada
     , pais_ori.codiso as codiso_ori
     , uf_ori.codestado as uf_ori
     , cidade_ori.codmunicipioibge as cidade_ori
     , pais_des.codiso as codiso_des
     , uf_des.codestado as uf_des
     , cidade_des.codmunicipioibge as cidade_des
     , he.motivoviagem
     , he.transporte as meiotransporte
     , hos.idhospede
     , con.idconta
     , res.datachegprevista
     , res.datapartprevista
     , res.numreservagds
     , ori_res.descricao as origem_des
     , ori_res.codreduzido as origem_cod
     , cel.ddi as cel_ddi
     , cel.ddd as cel_ddd
     , cel.numero as cel_num
     , tel.ddi as tel_ddi
     , tel.ddd as tel_ddd
     , tel.numero as tel_num
  from cm.reservasfront res
  join cm.statusreserva sta on res.statusreserva = sta.statusreserva
  join cm.hotel hot on res.idhotel = hot.idhotel and hot.idhotel in (1,123381,408113,448696)
  join cm.pessoa pes on hot.idpessoa = pes.idpessoa
  join cm.contasfront con on res.idreservasfront = con.idreservasfront
  join cm.pessoa pho on con.idhospede = pho.idpessoa
  join cm.hospede hos on con.idhospede = hos.idhospede
  left join cm.pessoafisica pfi on pho.idpessoa = pfi.idpessoa
  left join cm.pais pais_nac on pfi.idnacionalidade = pais_nac.idpais
  join cm.movimentohospedes mov on res.idreservasfront = mov.idreservasfront and con.idhospede = mov.idhospede
  left join cm.profiss prof on pfi.idprofiss = prof.idprofiss
  -- residência
  left join cm.endpess endres on pho.idendresidencial = endres.idendereco
  left join cm.cidades cidres on endres.idcidades = cidres.idcidades
  left join cm.estado estres on cidres.idestado = estres.idestado
  left join cm.pais paisres on estres.idpais = paisres.idpais
  -- origem e destino da estada
  left join (select idhistoricoestada,motivoviagem,transporte,idhospede,datachegada,idhotel,idcidadeorigem,idcidadedestino 
            from (select idhistoricoestada,motivoviagem,transporte,idhospede,datachegada,idhotel,idcidadeorigem,idcidadedestino, ROW_NUMBER() OVER (PARTITION BY idhospede ORDER BY datachegada desc, idhistoricoestada desc) rn 
            from cm.historicoestada he1 ) where rn = 1) he 
            on he.idhospede = hos.idhospede and he.datachegada = mov.datachegprevista and he.idhotel = hot.idhotel 
  left join cm.cidades cidade_ori on he.idcidadeorigem = cidade_ori.idcidades
  left join cm.estado uf_ori on cidade_ori.idestado = uf_ori.idestado
  left join cm.pais pais_ori on uf_ori.idpais = pais_ori.idpais
  left join cm.cidades cidade_des on he.idcidadedestino = cidade_des.idcidades
  left join cm.estado uf_des on cidade_des.idestado = uf_des.idestado
  left join cm.pais pais_des on uf_des.idpais = pais_des.idpais
  left join cm.origemreserva ori_res on res.idorigem = ori_res.idorigem
  left join (select ddi, ddd, numero, tipo, idendereco from(select  ddi, ddd, numero, tipo, idendereco,ROW_NUMBER() OVER ( PARTITION BY idendereco ORDER BY NULL ) rn
             from cm.telendpess where tipo='L' and numero is not null) where rn = 1
             )cel on endres.idendereco = cel.idendereco
  left join (select ddi, ddd, numero, tipo, idendereco from(select  ddi, ddd, numero, tipo, idendereco,ROW_NUMBER() OVER ( PARTITION BY idendereco ORDER BY NULL ) rn
             from cm.telendpess where tipo='P' and numero is not null) where rn = 1
             )tel on endres.idendereco = tel.idendereco
 where res.datapartprevista >= TO_DATE('01/02/2014','DD/MM/YYYY') -- Data de corte
   and con.tipoconta = 'H'
 order by res.datachegprevista desc, idreservasfront;