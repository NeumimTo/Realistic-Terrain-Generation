package rtg.world.gen.terrain;

import rtg.api.world.RTGWorld;

/**
 * @author Zeno410
 */
public class GroundEffect extends HeightEffect {

    // the standard ground effect
    private final float amplitude;

    public GroundEffect(float amplitude) {

        this.amplitude = amplitude;
    }

    @Override
    public final float added(RTGWorld rtgWorld, float x, float y) {

        return TerrainBase.groundNoise(x, y, amplitude, rtgWorld.simplex);
    }

}
