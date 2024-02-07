import java.util.ArrayList;

public class Management {
    private SalesNavDataIntegration salesNavData;
    private HubspotDataIntegration hubspotData;
    private ArrayList<String> matchedContacts;

    public Management() {
        salesNavData = new SalesNavDataIntegration();
        hubspotData = new HubspotDataIntegration();
        matchedContacts = new ArrayList<>();
    }

    public void compareContacts() {
        // Reset the list each time compareContacts() is called
        matchedContacts.clear();

        ArrayList<String> salesNavContacts = salesNavData.getContacts();
        ArrayList<String> hubspotContacts = hubspotData.getHubspotContacts();

        for (String salesNavContact : salesNavContacts) {
            boolean matchFound = false;
            String salesNavName = salesNavData.extractSalesNavName(salesNavContact);
            for (String hubspotContact : hubspotContacts) {
                String hubspotName = hubspotData.extractHubspotName(hubspotContact);
                if (salesNavName.equals(hubspotName)) {
                    matchFound = true;
                    matchedContacts.add(salesNavContact);
                    System.out.println(salesNavContact + "\n==========\n" + hubspotContact);
                    break;
                }
            }
        }
    }

    public int countMatchedContacts() {
        return matchedContacts.size();
    }

    public ArrayList<String> getMatchedContacts() {
        return matchedContacts;
    }
}
