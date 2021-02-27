package src;

/**
 * Interface for a "row" of a datatable, which is a collection of values for
 * a set of attributes.
 */
public interface IAttributeDatum {

  /**
   * Looks up the value associated with the named attribute
   *
   * @param attributeName - A String representing the attribute to get the
   *                      value of
   * @return the value of the named attribute
   */
  public Object getValueOf(String attributeName);
}
