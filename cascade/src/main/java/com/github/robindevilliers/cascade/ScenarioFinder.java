package com.github.robindevilliers.cascade;


import com.github.robindevilliers.cascade.modules.Scanner;
import com.github.robindevilliers.cascade.annotations.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ScenarioFinder {
    
    List<Scenario> findScenarios(String[] paths, Scanner classpathScanner){
        List<Scenario> scenarios = new ArrayList<>();

        for (String path : paths) {
            classpathScanner.initialise(path);
            Set<Class<?>> steps = classpathScanner.getTypesAnnotatedWith(Step.class);

            for (Class<?> step : steps) {
                findScenarios(scenarios, step, classpathScanner, step);
            }
        }
        return scenarios;
    }

    private void findScenarios(List<Scenario> scenarios, Class<?> clazz, Scanner classpathScanner, Class<?> stateClazz) {

        if (Arrays.stream(stateClazz.getAnnotations())
                .noneMatch(a -> a.annotationType().equals(Step.class))){
            return; //Reflections library will return classes that implement interfaces that have annotations.  We don't want this yet.
        }

        if (clazz.isInterface()) {
            Set<Class> subtypes = classpathScanner.getSubTypesOf(clazz);
            for (Class subType : subtypes) {
                findScenarios(scenarios, subType, classpathScanner, stateClazz);
            }
        } else {
            scenarios.add(new Scenario(clazz, stateClazz));
        }
    }
}
