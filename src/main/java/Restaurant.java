import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    // Method to determine if the Restaurant is open or closed.
    public boolean isRestaurantOpen() {
        boolean is_Restaurant_Open = false;

        // The Restaurant is marked as Open if the current time is after its designated opening time and
        // before its designated closing time.
        if(getCurrentTime().isAfter(openingTime) && getCurrentTime().isBefore(closingTime)) {
            is_Restaurant_Open = true;
        }

        return is_Restaurant_Open;
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    // The getter method for menu attribute has been defined here
    // as menu is initialized with private access modifier.
    public List<Item> getMenu() {
        return this.menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    // Method to return the total cost incurred by the user for their chosen items
    public int returnsCostIncurredForOrderPlaced(List<String> selectedItems){
        return -1;
    }
}
