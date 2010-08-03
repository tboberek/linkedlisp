package com.googlecode.linkedlisp.testing;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import static org.junit.Assert.*;

public class Assert extends Function {

    @Override
    public Object execute(Environment s, List params) throws Exception {
        Object x = s.resolve(params.get(0));
        if (params.size() > 1) assertNotNull(s.resolveAsString(params.get(1)), x);
        else assertNotNull(x);
        return x;
    }

}
