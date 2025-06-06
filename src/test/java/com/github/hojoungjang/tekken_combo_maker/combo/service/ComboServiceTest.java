package com.github.hojoungjang.tekken_combo_maker.combo.service;

import com.github.hojoungjang.tekken_combo_maker.character.mock.FakeCharacterRepository;
import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateAllRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboCreateRequest;
import com.github.hojoungjang.tekken_combo_maker.combo.dto.ComboDto;
import com.github.hojoungjang.tekken_combo_maker.combo.mock.FakeComboRepository;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.common.exception.NotFoundException;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComboServiceTest {

    private ComboService comboService = new ComboService(
            new FakeComboRepository(),
            new FakeCharacterRepository()
    );

    @DisplayName("캐릭터 별로 콤보를 조회 할 수 있다.")
    @Test
    public void findAllComboByCharacterId() throws Exception {
        // given
        Long characterId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ComboDto> characterComboPage = comboService.findAllByCharacter(characterId, pageable);

        // then
        List<ComboDto> characterCombos = characterComboPage.getContent();
        Assertions.assertThat(characterCombos).isNotEmpty().hasSize(1);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getId).contains(1L);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getName).contains("combo 1");
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getDamage).contains(50);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getHitCount).contains(6);
    }

    @DisplayName("게시물 별로 콤보를 조회 할 수 있다.")
    @Test
    public void findAllComboByPostId() throws Exception {
        // given
        Long postId = 2L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ComboDto> postComboPage = comboService.findAllByPost(postId, pageable);

        // then
        List<ComboDto> postCombos = postComboPage.getContent();
        Assertions.assertThat(postCombos).isNotEmpty().hasSize(1);
        Assertions.assertThat(postCombos)
                .extracting(ComboDto::getId).contains(2L);
        Assertions.assertThat(postCombos)
                .extracting(ComboDto::getName).contains("combo 2");
        Assertions.assertThat(postCombos)
                .extracting(ComboDto::getDamage).contains(50);
        Assertions.assertThat(postCombos)
                .extracting(ComboDto::getHitCount).contains(6);
    }

    @DisplayName("입력된 모든 콤보를 저장할 수 있다.")
    @Test
    public void saveAllCombos() throws Exception {
        // given
        Long characterId = 1L;
        List<ComboCreateRequest> comboPayloads = new ArrayList<>();
        for (long id=1; id <= 3; id++) {
            ComboCreateRequest comboPayload = ComboCreateRequest.builder()
                    .name(String.format("new combo %d", id))
                    .description(String.format("국민 콤보 (필드)"))
                    .moveIds(List.of("move-id-1", "move-id-2", "move-id-3"))
                    .damage(30)
                    .hitCount(7)
                    .build();
            comboPayloads.add(comboPayload);
        }
        ComboCreateAllRequest request = ComboCreateAllRequest.builder()
                .combos(comboPayloads)
                .build();

        // when
        comboService.saveAll(characterId, request);

        // then
        Page<ComboDto> characterComboPage = comboService.findAllByCharacter(characterId, PageRequest.of(0, 10));
        List<ComboDto> characterCombos = characterComboPage.getContent();
        Assertions.assertThat(characterCombos).isNotEmpty().hasSize(4);
        Assertions.assertThat(characterCombos)
                .extracting(ComboDto::getName)
                .contains("new combo 1", "new combo 2", "new combo 3");
    }

    @DisplayName("ID 에 해당하는 캐릭터가 없을경우 NotFoundException 예외가 발생한다.")
    @Test
    public void ID_에_해당하는_캐릭터가_없을경우_콤보를_저장할수_없다() throws Exception {
        // given
        Long characterId = 10L;
        List<ComboCreateRequest> comboPayloads = new ArrayList<>();
        for (long id=1; id <= 3; id++) {
            ComboCreateRequest comboPayload = ComboCreateRequest.builder()
                    .name(String.format("new combo %d", id))
                    .description(String.format("국민 콤보 (필드)"))
                    .moveIds(List.of("move-id-1", "move-id-2", "move-id-3"))
                    .damage(30)
                    .build();
            comboPayloads.add(comboPayload);
        }
        ComboCreateAllRequest request = ComboCreateAllRequest.builder()
                .combos(comboPayloads)
                .build();

        // when
        // then
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                        comboService.saveAll(characterId, request);
                })
                .withMessageContaining("Character not found with ID: 10");

    }
}