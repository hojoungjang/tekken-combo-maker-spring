package com.github.hojoungjang.tekken_combo_maker.combo.mock;

import com.github.hojoungjang.tekken_combo_maker.character.model.entity.Character;
import com.github.hojoungjang.tekken_combo_maker.combo.model.entity.Combo;
import com.github.hojoungjang.tekken_combo_maker.combo.service.IComboRepository;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.post.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeComboRepository implements IComboRepository {

    private List<Combo> combos = new ArrayList<>();

    private Combo createTestData(Long id) {
        Member member = Member.builder()
                .email(String.format("test%d@example.com", id))
                .nickname(String.format("test user %d", id))
                .build();
        ReflectionTestUtils.setField(member, "id", id);

        Character character = Character.builder()
                .name(String.format("character %d", id))
                .fullName(String.format("character full %d", id))
                .description(String.format("character description %d", id))
                .avatarImageUrl(String.format("/img/character%d.png", id))
                .build();
        ReflectionTestUtils.setField(character, "id", id);

        Post post = Post.builder()
                .member(member)
                .build();
        ReflectionTestUtils.setField(post, "id", id);

        Combo combo = Combo.builder()
                .character(character)
                .post(post)
                .name(String.format("combo %d", id))
                .damage(50)
                .hitCount(6)
                .build();
        ReflectionTestUtils.setField(combo, "id", id);
        return combo;
    }

    public FakeComboRepository() {
        for (long i=1; i < 4; i++) {
            combos.add(createTestData(i));
        }
    }

    @Override
    public Page<Combo> findAllByCharacter(Long characterId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Combo> characterCombos = combos.stream().filter(combo -> Objects.equals(combo.getCharacter().getId(), characterId)).toList();
        List<Combo> data = new ArrayList<>(characterCombos.subList(offset, Math.min(offset + pageSize, characterCombos.size())));
        return new PageImpl<>(data, pageable, data.size());
    }

    @Override
    public Page<Combo> findAllByPost(Long postId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        List<Combo> postCombos = combos.stream().filter(combo -> Objects.equals(combo.getPost().getId(), postId)).toList();
        List<Combo> data = new ArrayList<>(postCombos.subList(offset, Math.min(offset + pageSize, postCombos.size())));
        return new PageImpl<>(data, pageable, data.size());
    }

    @Override
    public List<Combo> saveAll(List<Combo> combos) {
        combos.forEach(combo -> {
            ReflectionTestUtils.setField(combo, "id", this.combos.size() + 1L);
        });
        this.combos.addAll(combos);
        return combos;
    }
}
