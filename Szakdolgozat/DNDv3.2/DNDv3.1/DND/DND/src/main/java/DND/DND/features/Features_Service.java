package DND.DND.features;

import DND.DND.Characters.Character;
import DND.DND.Characters.CharacterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Features_Service
{
    @Autowired
    private Features_Repository features_repository;

    public Features saveFeatures(Features_Dto features_dto, CharacterDto characterDto)
    {
        Features features = new Features();
        features.setName(characterDto.getName());
        features.setEmail(characterDto.getUser());
        features.setEgy(features_dto.getEgy());
        features.setKetto(features_dto.getKetto());
        features.setHarom(features_dto.getHarom());
        features.setNegy(features_dto.getNegy());
        features.setOt(features_dto.getOt());
        features.setHat(features_dto.getHat());
        features.setHet(features_dto.getHet());
        features.setNyolc(features_dto.getNyolc());
        features.setKilenc(features_dto.getKilenc());
        features.setTiz(features_dto.getTiz());
        features.setTizenegy(features_dto.getTizenegy());
        features.setTizenkette(features_dto.getTizenkette());
        features.setTizenharom(features_dto.getTizenharom());
        features.setTizennegy(features_dto.getTizennegy());
        features.setTizenot(features_dto.getTizenot());
        features.setTizenhat(features_dto.getTizenhat());
        features.setTizenhet(features_dto.getTizenhet());
        features.setTizennyolc(features_dto.getTizennyolc());
        features.setTizenkilenc(features_dto.getTizenkilenc());
        features.setHusz(features_dto.getHusz());
        return features_repository.save(features);
    }

    public Features updateFeatures(Features_Dto features_dto, Character character)
    {
        Features features = getFeatures(character.getName(), character.getUser());
        features.setEgy(features_dto.getEgy());
        features.setKetto(features_dto.getKetto());
        features.setHarom(features_dto.getHarom());
        features.setNegy(features_dto.getNegy());
        features.setOt(features_dto.getOt());
        features.setHat(features_dto.getHat());
        features.setHet(features_dto.getHet());
        System.out.println(features.getNyolc());
        features.setNyolc(features_dto.getNyolc());
        features.setKilenc(features_dto.getKilenc());
        features.setTiz(features_dto.getTiz());
        features.setTizenegy(features_dto.getTizenegy());
        features.setTizenkette(features_dto.getTizenkette());
        features.setTizenharom(features_dto.getTizenharom());
        features.setTizennegy(features_dto.getTizennegy());
        features.setTizenot(features_dto.getTizenot());
        features.setTizenhat(features_dto.getTizenhat());
        features.setTizenhet(features_dto.getTizenhet());
        features.setTizennyolc(features_dto.getTizennyolc());
        features.setTizenkilenc(features_dto.getTizenkilenc());
        features.setHusz(features_dto.getHusz());
        return features_repository.save(features);
    }

    public Features getFeatures(String name, String email)
    {
       return features_repository.findFeaturesByCharacter(name, email);
    }
}
