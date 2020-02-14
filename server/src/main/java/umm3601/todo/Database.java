package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * A fake "database" of todo info
 * <p>
 * Since we don't want to complicate this lab with a real database, we're going
 * to instead just read a bunch of todo data from a specified JSON file, and
 * then provide various database-like methods that allow the `TodoController` to
 * "query" the "database".
 */
public class Database {

  private Todo[] allTodos;

  public Database(String todoDataFile) throws IOException {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
    allTodos = gson.fromJson(reader, Todo[].class);
  }

  public int size() {
    return allTodos.length;
  }

  /**
   * Get the single todo specified by the given ID. Return `null` if there is no
   * todo with that ID.
   *
   * @param id the ID of the desired todo
   * @return the todo with the given ID, or null if there is no todo with that ID
   */
  public Todo getTodo(String id) {
    return Arrays.stream(allTodos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
  }

  /**
   * Get an array of all the todos satisfying the queries in the params.
   *
   * @param queryParams map of key-value pairs for the query
   * @return an array of all the todos matching the given criteria
   */
  public Todo[] listTodos(Map<String, List<String>> queryParams) {
    Todo[] filteredTodos = allTodos;

    // Filter ID if defined
    if (queryParams.containsKey("_id")) {
      String targetID = queryParams.get("_id").get(0);
      filteredTodos = filterTodosByID(filteredTodos, targetID);
    }
    // Filter owner if defined
    if (queryParams.containsKey("owner")){
      String targetOwner = queryParams.get("owner").get(0);
      filteredTodos = filterTodosByOwner(filteredTodos, targetOwner);
    }
    // Filter status if defined
    if (queryParams.containsKey("status")) {
      boolean targetStatus = Boolean.parseBoolean(queryParams.get("status").get(0));
      filteredTodos = filterTodosByStatus(filteredTodos, targetStatus);
    }
    // Filter body if defined
    if (queryParams.containsKey("body")){
      String targetBody = queryParams.get("body").get(0);
      filteredTodos = filterTodosByBody(filteredTodos, targetBody);
    }
    // Filter category if defined
    if (queryParams.containsKey("category")){
      String targetCategory = queryParams.get("category").get(0);
      filteredTodos = filterTodosByCategory(filteredTodos, targetCategory);
    }
       
    return filteredTodos;
  }
  /**
   * Get an array of all the todos having the target ID.
   *
   * @param todos     the list of todos to filter by ID
   * @param targetID the target ID to look for
   * @return an array of all the todos from the given list that have the target
   *         ID
   */
  public Todo[] filterTodosByID(Todo[] todos, String targetID) {
    return Arrays.stream(todos).filter(x -> x._id.equals(targetID)).toArray(Todo[]::new);
  }
/**
   * Get an array of all the todos having the target owner.
   *
   * @param todos         the list of todos to filter by owner
   * @param targetOwner the target owner to look for
   * @return an array of all the todos from the given list that have the target
   *         owner
   */
  public Todo[] filterTodosByOwner(Todo[] todos, String targetOwner) {
    return Arrays.stream(todos).filter(x -> x.owner.equals(targetOwner)).toArray(Todo[]::new);
  }

  /**
   * Get an array of all the todos having the target status.
   *
   * @param todos         the list of todos to filter by status
   * @param targetStatus the target status to look for
   * @return an array of all the todos from the given list that have the target
   *         status
   */
  public Todo[] filterTodosByStatus(Todo[] todos, boolean targetStatus) {
    return Arrays.stream(todos).filter(x -> x.status == targetStatus).toArray(Todo[]::new);
  }

/**
   * Get an array of all the todos having the target body.
   *
   * @param todos         the list of todos to filter by body
   * @param targetBody the target body to look for
   * @return an array of all the todos from the given list that have the target
   *         body
   */
  public Todo[] filterTodosByBody(Todo[] todos, String targetBody) {
    return Arrays.stream(todos).filter(x -> x.body.equals(targetBody)).toArray(Todo[]::new);
  }

  /**
   * Get an array of all the todos having the target category.
   *
   * @param todos         the list of todos to filter by category
   * @param targetCategory the target category to look for
   * @return an array of all the todos from the given list that have the target
   *         category
   */
  public Todo[] filterTodosByCategory(Todo[] todos, String targetCategory) {
    return Arrays.stream(todos).filter(x -> x.category.equals(targetCategory)).toArray(Todo[]::new);
  }

}
