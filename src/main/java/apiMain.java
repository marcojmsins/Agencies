import com.google.gson.Gson;
import spark.Spark;
import java.io.IOException;

import static spark.Spark.port;

public class apiMain
{
    public static void main(String[] args)
    {
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



