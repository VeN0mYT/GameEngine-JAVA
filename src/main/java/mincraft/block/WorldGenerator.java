package mincraft.block;

public class WorldGenerator {
    public BlockType sample(int worldX, int worldY, int worldZ) {
        // Example: 2D terrain heightmap + underground layers
        float height = (float)(20 * Math.sin(worldX * 0.05) +
                20 * Math.cos(worldZ * 0.05) + 40);

        int topY = (int) Math.floor(height); // top surface y-coordinate

        if (worldY == topY) return BlockType.GRASS;       // top surface
        if (worldY < topY && worldY >= topY - 4) return BlockType.DIRT; // soil layer
        if (worldY < topY - 4) return BlockType.STONE;   // deep underground
        return BlockType.AIR;
    }
}
