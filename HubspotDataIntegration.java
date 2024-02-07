import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class integrates data from both hubspot tables in postgresql
 */
public class HubspotDataIntegration extends DatabaseConnection{

    public ArrayList<String> getHubspotContacts() {
        ArrayList<String> hubspotContacts = new ArrayList<String>();
        String hubspotContactInfoString = "SELECT \"Record ID\",\"First Name\",\"Last Name\",\"Email\",\"Phone Number\",\"Contact owner\",\"Associated Company\", \"LinkedIn Profile\",\"Job Title\",\"Associated Company IDs\" FROM public.\"hubspotContactData\"";

        try (Connection conn = connect(); Statement statement = conn.createStatement(); ResultSet result = statement.executeQuery(hubspotContactInfoString)) {
            while(result.next()) {
                int recordId = result.getInt("Record ID");
                String firstName = result.getString("First Name");
                String lastName = result.getString("Last Name");
                String email = result.getString("Email");
                String phoneNumber = result.getString("Phone Number");
                String contactOwner = result.getString("Contact owner");
                String associatedCompanyName = result.getString("Associated Company");
                String linkedInProfile = result.getString("LinkedIn Profile");
                String jobTitle = result.getString("Job Title");
                String associatedCompanyId = result.getString("Associated Company IDs");

                String formattedHubspotContacts = String.format(
                    "Record Id: %s\nName: %s %s\nAssociated Company: %s\nJob Title: %s\nContact Owner: %s\nLinkedIn Profile: %s\n",
                    recordId, firstName, lastName, associatedCompanyName, jobTitle, contactOwner, linkedInProfile
                );
                hubspotContacts.add(formattedHubspotContacts);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return hubspotContacts;
    } //end getHubspotContacts()

    public int getHubspotContactCount(){
        String hubspotContactCountString = "SELECT count(*) FROM public.\"hubspotContactData\"";
        int count = 0;

        try(Connection conn = connect(); Statement statement = conn.createStatement(); ResultSet result = statement.executeQuery(hubspotContactCountString)) {
            result.next();
            count = result.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }  //end getContactCount()

    public String extractHubspotName(String contactInfo) {
        int nameStartIndex = contactInfo.indexOf("Name: ");
        if (nameStartIndex >= 0) {
            int nameEndIndex = contactInfo.indexOf("Associated Company:", nameStartIndex);
            if (nameEndIndex >= 0) {
                String name = contactInfo.substring(nameStartIndex + 6, nameEndIndex).trim();
                // Split the name if it contains a comma
                if (name.contains(",")) {
                    name = name.split(",")[0].trim();
                }
                return name;
            }
        }
        return "";
    }

    public void displayHubspotContacts(ArrayList<String> hubspotContacts) {
        for (String contact : hubspotContacts) {
            System.out.println(contact);
            System.out.println("======================================");
        }
    } //end displayHubspotContacts()

} //end class
