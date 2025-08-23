package com.bankapp.blocker.service;

import java.util.Random;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class BlockerService {

    static int CHANCE_OF_BLOCKING_OPERATION_PERCENT = 15;

    public boolean isSuspicious() {
        Random random = new Random();
        int roll = random.nextInt(100);
        return roll < CHANCE_OF_BLOCKING_OPERATION_PERCENT;
    }

}
