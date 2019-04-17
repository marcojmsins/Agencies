import com.google.gson.Gson;
import spark.Spark;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.port;

public class apiMain
{
    public static void main(String[] args)
    {

        //URL EXAMPLE http://localhost:3456/agencias?site_id=MLA&payment_method_id=rapipago&latitude=-31.412971&longitude=-64.187&radius=300&sortBy=distance
        port(3456);
        final AgencyService agencyService = new AgencyImpl();
        Spark.get("/agencias", ((request, response) -> {
            response.type("application/json");
            String siteId = request.queryParams("site_id");
            String paymentMethodId = request.queryParams("payment_method_id");
            String latitude = request.queryParams("latitude");
            String longitude = request.queryParams("longitude");
            String radius =request.queryParams("radius");
            String sort=request.queryParams("sortBy");
            System.out.println(request.params());
            try {
                agencyService.loadAgencies(siteId, paymentMethodId, latitude, longitude, radius);

                if(sort!=null) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCES, new Gson().toJsonTree(agencyService.getSortAgencies(sort))));
                }
                else{
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCES, new Gson().toJsonTree(agencyService.getAgencies())));
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ocurrio un error al traer Agencias");
            }
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCES, new Gson().toJsonTree(agencyService.getAgencies())));
        }));
    }



}



