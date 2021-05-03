import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }

    public void addMenu() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Chocolate Fudge", 200);
        restaurant.addToMenu("Tuna Sandwich", 300);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.parse("13:00:00");
        Restaurant restaurant1 = Mockito.spy(restaurant);
        Mockito.doReturn(currentTime).when(restaurant1).getCurrentTime();
        boolean isRestaurantOpen = restaurant1.isRestaurantOpen();
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.parse("23:00:00");
        Restaurant restaurant1 = Mockito.spy(restaurant);
        Mockito.doReturn(currentTime).when(restaurant1).getCurrentTime();
        boolean isRestaurantOpen = restaurant1.isRestaurantOpen();
        assertFalse(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_get_current_time_return_current_system_time() {
        assertTrue(LocalTime.now().equals(restaurant.getCurrentTime()));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        addMenu();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        addMenu();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        addMenu();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>>>ORDERS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void select_items_from_menu_should_return_total_order_cost() {
        //arrange
        addMenu();
        //act
        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Chocolate Fudge");
        selectedItems.add("Tuna Sandwich");
        int totalCost = restaurant.calculateTotalCost();
        //assert
        assertEquals(500, totalCost);
    }
    //>>>>>>>>>>>>>>>>>>>>>>ORDERS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}