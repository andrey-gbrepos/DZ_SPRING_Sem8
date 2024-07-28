package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class LoggingAspect {


    @Pointcut("execution(* ru.gb.timesheet.service.TimesheetService.*(..))")
    public void timesheetServiceMethodsPointcut() {
    }

    @Pointcut("execution(* ru.gb.timesheet.service.ProjectService.*(..))")
    public void projectServiceMethodsPointcut() {
    }


//    @Before(value = "timesheetServiceMethodsPointcut()")
//    public void beforeTimesheetServiceFindById(JoinPoint jp) {
//        Long id = (Long) jp.getArgs()[0];
//        log.info("TimesheetServicefindById(), id = {}", id);
//        String methodName = jp.getSignature().getName();
//        log.info("Before -> TimesheetService#(), id = {}", methodName);
//    }

//    @After(value = "timesheetServiceMethodsPointcut()")
//    public void afterTimesheetServiceFindById(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//        log.info("After -> TimesheetService#(){}", methodName);
//    }
//
//    @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
//    public void afterThrowingTimesheetServiceFindById(JoinPoint jp, Exception ex) {
//        String methodName = jp.getSignature().getName();
//        log.info("AfterThrowing -> TimesheetService#(){} -> {}", methodName, ex.getClass().getName());
//    }

    @Around(value = "timesheetServiceMethodsPointcut()")
    public Object aroundTimesheetService(ProceedingJoinPoint pjp) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) pjp.getSignature();
        Optional<Object> args = Optional.ofNullable(pjp.getArgs()[0]);
        try {
            return pjp.proceed();
        } finally {
            if (!args.isPresent()) {
                log.info("{}()", pjp.getSignature().getName());
            }
            else {
                String ParametrType = codeSignature.getParameterTypes()[0].toString();
                ParametrType = ParametrType.substring((ParametrType.lastIndexOf(".") + 1));
                String ParametrArgs = pjp.getArgs()[0].toString();
                log.info("{} ({}={})", pjp.getSignature().getName(), ParametrType, ParametrArgs);
            }
        }
    }
}
