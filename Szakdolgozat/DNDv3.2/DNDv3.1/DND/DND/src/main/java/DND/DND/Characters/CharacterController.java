package DND.DND.Characters;

import DND.DND.Character_Notes.Characters_Notes;
import DND.DND.Character_Notes.Characters_Notes_Service;
import DND.DND.Currency.Currency_Dto;
import DND.DND.Currency.Currency_Service;
import DND.DND.Shields.Shields;
import DND.DND.Shields.Shields_Service;
import DND.DND.Skills.SkillsDto;
import DND.DND.Skills.SkillsService;
import DND.DND.Spells.Spell_Service;
import DND.DND.Spells.Spells;
import DND.DND.Spells.Spells_Repository;
import DND.DND.Weapons.Weapons;
import DND.DND.Weapons.Weapons_Service;
import DND.DND.armor.Armors;
import DND.DND.armor.Armors_Service;
import DND.DND.features.Features;
import DND.DND.features.Features_Dto;
import DND.DND.features.Features_Service;
import DND.DND.saving_throws.Saving_throws_dto;
import DND.DND.saving_throws.Saving_throws_service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private SkillsService skillsService;

    @Autowired
    private Saving_throws_service saving_throws_service;

    @Autowired
    private Weapons_Service weapons_service;

    @Autowired
    private Armors_Service armors_service;

    @Autowired
    private Shields_Service shields_service;

    @Autowired
    private Features_Service features_service;

    @Autowired
    private Characters_Notes_Service characters_notes_service;

    @Autowired
    private Currency_Service currency_service;

    @Autowired
    private Spell_Service spell_service;

    @Autowired
    private Spells_Repository spells_repository;

    @GetMapping("/create")
    public String showCreateCharacterForm(Model model, Principal principal,  RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        List<Character> characters = characterService.getCharactersByEmail(username);
        if (characters.size() == 3)
        {
            redirectAttributes.addFlashAttribute("characterLimitError", "Nem hozhatsz létre több karaktert! Fizess elő a pro verzióra!");
            return "redirect:/characters";
        }

        CharacterDto characterDto = new CharacterDto();
        characterDto.setUser(username);
        model.addAttribute("characterDto", characterDto);

        Features_Dto features_dto = new Features_Dto();
        model.addAttribute("features_dto", features_dto);

        Currency_Dto currency_dto = new Currency_Dto();
        model.addAttribute("currency_dto", currency_dto);

        model.addAttribute("saving_throws_dto", new Saving_throws_dto());

        List<String> existingNames = characters.stream()
                .map(Character::getName)
                .toList();
        model.addAttribute("existingNames", existingNames);

        return "Character_Create";
    }

    @PostMapping("/create")
    public String createCharacter(@ModelAttribute("characterDto") CharacterDto characterDto,
                                  Features_Dto features_dto,
                                  Currency_Dto currency_dto,
                                  SkillsDto skillsDto,
                                  @ModelAttribute("saving_throws_dto") Saving_throws_dto saving_throws_dto,
                                  @RequestParam String equipment,
                                  String story_string,
                                  String inventory_string,
                                  String spells,
                                  Model model) {
        List<Character> characters = characterService.getCharactersByEmail(characterDto.getUser());

        for (Character character : characters) {
            if (character.getName().equals(characterDto.getName())) {
                model.addAttribute("errorMessage", "A character with the same name already exists!");
                return "Character_Create";
            }
        }
        Character savedCharacter = characterService.saveCharacter(characterDto);
        skillsService.saveSkills(savedCharacter, skillsDto);
        saving_throws_service.save_saving_throws(savedCharacter, saving_throws_dto);
        features_service.saveFeatures(features_dto, characterDto);
        weapons_service.saveWeapons(savedCharacter, equipment);
        armors_service.saveArmors(savedCharacter, equipment);
        shields_service.saveShields(savedCharacter, equipment);
        characters_notes_service.saveStoryAndInventory(savedCharacter, story_string, inventory_string);
        currency_service.saveCurrency(savedCharacter, currency_dto);
        spell_service.saveSpells(savedCharacter, spells);
        LogCharacterActions.logCA("Character created: " + savedCharacter.getName());
        return "redirect:/characters";
    }

    @GetMapping("/edit/{id}")
    public String editCharacter(@PathVariable("id") Long id, Model model) throws JsonProcessingException {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        CharacterDto characterDto = new CharacterDto();
        characterDto.setName(character.getName());
        characterDto.setLevel(character.getLevel());
        characterDto.setRace(character.getRace());
        characterDto.setKlass(character.getKlass());
        characterDto.setBackground(character.getBackground());
        characterDto.setAlignment(character.getAlignment());
        characterDto.setStrength(character.getStrength());
        characterDto.setDexterity(character.getDexterity());
        characterDto.setConstitution(character.getConstitution());
        characterDto.setIntelligence(character.getIntelligence());
        characterDto.setWisdom(character.getWisdom());
        characterDto.setCharisma(character.getCharisma()); //nem volt itt
        characterDto.setHp(character.getHp());
        characterDto.setImage_URL(character.getImage_URL());

        model.addAttribute("characterDto", characterDto);

        List<Weapons> weaponsList = weapons_service.getWeapons(character.getName(), character.getUser());
        List<Armors> armorsList = armors_service.getArmors(character.getName(), character.getUser());
        List<Shields> shieldsList = shields_service.getShields(character.getName(), character.getUser());

        Map<String, Object> equipments = new HashMap<>();
        equipments.put("weapons", weaponsList);
        equipments.put("armors", armorsList);
        equipments.put("shields", shieldsList);
        model.addAttribute("equipments", equipments);

        Characters_Notes characters_notes = characters_notes_service.getCharacters_Notes(character.getName(), character.getUser());
        model.addAttribute("characters_notes_story", characters_notes.getStory());

        Features features = features_service.getFeatures(character.getName(), character.getUser());
        model.addAttribute("feature", features);

        List<Spells> spells = spells_repository.findSpellsByCharacter(character.getName(), character.getUser());
        model.addAttribute("spells", spells);

        model.addAttribute("saving_throws_dto", new Saving_throws_dto());
        model.addAttribute("skillsDto", new SkillsDto());

        var stList = saving_throws_service.get_saving_throws(character.getName(), character.getUser());
        var st = stList != null && !stList.isEmpty() ? stList.get(0) : null;
        var skillsList = skillsService.getSkills(character.getName(), character.getUser());
        var skills = skillsList != null && !skillsList.isEmpty() ? skillsList.get(0) : null;

        model.addAttribute("id", id);

        Map<String, Object> payload = new HashMap<>();
        payload.put("character", characterDto);
        payload.put("features", features);
        payload.put("equipments", equipments);
        payload.put("spells", spells);
        payload.put("story", characters_notes.getStory());
        payload.put("savingThrows", st);
        payload.put("skills", skills);

        String editDataJson = new ObjectMapper().writeValueAsString(payload);
        model.addAttribute("editDataJson", editDataJson);

        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCharacter(@PathVariable("id") Long id,
                                  @ModelAttribute CharacterDto characterDto,
                                  Features_Dto features_dto,
                                  @RequestParam("equipmentJson") String equipmentJson,
                                  @RequestParam("story") String story,
                                  @RequestParam("spells") String spells,
                                  SkillsDto skillsDto,
                                  @ModelAttribute("saving_throws_dto") Saving_throws_dto saving_throws_dto
    ) {
        Character character = characterService.updateCharacter(id, characterDto);
        skillsService.updateSkills(character, skillsDto);
        saving_throws_service.update_saving_throws(character, saving_throws_dto);
        weapons_service.updateWeapons(character, equipmentJson);
        armors_service.updateArmors(character, equipmentJson);
        shields_service.updateShields(character, equipmentJson);
        characters_notes_service.updateStory(character, story);
        features_service.updateFeatures(features_dto, character);
        spell_service.updateSpells(character, spells);

        LogCharacterActions.logCA("Character modified: " + character.getName());
        return "redirect:/characters";
    }

    @PostMapping("/deleteCharacter")
    public String deleteCharacter(@RequestParam("characterId") Long characterId, Model model) {
        characterService.deleteCharacterById(characterId);
        LogCharacterActions.logCA("Character deleted!");
        model.addAttribute("message", "Character deleted successfully");
        return "redirect:/characters";
    }
}
