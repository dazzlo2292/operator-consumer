package ru.app.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.app.core.dtos.MnpInformation;
import ru.app.core.dtos.PhoneInformation;
import ru.app.core.services.OperatorService;

@RequiredArgsConstructor
@RestController
public class ApiController {

    private final OperatorService operatorService;

    @GetMapping("/operators/phone/info")
    public PhoneInformation getPhoneInformation(@RequestParam String phone) {
        return operatorService.getPhoneInformation(phone);
    }

    @PostMapping("/operators/mnp")
    public void addMnpInformation(@RequestBody MnpInformation mnpInformation) {
        operatorService.addMnpPrefix(mnpInformation);
    }

    @PostMapping("/operators/sync")
    public void syncOperatorInformation() {
        operatorService.syncOperators();
    }
}
