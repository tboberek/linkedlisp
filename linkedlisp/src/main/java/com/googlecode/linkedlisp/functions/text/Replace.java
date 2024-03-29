package com.googlecode.linkedlisp.functions.text;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;
import java.util.List;

public class Replace extends Function {

    @Override
    public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));
        String regex = s.evaluate(params.get(1)).toString().replace("\\\\", "\\");
        String replacement = s.evaluate(params.get(2)).toString().replace("\\\\", "\\");
        if (value == null) return null;
        return value.toString().replaceAll(regex, replacement);
    }

}
