package fudan.se.lab4.controller;

import fudan.se.lab4.service.ClientService;
import fudan.se.lab4.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired(required = false)
    private ClientService clientService;

    @PostMapping(value = "/client/getCountryList")
    public List<String> getCountryList() {
        return clientService.getCountryList();
    }

    @PostMapping(value = "/client/getCountry")
    public String getCountry() {
        return clientService.getCountry();
    }

    @PostMapping(value = "/client/setLocaleByCountry")
    public void setLocaleByCountry() {
        clientService.setLocaleByCountry();
    }

    @PostMapping(value = "/client/getCurrencyList")
    public List<String> getCurrencyList() {
        return clientService.getCurrencyList();
    }

    @PostMapping(value = "/client/getCurrencyCountry")
    public String getCurrencyCountry() {
        return clientService.getCurrencyCountry();
    }

    @PostMapping(value = "/client/getCurrencyLabel")
    public String getCurrencyLabel() {
        return clientService.getCurrencyLabel();
    }

    @PostMapping(value = "/client/getDrinkList")
    public List<String> getDrinkList() {
        return clientService.getDrinkList();
    }

    @PostMapping(value = "/client/getIngredientList")
    public List<String> getIngredientList() {
        return clientService.getIngredientList();
    }

    @PostMapping(value = "/client/getStrategyClassList")
    public List<String> getStrategyClassList() {
        return clientService.getStrategyClassList();
    }

    @PostMapping(value = "/client/getStrategyList")
    public List<String> getStrategyList() {
        return clientService.getStrategyList();
    }

    @PostMapping(value = "/client/addStrategy")
    public void addStrategy(@RequestBody List<String> strategies) {
        clientService.addStrategy(strategies);
    }

    @PostMapping(value = "/client/deleteStrategy")
    public void deleteStrategy(@RequestBody Strategy strategy) {
        clientService.deleteStrategy(strategy);
    }

    @PostMapping(value = "/client/getStrategies")
    public List<Strategy> getStrategies() {
        return clientService.getStrategies();
    }

}
