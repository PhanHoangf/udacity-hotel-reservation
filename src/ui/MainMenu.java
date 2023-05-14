package ui;

public class MainMenu {
    private boolean isAdminMenu = false;

    public void setAdminMenu(boolean adminMenu) {
        isAdminMenu = adminMenu;
    }

    public static void main(String[] args) {
        generateMainMenu();
        System.out.println("//-------------------------------------//");
        generateAdminMenu();
    }

    public static void generateMainMenu() {
        String option1 = "1. Find and reserve a room";
        String option2 = "2. See my reservations";
        String option3 = "3. Create an account";
        String option4 = "4. Admin";
        String option5 = "5. Exit";
        String[] mainMenuOptions = new String[]{option1, option2, option3, option4, option5};

        for (String option : mainMenuOptions) {
            System.out.println(option);
        }
    }

    private static void generateAdminMenu() {
        String option1 = "1. See all Customers";
        String option2 = "2. See all Rooms";
        String option3 = "3. See all Reservations";
        String option4 = "4. Add a room";
        String option5 = "5. Back to Main Menu";
        String[] mainMenuOptions = new String[]{option1, option2, option3, option4, option5};

        for (String option : mainMenuOptions) {
            System.out.println(option);
        }
    }

    private Integer userInput() {
        return null;
    }

    private void handleUserInput(int userInput) {
        switch (userInput) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                System.out.println("Please choose options that are showed in the menu");
        }
    }
}
