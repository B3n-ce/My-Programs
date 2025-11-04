package DND.DND.Spells;

import DND.DND.Characters.Character;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Spell_Service
{
    @Autowired
    private Spells_Repository spells_repository;

    public void saveSpells(Character character, String spellJson) {
        if (spellJson != "")
        {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode spellsArray = objectMapper.readTree(spellJson);

                for (JsonNode spellNode : spellsArray) {
                    String spellName = spellNode.path("spell_name").asText();
                    String castingAction = spellNode.path("casting_action").asText();
                    String rangeArea = spellNode.path("range_area").asText();
                    String spellSchool = spellNode.path("spell_school").asText();
                    String spellLevel = spellNode.path("spell_level").asText();
                    String description = spellNode.path("spell_description").asText();
                    String duration = spellNode.path("spell_duration").asText();
                    Spells spell = new Spells();
                    spell.setName(character.getName());
                    spell.setEmail(character.getUser());
                    spell.setSpell_name(spellName);
                    spell.setCasting_action(castingAction);
                    spell.setRange_area(rangeArea);
                    spell.setSpell_school(spellSchool);
                    spell.setSpell_level(spellLevel);
                    spell.setSpell_description(description);
                    spell.setSpell_duration(duration);
                    spells_repository.save(spell);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Spells spell = new Spells();
            spell.setName(character.getName());
            spell.setEmail(character.getUser());
            spell.setSpell_name("spellName");
            spell.setCasting_action("castingAction");
            spell.setRange_area("rangeArea");
            spell.setSpell_school("spellSchool");
            spell.setSpell_level("spellLevel");
            spell.setSpell_description("description");
            spell.setSpell_duration("duration");
            spells_repository.save(spell);
        }
    }

    public void updateSpells(Character character, String spellsJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode spellsArray = objectMapper.readTree(spellsJson);

            List<Spells> spellsList = spells_repository.findSpellsByCharacter(
                    character.getName(), character.getUser());

            Map<String, Spells> dbSpellsMap = spellsList.stream()
                    .collect(Collectors.toMap(Spells::getSpell_name, s -> s));

            Set<String> incomingNames = new HashSet<>();
            for (JsonNode node : spellsArray) {
                String name = node.path("spell_name").asText();
                incomingNames.add(name);

                Spells spell = dbSpellsMap.get(name);
                if (spell == null) {
                    spell = new Spells();
                    spell.setName(character.getName());
                    spell.setEmail(character.getUser());
                }

                spell.setSpell_name(name);
                spell.setCasting_action(node.path("casting_action").asText());
                spell.setRange_area(node.path("range_area").asText());
                spell.setSpell_school(node.path("spell_school").asText());
                spell.setSpell_level(node.path("spell_level").asText());
                spell.setSpell_description(node.path("spell_description").asText());
                spell.setSpell_duration(node.path("spell_duration").asText());

                spells_repository.save(spell);
            }

            for (Spells dbSpell : spellsList) {
                if (!incomingNames.contains(dbSpell.getSpell_name())) {
                    spells_repository.delete(dbSpell);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}