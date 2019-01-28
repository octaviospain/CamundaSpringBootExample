package com.transgressoft.csbe.plugins;

import org.assertj.core.util.Lists;
import org.camunda.feel.interpreter.Val;
import org.camunda.feel.spi.*;

import java.util.*;
import java.util.function.Function;

public class CoalesceFunctionProvider extends JavaFunctionProvider {

    @Override
    public List<JavaFunction> resolveFunctions(String functionName) {
        if (functionName.equals("coalesce")) {
            Function<List<Val>, Val> function =
                    arguments -> arguments.stream()
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElse(null);

            List<String> params = Lists.newArrayList("args");
            return Collections.singletonList(new JavaFunction(params, function, true));
        } else {
            return Collections.emptyList();
        }
    }
}
