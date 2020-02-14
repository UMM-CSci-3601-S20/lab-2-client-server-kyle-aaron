package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.user.Database listUsers with _age_ and _company_ query
 * parameters
 */
public class FilterTodosByCombinedFiltersFromDB {

  @Test
  public void listTodosWithCombinedFilters() throws IOException {
    Database db = new Database("/todos.json");
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
    queryParams.put("status", Arrays.asList(new String[] { "false" }));
    Todo[] falseVideoGamesTodos = db.listTodos(queryParams);
    assertEquals(41, falseVideoGamesTodos.length, "Incorrect number of todos with category video games and status false");
  }
}
