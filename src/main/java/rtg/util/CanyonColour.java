package rtg.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.HashMap;
import java.util.Map;

public enum CanyonColour {
	MESA(new byte[]{-1, -1, -1, 1, 1, 1, 0, -1, -1, 6, 1, 1, 8, 0, -1, -1, 14, -1, -1, 6, 1, 1, 4}),
	MESA_WHITE(new byte[]{-1, -1, 0, 1, 0, 0, 0, 14, 0, 8, 0, 1, 8, 0, -1, 0, 14, 0, 0, 14, 0, 0, 8}),
	SAVANNA(new byte[]{1, 1, 0, 0, 8, 8, 0, 0, 0});

	// If you remove that U, i will locate and dismember you.
	private static Map<CanyonColour, IBlockState[]> colours = new HashMap<>();
	private static OpenSimplexNoise simplex;
	private byte[] bytes;

	CanyonColour(byte[] bytes) {
		this.bytes = bytes;
	}

	public static void init(long l) {
		simplex = new OpenSimplexNoise(l);

		for (CanyonColour colour : CanyonColour.values()) {
			IBlockState[] c = new IBlockState[256];
			int j;
			for (int i = 0; i < 256; i++) {
				j = i + (int) (simplex.noise1(i / 3) * 2);
				j = (j < 0)? 0 : (j > 255)? 255 : j;
				byte b = colour.bytes[j % colour.bytes.length];
				c[i] = (b == -1)? Blocks.hardened_clay.getDefaultState() : Blocks.stained_hardened_clay.getStateFromMeta(b);
			}
			colours.put(colour, c);
		}
	}

	public IBlockState getForHeight(int x, int y, int z) {
		y = y + (int) (simplex.noise2(x / 80, z / 80) * 5f);
		y = (y < 0)? 0 : (y > 255)? 255 : y;
		return colours.get(this)[y];
	}
}
