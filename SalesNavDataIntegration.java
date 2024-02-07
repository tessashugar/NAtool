import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * This class integrates data from the salesNavData table in postgresql
 */
public class SalesNavDataIntegration extends DatabaseConnection {
    ArrayList<String> contacts = new ArrayList<String>();

    public int getContactCount(){
        String contactCountString = "SELECT count(*) FROM public.\"salesNavData\"";
        int count = 0;

        try(Connection conn = connect(); Statement statement = conn.createStatement(); ResultSet result = statement.executeQuery(contactCountString)) {
            result.next();
            count = result.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }  //end getContactCount()

    public ArrayList<String> getContacts() {
        ArrayList<String> contacts = new ArrayList<>();
    
        String contactInfoString = "SELECT \"firstName\",\"lastName\",\"companyName\",\"title\",\"summary\",\"titleDescription\",\"industry\",\"companyLocation\",\"location\",\"durationInCompany\",\"connectionDegree\",\"sharedConnectionsCount\",\"linkedInProfileUrl\",\"titleDescription\" FROM public.\"salesNavData\"";
    
        try (Connection conn = connect(); Statement statement = conn.createStatement(); ResultSet result = statement.executeQuery(contactInfoString)) {
            while (result.next()) {
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String companyName = result.getString("companyName");
                String title = result.getString("title");
                String companyLocation = result.getString("companyLocation");
                String connectionDegree = result.getString("connectionDegree");
                String linkedInProfileUrl = result.getString("linkedInProfileUrl");
    
                String formattedContact = String.format(
                    "Name: %s %s\nCompany: %s\nTitle: %s\nLocation: %s\nConnection Degree: %s\nLinkedIn Profile: %s\n",
                    firstName, lastName, companyName, title, companyLocation, connectionDegree, linkedInProfileUrl
                );
                contacts.add(formattedContact);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contacts;
    } //end getContacts()

    public String extractSalesNavName(String contactInfo) {
        int nameStartIndex = contactInfo.indexOf("Name: ");
        if (nameStartIndex >= 0) {
            int nameEndIndex = contactInfo.indexOf("\n", nameStartIndex);
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
    } //end extractSalesNavName()

    public void displayContacts(ArrayList<String> contacts) {
        for (String contact : contacts) {
            System.out.println(contact);
            System.out.println("======================================");
        }
    } //end displayContacts()

} //end class

