package DND.DND.Skills;

import DND.DND.Characters.Character;
import DND.DND.Characters.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillsService
{
    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    private CharacterRepository characterRepository;

    public Skills saveSkills(Character character, SkillsDto dto) {
        if (dto == null) dto = new SkillsDto();

        Skills skills = new Skills();
        skills.setName(character.getName());
        skills.setEmail(character.getUser());

        skills.setAcrobatics(dto.getAcrobatics());
        skills.setAcrobatics_p(dto.isAcrobatics_p());

        skills.setAnimal_handling(dto.getAnimal_handling());
        skills.setAnimal_handling_p(dto.isAnimal_handling_p());

        skills.setArcane(dto.getArcane());
        skills.setArcane_p(dto.isArcane_p());

        skills.setAthletics(dto.getAthletics());
        skills.setAthletics_p(dto.isAthletics_p());

        skills.setDeception(dto.getDeception());
        skills.setDeception_p(dto.isDeception_p());

        skills.setHistory(dto.getHistory());
        skills.setHistory_p(dto.isHistory_p());

        skills.setInsight(dto.getInsight());
        skills.setInsight_p(dto.isInsight_p());

        skills.setIntimidation(dto.getIntimidation());
        skills.setIntimidation_p(dto.isIntimidation_p());

        skills.setInvestigation(dto.getInvestigation());
        skills.setInvestigation_p(dto.isInvestigation_p());

        skills.setMedicine(dto.getMedicine());
        skills.setMedicine_p(dto.isMedicine_p());

        skills.setNature(dto.getNature());
        skills.setNature_p(dto.isNature_p());

        skills.setPerception(dto.getPerception());
        skills.setPerception_p(dto.isPerception_p());

        skills.setPerformance(dto.getPerformance());
        skills.setPerformance_p(dto.isPerformance_p());

        skills.setPersuasion(dto.getPersuasion());
        skills.setPersuasion_p(dto.isPersuasion_p());

        skills.setReligion(dto.getReligion());
        skills.setReligion_p(dto.isReligion_p());

        skills.setSleight_of_hand(dto.getSleight_of_hand());
        skills.setSleight_of_hand_p(dto.isSleight_of_hand_p());

        skills.setStealth(dto.getStealth());
        skills.setStealth_p(dto.isStealth_p());

        skills.setSurvival(dto.getSurvival());
        skills.setSurvival_p(dto.isSurvival_p());

        return skillsRepository.save(skills);
    }

    public Skills updateSkills(Character character, SkillsDto dto) {
        List<Skills> skillsList = getSkills(character.getName(), character.getUser());
        Skills skills = skillsList.isEmpty() ? new Skills() : skillsList.get(0);

        skills.setName(character.getName());
        skills.setEmail(character.getUser());

        if (dto == null) dto = new SkillsDto();

        skills.setAcrobatics(dto.getAcrobatics());
        skills.setAcrobatics_p(dto.isAcrobatics_p());

        skills.setAnimal_handling(dto.getAnimal_handling());
        skills.setAnimal_handling_p(dto.isAnimal_handling_p());

        skills.setArcane(dto.getArcane());
        skills.setArcane_p(dto.isArcane_p());

        skills.setAthletics(dto.getAthletics());
        skills.setAthletics_p(dto.isAthletics_p());

        skills.setDeception(dto.getDeception());
        skills.setDeception_p(dto.isDeception_p());

        skills.setHistory(dto.getHistory());
        skills.setHistory_p(dto.isHistory_p());

        skills.setInsight(dto.getInsight());
        skills.setInsight_p(dto.isInsight_p());

        skills.setIntimidation(dto.getIntimidation());
        skills.setIntimidation_p(dto.isIntimidation_p());

        skills.setInvestigation(dto.getInvestigation());
        skills.setInvestigation_p(dto.isInvestigation_p());

        skills.setMedicine(dto.getMedicine());
        skills.setMedicine_p(dto.isMedicine_p());

        skills.setNature(dto.getNature());
        skills.setNature_p(dto.isNature_p());

        skills.setPerception(dto.getPerception());
        skills.setPerception_p(dto.isPerception_p());

        skills.setPerformance(dto.getPerformance());
        skills.setPerformance_p(dto.isPerformance_p());

        skills.setPersuasion(dto.getPersuasion());
        skills.setPersuasion_p(dto.isPersuasion_p());

        skills.setReligion(dto.getReligion());
        skills.setReligion_p(dto.isReligion_p());

        skills.setSleight_of_hand(dto.getSleight_of_hand());
        skills.setSleight_of_hand_p(dto.isSleight_of_hand_p());

        skills.setStealth(dto.getStealth());
        skills.setStealth_p(dto.isStealth_p());

        skills.setSurvival(dto.getSurvival());
        skills.setSurvival_p(dto.isSurvival_p());

        return skillsRepository.save(skills);
    }

    public List<Skills> getSkills(String name, String email)
    {
        return skillsRepository.findCharactersBySkill(name, email);
    }
}
