package DND.DND.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Currency_Controller
{
    @Autowired Currency_Repository currency_repository;

    @PostMapping("/save_currency")
    public String currency(Currency currency)
    {
        currency_repository.save(currency);
        return "redirect:/characters";
    }
}
