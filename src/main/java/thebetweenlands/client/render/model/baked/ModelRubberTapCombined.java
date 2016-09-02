package thebetweenlands.client.render.model.baked;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonParser;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelCustomData;
import net.minecraftforge.common.model.IModelState;
import thebetweenlands.client.render.model.baked.modelbase.ModelRubberTap;

public class ModelRubberTapCombined implements IModelCustomData {
	private final IModel tapModel;
	private final ResourceLocation tapTexture;

	public ModelRubberTapCombined(ResourceLocation texture) {
		this(texture, null, 0);
	}

	public ModelRubberTapCombined(ResourceLocation texture, ResourceLocation fluidTexture, int height) {
		this.tapModel = new ModelCombined(new ModelFromModelBase(new ModelRubberTap(), texture, 128, 128), new ModelRubberTapLiquid(fluidTexture, height));
		this.tapTexture = texture;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return this.tapModel.getDependencies();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return this.tapModel.getTextures();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return this.tapModel.bake(state, format, bakedTextureGetter);
	}

	@Override
	public IModelState getDefaultState() {
		return this.tapModel.getDefaultState();
	}

	@Override
	public ModelRubberTapCombined process(ImmutableMap<String, String> customData) {
		if(!customData.containsKey("fluid_texture")) 
			return this;

		JsonParser parser = new JsonParser();

		String fluidJsonStr = customData.get("fluid_texture");
		String fluid = parser.parse(fluidJsonStr).getAsString();

		int height = 0;

		if(customData.containsKey("fluid_height")) {
			String fluidHeightJsonStr = customData.get("fluid_height");
			height = parser.parse(fluidHeightJsonStr).getAsInt();
		}

		return new ModelRubberTapCombined(this.tapTexture, new ResourceLocation(fluid), height);
	}
}
