package com.telepathicgrunt.structurevoidtoggle.behaviors;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import com.telepathicgrunt.structurevoidtoggle.mixin.LevelRendererAccessor;
import com.telepathicgrunt.structurevoidtoggle.mixin.StructureVoidBlockAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class ToggleBehavior {
	public enum STRUCTURE_BLOCK_MODE {
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
	public static STRUCTURE_BLOCK_MODE MODE = STRUCTURE_BLOCK_MODE.SMALL_HITBOX;

	// The current mode for the structure void block rendering for the current client
	public static boolean VISIBLE = true;

	// The current mode for the structure void block forced rendering for the current client
	public static boolean FORCED_RENDERING = false;
	
	// Keybind for switching hitbox modes. 96 is the keycode for backtick `
	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_TOGGLE = new KeyMapping(
			"key.structure_void", GLFW.GLFW_KEY_GRAVE_ACCENT, "key.categories.misc");

	// Keybind for switching render modes. INSERT by default
	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE = new KeyMapping(
			"key.structure_void_render", GLFW.GLFW_KEY_INSERT, "key.categories.misc"
	);

	// Keybind for forcing structure void rendering
	public static final KeyMapping KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE = new KeyMapping(
			"key.structure_void_render", GLFW.GLFW_KEY_DELETE, "key.categories.misc"
	);

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
			case NO_HITBOX :
				player.displayClientMessage(MutableComponent.create(new TranslatableContents("Structure Void Toggle: No hitbox set.")), true);
				StructureVoidBlockAccessor.setSHAPE(Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
				break;
			case SMALL_HITBOX :
				player.displayClientMessage(MutableComponent.create(new TranslatableContents("Structure Void Toggle: Small hitbox set.")), true);
				StructureVoidBlockAccessor.setSHAPE(Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D));
				break;
			case FULL_HITBOX :
				player.displayClientMessage(MutableComponent.create(new TranslatableContents("Structure Void Toggle: Full hitbox set.")), true);
				StructureVoidBlockAccessor.setSHAPE(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
				break;
			default :
				break;
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
			player.displayClientMessage(MutableComponent.create(new TranslatableContents("Structure Void Toggle: Visible.")), true);
		}
		else {
			player.displayClientMessage(MutableComponent.create(new TranslatableContents("Structure Void Toggle: Invisible.")), true);
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
			player.displayClientMessage(MutableComponent.create(new TranslatableContents("Structure Void Toggle: Forced Rendering.")), true);
		}
		else {
			player.displayClientMessage(MutableComponent.create(new TranslatableContents("Structure Void Toggle: Disabled Forced Rendering.")), true);
		}
	}

	/**
	 * Switches between forced rendering when DELETE is pressed.
	 */
	public static void forceRenderInvisibleBlocks(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS && FORCED_RENDERING) {
			Player player = Minecraft.getInstance().player;
			Level level = player.getLevel();

			float drawRadius = 0.05f;
			float minCorner = 0.5f - drawRadius;
			float maxCorner = 0.5f + drawRadius;
			Vector4f vector4fMin = new Vector4f(minCorner, minCorner, minCorner, 1.0F);
			Vector4f vector4fMax = new Vector4f(maxCorner, maxCorner, maxCorner, 1.0F);

			int radius = 40;
			Vec3 cameraPos = event.getCamera().getPosition();
			BlockPos centerPos = new BlockPos(cameraPos);
			HashMap<ChunkPos, Boolean> chunkAllowedMap = new HashMap<>();
			BlockPos.MutableBlockPos worldSpot = new BlockPos.MutableBlockPos();

			PoseStack poseStack = event.getPoseStack();
			poseStack.pushPose();
			poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

			Tesselator tesselator = Tesselator.getInstance();
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			BufferBuilder bufferbuilder = tesselator.getBuilder();
			bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					for (int y = -radius; y <= radius; y++) {
						if (x * x + y * y + z * z > radius * radius) {
							continue;
						}

						worldSpot.set(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ() + z);
						ChunkPos chunkPos = new ChunkPos(worldSpot);
						boolean isValidChunk = chunkAllowedMap.computeIfAbsent(chunkPos,
								(c) -> {
									for(LevelChunkSection levelChunkSection : level.getChunk(chunkPos.x, chunkPos.z).getSections()) {
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

						if (!((LevelRendererAccessor)event.getLevelRenderer()).getCullingFrustum().isVisible(new AABB(
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

							renderLineBox(
									bufferbuilder,
									poseStack.last().pose(),
									vector4fMin.x() + worldSpot.getX(),
									vector4fMin.y() + worldSpot.getY(),
									vector4fMin.z() + worldSpot.getZ(),
									vector4fMax.x() + worldSpot.getX(),
									vector4fMax.y() + worldSpot.getY(),
									vector4fMax.z() + worldSpot.getZ(),
									red,
									green,
									blue,
									alpha);
						}
					}
				}
			}
			tesselator.end();
			poseStack.popPose();
		}
	}
	
	public static void renderLineBox(BufferBuilder builder, Matrix4f pose, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int red, int green, int blue, int alpha) {
		builder.vertex(pose, minX, minY, minZ).color(red, green, blue, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, minY, minZ).color(red, green, blue, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, minX, minY, minZ).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
		builder.vertex(pose, minX, maxY, minZ).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
		builder.vertex(pose, minX, minY, minZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
		builder.vertex(pose, minX, minY, maxZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
		builder.vertex(pose, maxX, minY, minZ).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, maxY, minZ).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, maxY, minZ).color(red, green, blue, alpha).normal(-1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, minX, maxY, minZ).color(red, green, blue, alpha).normal(-1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, minX, maxY, minZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
		builder.vertex(pose, minX, maxY, maxZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
		builder.vertex(pose, minX, maxY, maxZ).color(red, green, blue, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
		builder.vertex(pose, minX, minY, maxZ).color(red, green, blue, alpha).normal(0.0F, -1.0F, 0.0F).endVertex();
		builder.vertex(pose, minX, minY, maxZ).color(red, green, blue, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, minY, maxZ).color(red, green, blue, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, minY, maxZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, -1.0F).endVertex();
		builder.vertex(pose, maxX, minY, minZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, -1.0F).endVertex();
		builder.vertex(pose, minX, maxY, maxZ).color(red, green, blue, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, maxY, maxZ).color(red, green, blue, alpha).normal(1.0F, 0.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, minY, maxZ).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, maxY, maxZ).color(red, green, blue, alpha).normal(0.0F, 1.0F, 0.0F).endVertex();
		builder.vertex(pose, maxX, maxY, minZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
		builder.vertex(pose, maxX, maxY, maxZ).color(red, green, blue, alpha).normal(0.0F, 0.0F, 1.0F).endVertex();
	}
}
