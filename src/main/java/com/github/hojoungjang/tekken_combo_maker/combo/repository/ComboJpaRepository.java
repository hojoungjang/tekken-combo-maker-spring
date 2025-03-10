package com.github.hojoungjang.tekken_combo_maker.combo.repository;

import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComboJpaRepository extends JpaRepository<Combo, Long> {

    @Query("select c from Combo c where c.character.id = :characterId")
    public Page<Combo> findAllByCharacter(
            @Param("characterId") Long characterId,
            Pageable pageable
    );

    @Query("select c from Combo c where c.post.id = :postId")
    public Page<Combo> findAllByPost(
            @Param("postId") Long postId,
            Pageable pageable
    );
}
