package com.googlecode.linkedlisp.functions.text;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Split extends Function {

    @Override
    public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));
        String regex = s.evaluate(params.get(1)).toString().replace("\\\\", "\\");
        if (value == null) return null;
        return new ArrayList(Arrays.asList(value.toString().split(regex)));
    }

}
