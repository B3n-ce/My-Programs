package DND.DND.Characters;

import DND.DND.Character_Notes.Characters_Notes_Repository;
import DND.DND.Currency.Currency_Repository;
import DND.DND.Shields.Shields_Repository;
import DND.DND.Skills.SkillsRepository;
import DND.DND.Spells.Spells_Repository;
import DND.DND.Weapons.Weapons_Repository;
import DND.DND.armor.Armors_Repository;
import DND.DND.features.Features_Repository;
import DND.DND.saving_throws.Saving_throws_repository;
import DND.DND.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private Saving_throws_repository saving_throws_repository;

    @Autowired
    private Weapons_Repository weapons_repository;

    @Autowired
    private Features_Repository features_repository;

    @Autowired
    private Armors_Repository armors_repository;

    @Autowired
    private Shields_Repository shields_repository;

    @Autowired
    private Characters_Notes_Repository characters_notes_repository;

    @Autowired
    private Currency_Repository currency_repository;

    @Autowired
    private Spells_Repository spells_repository;

    public Character saveCharacter(CharacterDto characterDto) {
        Character character = new Character();
        character.setName(characterDto.getName());
        character.setLevel(characterDto.getLevel());
        character.setRace(characterDto.getRace());
        character.setKlass(characterDto.getKlass());
        character.setBackground(characterDto.getBackground());
        character.setAlignment(characterDto.getAlignment());
        character.setStrength(characterDto.getStrength());
        character.setDexterity(characterDto.getDexterity());
        character.setConstitution(characterDto.getConstitution());
        character.setIntelligence(characterDto.getIntelligence());
        character.setWisdom(characterDto.getWisdom());
        character.setCharisma(characterDto.getCharisma());
        character.setUser(characterDto.getUser());
        character.setHp(characterDto.getHp());
        character.setProficiency_bonus(characterDto.getProficiency_bonus());
        character.setInitiative(characterDto.getInitiative());
        character.setArmor_class(characterDto.getArmor_class());
        character.setHit_dice(characterDto.getHit_dice());
        character.setSpeed(characterDto.getSpeed());
        character.setImage_URL(characterDto.getImage_URL());

        character.setCharisma_b(characterDto.getCharisma_b());
        character.setConstitution_b(characterDto.getConstitution_b());
        character.setDexterity_b(characterDto.getDexterity_b());
        character.setIntelligence_b(characterDto.getIntelligence_b());
        character.setStrength_b(characterDto.getStrength_b());
        character.setWisdom_b(characterDto.getWisdom_b());
        character.setDeity(characterDto.getDeity());
        character.setXp(characterDto.getXp());

        return characterRepository.save(character);
    }


    public List<Character> getCharactersByEmail(String email) {
        return characterRepository.findCharactersByEmail(email);
    }

    @Transactional
    public void deleteCharacterById(Long id) {
        Optional<Character> characterOptional = characterRepository.findById(id);

        if (characterOptional.isPresent()) {
            Character character = characterOptional.get();
            String name = character.getName();
            String email = character.getUser();

            skillsRepository.deleteSkillByNameAndEmail(name, email);

            saving_throws_repository.deleteSavingThrowByNameAndEmail(name, email);

            weapons_repository.deleteWeaponByNameAndEmail(name, email);
            armors_repository.deleteArmorsByNameAndEmail(name, email);
            shields_repository.deleteShieldsByNameAndEmail(name, email);
            features_repository.deleteFeaturesByNameAndEmail(name, email);
            characters_notes_repository.deleteCharacters_NotesByNameAndEmail(name, email);
            currency_repository.deleteCurrenciesByNameAndEmail(name, email);
            spells_repository.deleteSpellsByNameAndEmail(name, email);
            characterRepository.deleteById(id);
        }
    }

    public Character updateCharacter(Long id, CharacterDto characterDto) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        character.setName(characterDto.getName());
        character.setLevel(characterDto.getLevel());
        character.setRace(characterDto.getRace());
        character.setKlass(characterDto.getKlass());
        character.setBackground(characterDto.getBackground());
        character.setAlignment(characterDto.getAlignment());

        character.setStrength(characterDto.getStrength());
        character.setDexterity(characterDto.getDexterity());
        character.setConstitution(characterDto.getConstitution());
        character.setIntelligence(characterDto.getIntelligence());
        character.setWisdom(characterDto.getWisdom());
        character.setCharisma(characterDto.getCharisma());

        character.setHp(characterDto.getHp());
        character.setProficiency_bonus(characterDto.getProficiency_bonus());
        character.setInitiative(characterDto.getInitiative());
        character.setArmor_class(characterDto.getArmor_class());
        character.setHit_dice(characterDto.getHit_dice());
        character.setSpeed(characterDto.getSpeed());
        character.setImage_URL(characterDto.getImage_URL());

        return characterRepository.save(character);
    }
}
