package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listTodos with multiple parameters
 */
public class FilterTodosByCombinedFiltersFromDB {

  @Test
  public void listTodosWithCombinedFilters() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("_id", Arrays.asList(new String[] { "58895985c1849992336c219b" }));
    Todo[] idTodos = db.listTodos(queryParams);
    assertEquals(1, idTodos.length, "Incorrect number of todos with id 58895985c1849992336c219b");

    queryParams.clear();
    queryParams.put("owner", Arrays.asList(new String[] { "Workman" }));
    Todo[] ownerTodos = db.listTodos(queryParams);
    assertEquals(49, ownerTodos.length, "Incorrect number of todos with owner Workman");

    queryParams.clear();
    queryParams.put("category", Arrays.asList(new String[] { "video games" }));
    queryParams.put("status", Arrays.asList(new String[] { "incomplete" }));
    Todo[] falseVideoGamesTodos = db.listTodos(queryParams);
    assertEquals(41, falseVideoGamesTodos.length, "Incorrect number of todos with category video games and status false");
  }

  @Test
  public void listTodosWithArbitraryFilters() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    queryParams.put("limit", Arrays.asList(new String[] { "12" }));
    queryParams.put("orderBy", Arrays.asList(new String[] { "category" }));
    Todo[] arbitraryTodos = db.listTodos(queryParams);
    int blancheCount = 0, trueCount = 0;
    for (int i = 0; i < 12; i++) {
      Todo currentTodo = arbitraryTodos[i];
      if (currentTodo.owner.equals("Blanche") && currentTodo.status == true) {
        blancheCount++;
        trueCount++;
      }
    }
    int precedingGroceryCount = 0;
  
    for (int i = 0; i < 3; i++) {
      Todo currentTodo = arbitraryTodos[i];
        if (currentTodo.category.equals("groceries")) {
            precedingGroceryCount++;    
        }
    }
    assertEquals(12, blancheCount, "Owners are not filtered correctly");
    assertEquals(12, trueCount, "Status is not filtered correctly");
    assertEquals(12, arbitraryTodos.length, "More than the expected number of todos appeared");
    assertEquals(3, precedingGroceryCount, "A different number of grocery categories were found.");
  }
}
