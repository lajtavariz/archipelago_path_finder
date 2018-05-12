package hu.ppke.itk.ai.config;

/**
 * Class for storing configuration values
 */
public class Config {

    /**
     * Default size of the map canvas.
     */
    public static final int DEFAULT_CANVAS_SIZE = 600;

    /**
     * Default size of one pixel on the map.
     */
    public static final int DEFAULT_PIXEL_SIZE = 60;

    /**
     * Default threshold. It determines the ratio of land and water.
     * Should be set between -1 and 1
     */
    public static final float DEFAULT_THRESHOLD = 0.3f;

    /**
     * This number specifies where the goal node will be placed.
     */
    public static final double DEFAULT_GOAL_NODE_THRESHOLD = 0.3;

    /**
     * Default thread sleep time in ms
     */
    public static final int DEFAULT_SLEEP_TIME = 500;

}
