import java.util.ArrayList;
import java.util.Scanner;

public class ToolDriver {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner input = new Scanner(System.in);

        DatabaseConnection dbConnect = new DatabaseConnection(); //connection object
                dbConnect.connect();
        SalesNavDataIntegration snIntegrate = new SalesNavDataIntegration(); //sales nav object
        HubspotDataIntegration hsIntegrate = new HubspotDataIntegration(); // hubspot object
        Management management = new Management(); //management object
        ArrayList<String> hubSpotContacts = hsIntegrate.getHubspotContacts(); //arraylist of hs contacts
        ArrayList<String> salesNavContacts = snIntegrate.getContacts(); //arraylist of salesnav contacts

        while (!exit) {
            System.out.println("Choose an operation: ");
            System.out.println("1. Count contacts");
            System.out.println("2. Display Sales Nav contacts");
            System.out.println("3. Display Hubspot contacts");
            System.out.println("4. Display Matched Contacts (Run this operation to see how many matches there are.2)");
            System.out.println("5. Exit");

            int choice = input.nextInt();

            switch (choice) {
                case 1: 
                    int contactCount = snIntegrate.getContactCount();
                    System.out.println(contactCount + " contacts found.");
                    int hubspotContactCount = hsIntegrate.getHubspotContactCount();
                    System.out.println(hubspotContactCount + " hubspot contacts found.");
                    System.out.println(management.countMatchedContacts() + " matched contacts found.\n");

                    break;
                case 2:
                    snIntegrate.displayContacts(salesNavContacts);
                    break;
                case 3:
                    hsIntegrate.displayHubspotContacts(hubSpotContacts);
                    break;
                case 4:
                    management.compareContacts();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");


            } //end switch
        } //end while loop
        input.close();
    } //end main()
} //end class
