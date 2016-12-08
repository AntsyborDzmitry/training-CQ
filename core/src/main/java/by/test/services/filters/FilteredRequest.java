package by.test.services.filters;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;

public class FilteredRequest extends HttpServletRequestWrapper {

    public FilteredRequest(ServletRequest request) {
        super((HttpServletRequest)request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return addPostfix(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String [] values = super.getParameterValues(name);
        if(values != null){
            Arrays.asList(values).forEach(this::addPostfix);
        }
        return values;
    }

    private String addPostfix (String param) {
        return  param + " - filtered";
    }
}
