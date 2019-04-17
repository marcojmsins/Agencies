import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.*;


public class AgencyImpl implements AgencyService {
    private HashMap<String, Agency> agencyMap;

    public AgencyImpl() {
        agencyMap = new HashMap<String, Agency>();
    }

    public void addAgency(Agency agency) {
        agencyMap.put(agency.getId(), agency);
    }

    public Collection<Agency> getAgencies() {
        return agencyMap.values();
    }

    public Collection<Agency> loadAgencies(String siteId, String paymentMethodId, String latitude, String longitude, String radius) throws IOException {
        String url = "https://api.mercadolibre.com/sites/"+siteId+"/payment_methods/"+paymentMethodId+"/agencies?near_to="+latitude+","+longitude+","+radius;
               try {
            String data = RestClient.readUrl(url);
            JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
            Agency[] agencies = new Gson().fromJson(jsonObject.get("results").getAsJsonArray(), Agency[].class);
            for (Agency agency : agencies) {
                agencyMap.put(agency.getId(), agency);
            }
        } catch (IOException e) {
            throw e;
        }
        return agencyMap.values();
    }

    public Agency getAgency(String id) {
        return agencyMap.get(id);
    }

    public void deleteAgency(String id) {
        agencyMap.remove(id);
    }

    public boolean existAgency(String id) {
        return agencyMap.containsKey(id);
    }

    public Collection <Agency> getSortAgencies(String sortBy) {
        List<Agency> agencyByDist = new ArrayList<>(getAgencies());
        switch(sortBy)
        {
            case "distance":
                Collections.sort(agencyByDist, Comparator.comparing(Agency::getDistance));
                System.out.println("Distance");
                break;

            case "payment_method_id":
                Collections.sort(agencyByDist, Comparator.comparing(Agency::getPayment_method_id));
                System.out.println("Payment Method Id");
                break;

            case "agency_code":
                Collections.sort(agencyByDist, Comparator.comparing(Agency::getAgency_code));
                System.out.println("Agency Code");
                break;

            case "correspondent_id":
                Collections.sort(agencyByDist, Comparator.comparing(Agency::getCorrespondent_id));
                System.out.println("Correspondent Id");
                break;

            default:
                System.out.println("No Sort Selected");
        }
        return agencyByDist;
    }

}
