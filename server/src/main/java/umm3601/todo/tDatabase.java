package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
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
public class tDatabase {

  private Todo[] allTodos;
  private OwnerComparator ownerComparator = new OwnerComparator();
  private StatusComparator statusComparator = new StatusComparator();
  private BodyComparator bodyComparator = new BodyComparator();
  private CategoryComparator categoryComparator = new CategoryComparator();

  public tDatabase(String todoDataFile) throws IOException {
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
      boolean targetStatus = true;
      String targetStatusStr = queryParams.get("status").get(0);
      if(targetStatusStr.equals("complete")) {
        targetStatus = true;
      }
      else{
       targetStatus = false;
      }
      filteredTodos = filterTodosByStatus(filteredTodos, targetStatus);
    }

    // Filter body if defined
    if (queryParams.containsKey("contains")){
      String targetBody = queryParams.get("contains").get(0);
      filteredTodos = filterTodosByBody(filteredTodos, targetBody);
    }

    // Filter category if defined
    if (queryParams.containsKey("category")){
      String targetCategory = queryParams.get("category").get(0);
      filteredTodos = filterTodosByCategory(filteredTodos, targetCategory);
    }

    //Filter number of todos if defined
    if (queryParams.containsKey("limit")){
      int targetLimit = Integer.parseInt(queryParams.get("limit").get(0));
      filteredTodos = filterTodosByLimit(filteredTodos, targetLimit);
    }
    
    // Filter by orderBy if defined
    if (queryParams.containsKey("orderBy")){
      String targetOrder = queryParams.get("orderBy").get(0).toString();
      filteredTodos = filterTodosByOrder(filteredTodos, targetOrder);
    }
    
    // Filter out null values, if present
    filteredTodos = filterNullValues(filteredTodos);    
    
    return filteredTodos;
  }
  
  /**
   * Get an array of all the todos with null values removed.
   *
   * @param todos    the list of todos to filter null from
   * @return an array of all the todos from the given list void of all null values
   */
  private Todo[] filterNullValues(Todo[] filteredTodos) {
    return Arrays.stream(filteredTodos).filter(x -> x != null).toArray(Todo[]::new);
  }

  /**
   * Get an array of all the todos having the target ID.
   *
   * @param todos    the list of todos to filter by ID
   * @param targetID the target ID to look for
   * @return an array of all the todos from the given list that have the target ID
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
    return Arrays.stream(todos).filter(x -> x.body.contains(targetBody)).toArray(Todo[]::new);
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

  /**
   * Get an array of the limit size.
   *
   * @param todos         the list of todos to filter by limit
   * @param targetLimit the target limit
   * @return an array of the limit size
   *
   */
  public Todo[] filterTodosByLimit(Todo[] todos, int targetLimit) {
    return Arrays.copyOfRange(Arrays.stream(todos).toArray(Todo[]::new), 0, targetLimit);
  }

  /**
   * Get an array of the limit size.
   *
   * @param todos         the list of todos to filter by limit
   * @param targetLimit the target limit
   * @return an array of the limit size
   *
   */
  public Todo[] filterTodosByOrder(Todo[] todos, String targetOrder) {
    switch(targetOrder) {
      case "owner": 
        Arrays.sort(todos, ownerComparator);
        return todos;
          
      case "status":
        Arrays.sort(todos, statusComparator);
        return todos;
     
      case "body":
        Arrays.sort(todos, bodyComparator);
        return todos;

      case "category":
        Arrays.sort(todos, categoryComparator);
        return todos;
      
      default:
        return todos;
    }
  }

  private class OwnerComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo o1, Todo o2) {
      int returnValue = o1.owner.compareTo(o2.owner);
      return returnValue;
    }
    
  }

  private class StatusComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo o1, Todo o2) {
      String o1Status, o2Status;
      if (o1.status == true) {
        o1Status = "true";
      }
      else {
        o1Status = "false";
      }

      if (o2.status == true) {
        o2Status = "true";
      }
      else {
        o2Status = "false";
      }

      int returnValue = o1Status.compareTo(o2Status);
      return returnValue;
    }
    
  }

  private class BodyComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo o1, Todo o2) {
      int returnValue = o1.body.compareTo(o2.body);
      return returnValue;
    }
    
  }

  private class CategoryComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo o1, Todo o2) {
      int returnValue = o1.category.compareTo(o2.category);
      return returnValue;
    }
    
  }
  
}
