package DND.DND.saving_throws;

import DND.DND.Characters.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import DND.DND.Characters.Character;

import java.util.List;


@Service
public class Saving_throws_service
{
    @Autowired
    Saving_throws_repository saving_throws_repository;

    public Saving_throws save_saving_throws(Character character, Saving_throws_dto dto) {
        if (dto == null) dto = new Saving_throws_dto(0, null, null, 0,false, 0,false, 0,false, 0,false, 0,false, 0,false);

        Saving_throws st = new Saving_throws();
        st.setName(character.getName());
        st.setEmail(character.getUser());

        st.setStr(dto.getStr());
        st.setStr_p(dto.isStr_p());

        st.setDex(dto.getDex());
        st.setDex_p(dto.isDex_p());

        st.setCon(dto.getCon());
        st.setCon_p(dto.isCon_p());

        st.setIntelligence(dto.getInt_save());
        st.setIntelligence_p(dto.isInt_save_p());

        st.setWis(dto.getWis());
        st.setWis_p(dto.isWis_p());

        st.setCha(dto.getCha());
        st.setCha_p(dto.isCha_p());

        return saving_throws_repository.save(st);
    }

    public List<Saving_throws> get_saving_throws(String name, String email)
    {
        return saving_throws_repository.findSavingThrowsByEmailAndName(name, email);
    }

    public Saving_throws update_saving_throws(Character character, Saving_throws_dto dto) {
        List<Saving_throws> list = saving_throws_repository
                .findSavingThrowsByEmailAndName(character.getName(), character.getUser());
        Saving_throws st = list.isEmpty() ? new Saving_throws() : list.get(0);

        st.setName(character.getName());
        st.setEmail(character.getUser());

        st.setStr(dto.getStr());
        st.setStr_p(dto.isStr_p());

        st.setDex(dto.getDex());
        st.setDex_p(dto.isDex_p());

        st.setCon(dto.getCon());
        st.setCon_p(dto.isCon_p());

        st.setIntelligence(dto.getInt_save());
        st.setIntelligence_p(dto.isInt_save_p());

        st.setWis(dto.getWis());
        st.setWis_p(dto.isWis_p());

        st.setCha(dto.getCha());
        st.setCha_p(dto.isCha_p());

        return saving_throws_repository.save(st);
    }

}
