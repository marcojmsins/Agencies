import java.io.IOException;
import java.util.Collection;


import java.util.Collection;

public interface AgencyService {

    public void addAgency(Agency agency);
    public Collection<Agency> getAgencies();
    public Collection<Agency> loadAgencies(String siteId, String  paymentMethodId, String latitude, String longitude, String radius)  throws IOException;
    public Agency getAgency(String id);
    public void deleteAgency(String id);
    public boolean existAgency(String id);
    public Collection <Agency> getSortAgencies(String sortBy);




}