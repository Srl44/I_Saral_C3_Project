import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void setupNeededForAllTestCases() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    // Setup for testing displayDetails() Method which has a Console output
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // We reassign the standard output stream to a new PrintStream with a ByteArrayOutputStream
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    // Restoring the standard output stream to its original state
    public void tearDown() {
        System.setOut(standardOut);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        // Mocking the Current Time method to return a time within the range of restaurant's operation.
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("16:20:00"));
        assertTrue(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        // Mocking the Current Time method to return a time outside the range of restaurant's operation.
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("07:30:00"));
        assertFalse(mockRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>CALCULATE COST INCURRED<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void return_total_cost_incurred_of_selected_items_after_menu_items_are_selected(){
        List<String> selectedItems = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int totalCostIncurred = restaurant.returnsCostIncurredForOrderPlaced(selectedItems);
        assertEquals(388,totalCostIncurred);
    }

    @Test
    public void return_total_cost_incurred_as_0_when_no_items_are_selected(){
        List<String> selectedItems = new ArrayList<String>();
        int totalCostIncurred = restaurant.returnsCostIncurredForOrderPlaced(selectedItems);
        assertEquals(0,totalCostIncurred);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<CALCULATE COST INCURRED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>TEST CASES TO IMPROVE CODE COVERAGE<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void view_restaurant_details(){
        setUp();

        restaurant.displayDetails();
        assertEquals("Restaurant:Amelie's cafe\n" +
                "Location:Chennai\n" +
                "Opening time:10:30\n" +
                "Closing time:22:00\n" +
                "Menu:\n" +
                "[Sweet corn soup:119\n" +
                ", Vegetable lasagne:269\n" +
                "]", outputStreamCaptor.toString().trim()); // The trim method is added to remove
                                                            // the new line that System.out.println() adds
        tearDown();
    }
    //<<<<<<<<<<<<<<<<<<<<<<TEST CASES TO IMPROVE CODE COVERAGE>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}