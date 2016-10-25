package naga.toolkit.drawing.shapes;

/**
 * @author Bruno Salmon
 */
public enum StrokeLineJoin {
    /**
     * Joins path segments by extending their outside edges until they meet.
     */
    MITER,

    /**
     * Joins path segments by connecting the outer corners
     * of their wide outlines with a straight segment.
     */
    BEVEL,

    /**
     * Joins path segments by rounding off the corner
     * at a radius of half the line width.
     */
    ROUND
}
