package DND.DND.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import DND.DND.Characters.Character;

@Service
public class Currency_Service
{
    @Autowired
    public Currency_Repository currency_repository;

    public Currency saveCurrency(Character character, Currency_Dto currency_dto)
    {
        Currency currency = new Currency();
        currency.setName(character.getName());
        currency.setEmail(character.getUser());
        currency.setCopper(currency_dto.getCopper());
        currency.setSilver(currency_dto.getSilver());
        currency.setGold(currency_dto.getGold());
        currency.setPlatinum(currency_dto.getPlatinum());
        return currency_repository.save(currency);
    }

    public Currency getCurrency(String name, String email)
    {
        return currency_repository.findCurrenciesByNameAndEmail(name, email);
    }
}
