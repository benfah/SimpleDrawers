package me.benfah.simpledrawers.mixin.client;

import me.benfah.simpledrawers.callback.ModelPostBakeCallback;
import me.benfah.simpledrawers.callback.ModelPreBakeCallback;
import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

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

	

	@Shadow
	public BakedModel bake(Identifier identifier, ModelBakeSettings settings)
	{
		return null;
	}

}
