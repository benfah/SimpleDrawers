package me.benfah.simpledrawers.mixin.client;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.datafixers.util.Pair;

import me.benfah.simpledrawers.block.BlockBasicDrawer;
import me.benfah.simpledrawers.callback.ModelPostBakeCallback;
import me.benfah.simpledrawers.callback.ModelPreBakeCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin
{

	@Shadow
	Map<ModelIdentifier, BakedModel> bakedModels;

	@Shadow
	Map<Identifier, UnbakedModel> modelsToBake;

	@Inject(method = "upload", at = @At(value = "RETURN"))
	public void onPostUpload(TextureManager textureManager, Profiler profiler,
			CallbackInfoReturnable<SpriteAtlasManager> info)
	{

		ModelPostBakeCallback.EVENT.invoker().onPostBake(bakedModels);
	}

	@Inject(method = "upload", at = @At(value = "INVOKE_STRING", target = "net/minecraft/util/profiler/Profiler.swap(Ljava/lang/String;)V", args = {
			"ldc=baking" }, shift = Shift.AFTER))
	public void onPreUpload(TextureManager textureManager, Profiler profiler,
			CallbackInfoReturnable<SpriteAtlasManager> info)
	{
		ModelPreBakeCallback.EVENT.invoker().onPreBake(modelsToBake, this::bake, bakedModels);
	}

	@Inject(method = "method_21604", at = @At(value = "INVOKE", target = "java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;", shift = Shift.BY, by = 3), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	public void onLambda(Map<?, ?> map, Identifier identifier, Pair<?, ?> pair, Map<?, ?> map2,
			ModelIdentifier modelIdentifier, BlockState state, CallbackInfo info, Pair<?, ?> localPair)
	{
		if (localPair == null && state.getBlock() instanceof BlockBasicDrawer)
		{
			info.cancel();
		}
	}

	@Shadow
	public BakedModel bake(Identifier identifier, ModelBakeSettings settings)
	{
		return null;
	}

}
