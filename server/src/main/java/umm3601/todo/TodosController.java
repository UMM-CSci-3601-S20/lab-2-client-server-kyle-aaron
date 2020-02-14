package umm3601.todo;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

/**
 * Controller that manages requests for info about users.
 */
public class TodosController {

  private Database database;

  /**
   * Construct a controller for users.
   * <p>
   * This loads the "database" of user info from a JSON file and stores that
   * internally so that (subsets of) users can be returned in response to
   * requests.
   *
   * @param database the `Database` containing user data
   */
  public TodosController(Database database) {
    this.database = database;
  }

  /**
   * Get the single user specified by the `id` parameter in the request.
   *
   * @param ctx a Javalin HTTP context
   */
  public void getTodo(Context ctx) {
    String id = ctx.pathParam("id", String.class).get();
    Todo todo = database.getTodo(id);
    if (todo != null) {
      ctx.json(todo);
      ctx.status(201);
    } else {
      throw new NotFoundResponse("No todo with id " + id + " was found.");
    }
  }

  /**
   * Get a JSON response with a list of all the todos in the "database".
   *
   * @param ctx a Javalin HTTP context
   */
  public void getTodos(Context ctx) {
    Todo[] todos = database.listTodos(ctx.queryParamMap());
    ctx.json(todos);
  }

}
