package com.github.hojoungjang.tekken_combo_maker.combo.dto;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class ComboDtoTest {

    @DisplayName("Combo 엔티티 객체를 이용해 ComboDto 생성 테스트")
    @Test
    public void Combo_엔티티_객체로_ComboDto_생성() throws Exception {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickName("test-user")
                .build();
        ReflectionTestUtils.setField(member, "id", 10001L);

        Character character = Character.builder()
                .name("카즈야")
                .description("테스트용 카즈야 상세 글")
                .avatarImageUrl("/img/kazuya.png")
                .build();
        ReflectionTestUtils.setField(character, "id", 10002L);

        Post post = Post.builder()
                .member(member)
                .build();
        ReflectionTestUtils.setField(post, "id", 10003L);

        Combo combo = Combo.builder()
                .character(character)
                .post(post)
                .name("테스트 콤보")
                .damage(50)
                .hitCount(6)
                .build();
        ReflectionTestUtils.setField(combo, "id", 10004L);

        // when
        ComboDto comboDto = ComboDto.fromEntity(combo);

        // then
        Assertions.assertThat(comboDto.getId()).isEqualTo(10004L);

        Assertions.assertThat(comboDto.getCharacter().id()).isEqualTo(10002L);
        Assertions.assertThat(comboDto.getCharacter().name()).isEqualTo("카즈야");

        Assertions.assertThat(comboDto.getPost().id()).isEqualTo(10003L);
        Assertions.assertThat(comboDto.getPost().authorId()).isEqualTo(10001L);

        Assertions.assertThat(comboDto.getName()).isEqualTo("테스트 콤보");
        Assertions.assertThat(comboDto.getDamage()).isEqualTo(50);
        Assertions.assertThat(comboDto.getHitCount()).isEqualTo(6);
    }
}