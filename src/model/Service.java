package model;

import java.util.ArrayList;

public class Service {
    private ArrayList<User> listOfUsers = new ArrayList<>();

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(ArrayList<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    /*******************************************************************************************************************
     * This method adds the user to a listOfUsers
     ******************************************************************************************************************/
    public void addUsersToListOfUsers(User user) {
        listOfUsers.add(user);
    }


}
