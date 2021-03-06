package kieker.develop.rl.ouput.config;

/**
 * Entity class for the outlet configuration.
 */
@SuppressWarnings("all")
public class OutletConfiguration {
  /**
   * Name of the outlet.
   */
  private String name;
  
  /**
   * Description of the outlet.
   */
  private String description;
  
  /**
   * default directory for the outlet.
   */
  private String directory;
  
  /**
   * Create a new outlet entity.
   */
  public OutletConfiguration(final String name, final String description, final String directory) {
    this.name = name;
    this.description = description;
    this.directory = directory;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public String getDirectory() {
    return this.directory;
  }
}
