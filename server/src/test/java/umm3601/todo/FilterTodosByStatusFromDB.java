package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import umm3601.todo.tDatabase;
import umm3601.todo.Todo;

/**
 * Tests umm3601.Todo.tDatabase filterTodosByStatus and listTodos with status query
 * parameters
 */
public class FilterTodosByStatusFromDB {

  @Test
  public void filterTodosByStatus() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] statusTrueTodos = db.filterTodosByStatus(allTodos, true);
    assertEquals(143, statusTrueTodos.length, "Incorrect number of Todos with status true");

    Todo[] statusFalseTodos = db.filterTodosByStatus(allTodos, false);
    assertEquals(157, statusFalseTodos.length, "Incorrect number of Todos with status false");
  }

  @Test
  public void listTodosWithStatusFilter() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("status", Arrays.asList(new String[] { "true" }));
    Todo[] statusTrueTodos = db.listTodos(queryParams);
    int statusTrueTodosLength = statusTrueTodos.length;
    assertEquals(143, statusTrueTodosLength, "Incorrect number of Todos with status true");

    queryParams.put("status", Arrays.asList(new String[] { "false" }));
    Todo[] statusFalseTodos = db.listTodos(queryParams);
    int statusFalseTodosLength = statusFalseTodos.length;
    assertEquals(157, statusFalseTodosLength, "Incorrect number of Todos with status false");

    queryParams.clear();
    Todo[] totalTodos = db.listTodos(queryParams);
    assertEquals(totalTodos.length, statusTrueTodosLength + statusFalseTodosLength, "Incorrect database entry total");

  }
}
