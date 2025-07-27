package com.bankapp.transfer.aspect;

import com.bankapp.transfer.dto.ResponseDto;
import com.bankapp.transfer.feign.blocker.BlockerFeignClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SuspiciousOperationAspect {

    BlockerFeignClient blockerFeignClient;

    @Around("execution(* com.bankapp.transfer.service.TransferService.processTransfer(..))")
    public Object checkSuspiciousOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean suspicious = blockerFeignClient.isSuspicious();
        
        if (suspicious) {
            ResponseDto response = new ResponseDto();
            response.setHasErrors(true);
            response.getErrors().add("Подозрительная операция.");
            return response;
        }
        
        return joinPoint.proceed();
    }
} 