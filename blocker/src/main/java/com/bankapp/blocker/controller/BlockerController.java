package com.bankapp.blocker.controller;

import com.bankapp.blocker.service.BlockerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/blocker")
public class BlockerController {

    BlockerService blockerService;

    @PostMapping("/is-suspicious")
    public boolean isSuspicious() {
        return blockerService.isSuspicious();
    }

}
