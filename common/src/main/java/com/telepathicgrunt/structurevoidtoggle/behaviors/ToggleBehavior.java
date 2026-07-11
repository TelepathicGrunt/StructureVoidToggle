package com.telepathicgrunt.structurevoidtoggle.behaviors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.telepathicgrunt.structurevoidtoggle.StructureVoidToggle;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4d;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class ToggleBehavior {
	public enum STRUCTURE_BLOCK_MODE {
		DEFAULT,
		NO_HITBOX,
		SMALL_HITBOX,
		FULL_HITBOX;

		public STRUCTURE_BLOCK_MODE next() {
			// Loop back to start if on last enum
			if(ordinal() + 1 == values().length) {
				return values()[0];
			}

			return values()[ordinal() + 1];
		}
	}

	// The current mode for the structure void block for the current client
	public static STRUCTURE_BLOCK_MODE MODE = STRUCTURE_BLOCK_MODE.DEFAULT;

	// The current mode for the structure void block rendering for the current client
	public static boolean VISIBLE = true;

	// The current mode for the structure void block forced rendering for the current client
	public static boolean FORCED_RENDERING = false;

	public static int RENDER_RADIUS = 40;

	public static KeyMapping.Category STRUCTURE_VOID_TOGGLE_KEY_CATEGORY = null;

	// Keybind for switching hitbox modes. 96 is the keycode for backtick `
	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_TOGGLE = new KeyMapping(
			"key.structure_void", GLFW.GLFW_KEY_GRAVE_ACCENT, STRUCTURE_VOID_TOGGLE_KEY_CATEGORY
	);

	// Keybind for switching render modes. INSERT by default
	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE = new KeyMapping(
			"key.structure_void_render", GLFW.GLFW_KEY_INSERT, STRUCTURE_VOID_TOGGLE_KEY_CATEGORY
	);

	// Keybind for forcing structure void rendering
	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE = new KeyMapping(
			"key.forced_render", GLFW.GLFW_KEY_DELETE, STRUCTURE_VOID_TOGGLE_KEY_CATEGORY
	);

	// Keybind for forcing structure void non-replacing
	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_NON_REPLACING_TOGGLE = new KeyMapping(
			"key.non_replacing", GLFW.GLFW_KEY_PAGE_UP, STRUCTURE_VOID_TOGGLE_KEY_CATEGORY
	);

	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_SHRINK_RADIUS_TOGGLE = new KeyMapping(
			"key.shrink_radius", GLFW.GLFW_KEY_MINUS, STRUCTURE_VOID_TOGGLE_KEY_CATEGORY
	);

	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_GROW_RADIUS_TOGGLE = new KeyMapping(
			"key.grow_radius", GLFW.GLFW_KEY_EQUAL, STRUCTURE_VOID_TOGGLE_KEY_CATEGORY
	);

	public static void registerKeyMappingCategory() {
		STRUCTURE_VOID_TOGGLE_KEY_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(StructureVoidToggle.MODID, "key.categories.structure_void_toggle"));
	}

	/**
	 * Toggles settings for the relevant keypress.
	 */
	public static void toggle(int key) {
		if (KEY_BIND_STRUCTURE_VOID_TOGGLE.isDown()) {
			toggleHitbox();
		}
		if (KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE.isDown()) {
			toggleRender();
		}
		if (KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE.isDown()) {
			toggleForcedRender();
		}
		if (KEY_BIND_STRUCTURE_VOID_NON_REPLACING_TOGGLE.isDown()) {
			toggleNonReplaceable();
		}
		if (KEY_BIND_STRUCTURE_VOID_SHRINK_RADIUS_TOGGLE.isDown()) {
			RENDER_RADIUS = Math.max(RENDER_RADIUS - 2, 2);
		}
		if (KEY_BIND_STRUCTURE_VOID_GROW_RADIUS_TOGGLE.isDown()) {
			RENDER_RADIUS = Math.min(RENDER_RADIUS + 2, 256);
		}
	}

	/*
	 * Switches between hitbox modes when backtick is pressed.
	 *
	 * The mode is changed clientside so two player can be in different modes and nothing will break because
	 * the server is not what does raytracing. That's the client thing and is why changing hitboxes for a
	 * block that entities can't touch won't cause issues. In theory.
	 */
	private static void toggleHitbox() {
		MODE = MODE.next();
		LocalPlayer player  = Minecraft.getInstance().player;
		if(player == null) return;


		switch (MODE) {
			case DEFAULT -> {
				player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.default_hitbox"));
				StructureVoidBlockShape.STRUCTURE_VOID_TOGGLE$SHAPE = Block.box(5, 5, 5, 11, 11, 11);
				((ShapeInterface)(Blocks.BARRIER)).setShape(Block.box(0, 0, 0, 16, 16, 16));
				((ShapeInterface)(Blocks.LIGHT)).setShape(Block.box(-2, -2, -2, -1, -1, -1));
			}
			case NO_HITBOX -> {
				player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.no_hitbox"));
				StructureVoidBlockShape.STRUCTURE_VOID_TOGGLE$SHAPE = Block.box(0, 0, 0, 0, 0, 0);
				((ShapeInterface)(Blocks.BARRIER)).setShape(Block.box(0, 0, 0, 0, 0, 0));
				((ShapeInterface)(Blocks.LIGHT)).setShape(Block.box(0, 0, 0, 0, 0, 0));
			}
			case SMALL_HITBOX -> {
				player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.small_hitbox"));
				StructureVoidBlockShape.STRUCTURE_VOID_TOGGLE$SHAPE = Block.box(5, 5, 5, 11, 11, 11);
				((ShapeInterface)(Blocks.BARRIER)).setShape(Block.box(5, 5, 5, 11, 11, 11));
				((ShapeInterface)(Blocks.LIGHT)).setShape(Block.box(5, 5, 5, 11, 11, 11));
			}
			case FULL_HITBOX -> {
				player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.full_hitbox"));
				StructureVoidBlockShape.STRUCTURE_VOID_TOGGLE$SHAPE = Block.box(0, 0, 0, 16, 16, 16);
				((ShapeInterface)(Blocks.BARRIER)).setShape(Block.box(0, 0, 0, 16, 16, 16));
				((ShapeInterface)(Blocks.LIGHT)).setShape(Block.box(0, 0, 0, 16, 16, 16));
			}
			default -> {
			}
		}
	}

	/**
	 * Switches between render modes when INSERT is pressed.
	 */
	private static void toggleRender() {
		VISIBLE = !VISIBLE;
		LocalPlayer player  = Minecraft.getInstance().player;
		if (player == null) return;

		if (VISIBLE) {
			player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.structure_block_visible"));
		}
		else {
			player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.structure_block_invisible"));
		}
	}

	/**
	 * Switches between forced rendering when DELETE is pressed.
	 */
	private static void toggleForcedRender() {
		FORCED_RENDERING = !FORCED_RENDERING;
		LocalPlayer player  = Minecraft.getInstance().player;
		if (player == null) return;

		if (FORCED_RENDERING) {
			player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.invisible_blocks_forced_render"));
		}
		else {
			player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.invisible_blocks_disabled_forced_render"));
		}
	}

	/**
	 * Switches between forced non-replaceable when PAGEUP is pressed.
	 */
	private static void toggleNonReplaceable() {
		StructureVoidBlockShape.FORCED_NON_REPLACEABLE = !StructureVoidBlockShape.FORCED_NON_REPLACEABLE;
		LocalPlayer player  = Minecraft.getInstance().player;
		if (player == null) return;

		if (StructureVoidBlockShape.FORCED_NON_REPLACEABLE) {
			player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.invisible_blocks_non_replaceable"));
		}
		else {
			player.sendOverlayMessage(Component.translatable("system.structure_void_toggle.invisible_blocks_replaceable"));
		}
	}

	/**
	 * Switches between forced rendering when DELETE is pressed.
	 */
	public static void forceRenderInvisibleBlocks(Level level, Vec3 cameraPos, Frustum frustum, PoseStack poseStack, boolean clearRenderState) {
		if (FORCED_RENDERING) {
			double drawRadius;
			if (MODE == STRUCTURE_BLOCK_MODE.FULL_HITBOX) {
				drawRadius = 0.4D;
			}
			else {
				drawRadius = 0.05D;
			}
			double minCorner = 0.5D - drawRadius;
			double maxCorner = 0.5D + drawRadius;
			Vector4d vector4dMin = new Vector4d(minCorner, minCorner, minCorner, 1.0D);
			Vector4d vector4dMax = new Vector4d(maxCorner, maxCorner, maxCorner, 1.0D);

			int radius = RENDER_RADIUS;
			BlockPos centerPos = BlockPos.containing(cameraPos);
			HashMap<ChunkPos, Boolean> chunkAllowedMap = new HashMap<>();
			BlockPos.MutableBlockPos worldSpot = new BlockPos.MutableBlockPos();

			int radiusSq = radius * radius;
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					for (int y = -radius; y <= radius; y++) {
						int distSq = x * x + y * y + z * z;
						if (distSq > radiusSq) {
							continue;
						}

						worldSpot.set(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ() + z);
						ChunkPos chunkPos = ChunkPos.containing(worldSpot);
						boolean isValidChunk = chunkAllowedMap.computeIfAbsent(chunkPos,
								(c) -> {
									for(LevelChunkSection levelChunkSection : level.getChunk(chunkPos.x(), chunkPos.z()).getSections()) {
										if (levelChunkSection.getStates().maybeHas((b) ->
												b.is(Blocks.STRUCTURE_VOID) ||
												b.is(Blocks.BARRIER) ||
												b.is(Blocks.LIGHT)))
										{
											return true;
										}
									}
									return false;
								});

						if (!isValidChunk) {
							z = (((worldSpot.getZ() >> 4) + 1) << 4) - 1 - centerPos.getZ();
							break;
						}

						if (!frustum.isVisible(new AABB(
								worldSpot.getX() + minCorner,
								worldSpot.getY() + minCorner,
								worldSpot.getZ() + minCorner,
								worldSpot.getX() + maxCorner,
								worldSpot.getY() + maxCorner,
								worldSpot.getZ() + maxCorner)))
						{
							continue;
						}

						BlockState blockstate = level.getBlockState(worldSpot);
						boolean flag1 = blockstate.is(Blocks.STRUCTURE_VOID);
						boolean flag2 = blockstate.is(Blocks.BARRIER);
						boolean flag3 = blockstate.is(Blocks.LIGHT);
						boolean flag4 = flag1 || flag2 || flag3;
						if (flag4) {

							int red = 255;
							int green = 255;
							int blue = 255;
							int alpha = 255;
							if (flag1) {
								green = 190;
								blue = 190;
							}
							else if (flag2) {
								green = 0;
								blue = 0;
							}
							else if (flag3) {
								blue = 0;
							}


							if (MODE == STRUCTURE_BLOCK_MODE.FULL_HITBOX) {
                                float distanceMult = Math.max(1 - ((distSq * 6f) / radiusSq), 0.5f);
                                Gizmos.cuboid(
                                    new AABB(
                                        (vector4dMin.x() + worldSpot.getX()),
                                        (vector4dMin.y() + worldSpot.getY()),
                                        (vector4dMin.z() + worldSpot.getZ()),
                                        (vector4dMax.x() + worldSpot.getX()),
                                        (vector4dMax.y() + worldSpot.getY()),
                                        (vector4dMax.z() + worldSpot.getZ())
                                    ),
                                    GizmoStyle.fill(ARGB.color(
                                        alpha,
                                        (int) (red * distanceMult),
                                        (int) (green * distanceMult),
                                        (int) (blue * distanceMult)
                                    )));
                            }
							else {
                                float distanceMult = Math.clamp((1 - (distSq * 40) / (float)radiusSq), 0, 1);
                                Gizmos.cuboid(
                                    new AABB(
                                        (vector4dMin.x() + worldSpot.getX()),
                                        (vector4dMin.y() + worldSpot.getY()),
                                        (vector4dMin.z() + worldSpot.getZ()),
                                        (vector4dMax.x() + worldSpot.getX()),
                                        (vector4dMax.y() + worldSpot.getY()),
                                        (vector4dMax.z() + worldSpot.getZ())
                                    ),
                                    GizmoStyle.stroke(ARGB.color(
                                        alpha,
                                        red,
                                        green,
                                        blue
                                    ), 1.5f + (2f * distanceMult)));
                            }
                        }
					}
				}
			}
		}
	}
}
