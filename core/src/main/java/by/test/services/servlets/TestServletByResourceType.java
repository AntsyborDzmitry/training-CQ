package by.test.services.servlets;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import java.io.IOException;


@SlingServlet(
        resourceTypes = {"bin/myTestResourceType"},
        methods = {"GET"}
)
public class TestServletByResourceType extends SlingSafeMethodsServlet {


    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        String firstName = request.getParameter("name");
        firstName = verifyProps("firstName", firstName);

        String lastName = verifyProps("lastName", firstName);
        lastName = StringUtils.reverse(lastName);

        String email = request.getParameter("email");
        email = verifyProps("email", email);


        JSONObject obj = new JSONObject();
        try {
            obj.put("firstName",firstName);
            obj.put("lastName",lastName);
            obj.put("address",email.toUpperCase());

            obj.write(response.getWriter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String verifyProps(String key, String val) {
         if(val != null && !val.isEmpty()){
            return val.toUpperCase();
         }
         return key + " unrecognized";
    }
}
