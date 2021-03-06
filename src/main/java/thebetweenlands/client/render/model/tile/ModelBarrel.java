package thebetweenlands.client.render.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * BLBarrel2 - TripleHeadedSheep
 * Created using Tabula 7.0.1
 */
public class ModelBarrel extends ModelBase {
    public ModelRenderer bottom_base_main;
    public ModelRenderer bottom_base_left;
    public ModelRenderer bottom_base_right;
    public ModelRenderer side_front_1;
    public ModelRenderer side_back_1;
    public ModelRenderer side_left_1;
    public ModelRenderer side_left_2;
    public ModelRenderer edgefill_left_right_1;
    public ModelRenderer edgefill_left_left_1;
    public ModelRenderer edgefill_left_right_2;
    public ModelRenderer edgefill_left_left_2;
    public ModelRenderer top_left;
    public ModelRenderer handle_left;
    public ModelRenderer top_corner_left;
    public ModelRenderer side_right_1;
    public ModelRenderer side_right_2;
    public ModelRenderer edgefill_right_left_1;
    public ModelRenderer edgefill_right_right_1;
    public ModelRenderer edgefill_right_left_2;
    public ModelRenderer edgefill_right_right_2;
    public ModelRenderer top_right;
    public ModelRenderer handle_right;
    public ModelRenderer top_corner_right;
    public ModelRenderer side_front_2;
    public ModelRenderer edgefill_front_left_1;
    public ModelRenderer edgefill_front_right_1;
    public ModelRenderer edgefill_front_left_2;
    public ModelRenderer edgefill_front_right_2;
    public ModelRenderer top_front;
    public ModelRenderer top_corner_front;
    public ModelRenderer side_back_2;
    public ModelRenderer edgefill_back_left_1;
    public ModelRenderer edgefill_back_right_1;
    public ModelRenderer edgefill_back_left_2;
    public ModelRenderer edgefill_back_right_2;
    public ModelRenderer top_back;
    public ModelRenderer top_corner_back;

    public ModelBarrel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.edgefill_right_right_1 = new ModelRenderer(this, 44, 0);
        this.edgefill_right_right_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_right_right_1.addBox(-1.0F, -6.0F, 3.01F, 1, 6, 1, 0.0F);
        this.top_back = new ModelRenderer(this, 42, 44);
        this.top_back.setRotationPoint(0.0F, -6.0F, -2.0F);
        this.top_back.addBox(-4.01F, -2.0F, 0.0F, 8, 2, 2, 0.0F);
        this.setRotateAngle(top_back, -0.136659280431156F, 0.0F, 0.0F);
        this.edgefill_back_right_1 = new ModelRenderer(this, 34, 15);
        this.edgefill_back_right_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_back_right_1.addBox(3.01F, -6.0F, 0.0F, 1, 6, 1, 0.0F);
        this.bottom_base_left = new ModelRenderer(this, 0, 15);
        this.bottom_base_left.setRotationPoint(4.0F, 0.0F, 0.0F);
        this.bottom_base_left.addBox(0.0F, -2.0F, -4.0F, 2, 2, 8, 0.0F);
        this.side_front_1 = new ModelRenderer(this, 0, 36);
        this.side_front_1.setRotationPoint(0.0F, -2.0F, -3.0F);
        this.side_front_1.addBox(-3.99F, -6.0F, -3.0F, 8, 6, 2, 0.0F);
        this.setRotateAngle(side_front_1, 0.136659280431156F, 0.0F, 0.0F);
        this.top_left = new ModelRenderer(this, 21, 54);
        this.top_left.setRotationPoint(-2.0F, -6.0F, 0.0F);
        this.top_left.addBox(0.0F, -2.0F, -3.99F, 2, 2, 8, 0.0F);
        this.setRotateAngle(top_left, 0.0F, 0.0F, 0.136659280431156F);
        this.edgefill_front_left_1 = new ModelRenderer(this, 72, 30);
        this.edgefill_front_left_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_front_left_1.addBox(3.01F, -6.0F, -1.0F, 1, 6, 1, 0.0F);
        this.side_back_1 = new ModelRenderer(this, 0, 54);
        this.side_back_1.setRotationPoint(0.0F, -2.0F, 3.0F);
        this.side_back_1.addBox(-4.01F, -6.0F, 1.0F, 8, 6, 2, 0.0F);
        this.setRotateAngle(side_back_1, -0.136659280431156F, 0.0F, 0.0F);
        this.top_front = new ModelRenderer(this, 42, 49);
        this.top_front.setRotationPoint(0.0F, -6.0F, 2.0F);
        this.top_front.addBox(-3.99F, -2.0F, -2.0F, 8, 2, 2, 0.0F);
        this.setRotateAngle(top_front, 0.136659280431156F, 0.0F, 0.0F);
        this.top_corner_front = new ModelRenderer(this, 74, 0);
        this.top_corner_front.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top_corner_front.addBox(3.0F, -2.0F, 0.0F, 1, 14, 1, 0.0F);
        this.bottom_base_main = new ModelRenderer(this, 0, 0);
        this.bottom_base_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.bottom_base_main.addBox(-4.0F, -2.0F, -6.0F, 8, 2, 12, 0.0F);
        this.edgefill_right_left_1 = new ModelRenderer(this, 39, 0);
        this.edgefill_right_left_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_right_left_1.addBox(-1.0F, -6.0F, -4.01F, 1, 6, 1, 0.0F);
        this.edgefill_right_left_2 = new ModelRenderer(this, 29, 0);
        this.edgefill_right_left_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_right_left_2.addBox(2.0F, -6.0F, -4.02F, 1, 6, 1, 0.0F);
        this.top_corner_right = new ModelRenderer(this, 69, 0);
        this.top_corner_right.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top_corner_right.addBox(0.0F, -2.0F, -4.0F, 1, 14, 1, 0.0F);
        this.edgefill_left_right_2 = new ModelRenderer(this, 42, 30);
        this.edgefill_left_right_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_left_right_2.addBox(-3.0F, -6.0F, -4.0F, 1, 6, 1, 0.0F);
        this.handle_right = new ModelRenderer(this, 0, -2);
        this.handle_right.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.handle_right.addBox(0.0F, 0.0F, -2.0F, 0, 3, 4, 0.0F);
        this.setRotateAngle(handle_right, 0.0F, 0.0F, 0.136659280431156F);
        this.edgefill_front_right_2 = new ModelRenderer(this, 67, 30);
        this.edgefill_front_right_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_front_right_2.addBox(-4.0F, -6.0F, 2.0F, 1, 6, 1, 0.0F);
        this.edgefill_back_left_1 = new ModelRenderer(this, 24, 15);
        this.edgefill_back_left_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_back_left_1.addBox(-4.01F, -6.0F, 0.0F, 1, 6, 1, 0.0F);
        this.edgefill_front_left_2 = new ModelRenderer(this, 62, 30);
        this.edgefill_front_left_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_front_left_2.addBox(3.02F, -6.0F, 2.0F, 1, 6, 1, 0.0F);
        this.side_left_2 = new ModelRenderer(this, 21, 30);
        this.side_left_2.setRotationPoint(3.0F, -6.0F, 0.0F);
        this.side_left_2.addBox(-2.0F, -6.0F, -3.98F, 2, 6, 8, 0.0F);
        this.setRotateAngle(side_left_2, 0.0F, 0.0F, -0.27314402793711257F);
        this.top_corner_left = new ModelRenderer(this, 64, 0);
        this.top_corner_left.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top_corner_left.addBox(-1.0F, -2.0F, 3.0F, 1, 14, 1, 0.0F);
        this.edgefill_right_right_2 = new ModelRenderer(this, 34, 0);
        this.edgefill_right_right_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_right_right_2.addBox(2.0F, -6.0F, 3.0F, 1, 6, 1, 0.0F);
        this.side_right_1 = new ModelRenderer(this, 42, 0);
        this.side_right_1.setRotationPoint(1.0F, -2.0F, 0.0F);
        this.side_right_1.addBox(-3.0F, -6.0F, -4.01F, 2, 6, 8, 0.0F);
        this.setRotateAngle(side_right_1, 0.0F, 0.0F, -0.136659280431156F);
        this.side_back_2 = new ModelRenderer(this, 21, 45);
        this.side_back_2.setRotationPoint(0.0F, -6.0F, 3.0F);
        this.side_back_2.addBox(-4.02F, -6.0F, -2.0F, 8, 6, 2, 0.0F);
        this.setRotateAngle(side_back_2, 0.27314402793711257F, 0.0F, 0.0F);
        this.edgefill_left_right_1 = new ModelRenderer(this, 52, 30);
        this.edgefill_left_right_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_left_right_1.addBox(0.0F, -6.0F, -4.01F, 1, 6, 1, 0.0F);
        this.top_right = new ModelRenderer(this, 42, 54);
        this.top_right.setRotationPoint(2.0F, -6.0F, 0.0F);
        this.top_right.addBox(-2.0F, -2.0F, -4.01F, 2, 2, 8, 0.0F);
        this.setRotateAngle(top_right, 0.0F, 0.0F, -0.136659280431156F);
        this.bottom_base_right = new ModelRenderer(this, 0, 25);
        this.bottom_base_right.setRotationPoint(-4.0F, 0.0F, 0.0F);
        this.bottom_base_right.addBox(-2.0F, -2.0F, -4.0F, 2, 2, 8, 0.0F);
        this.edgefill_back_left_2 = new ModelRenderer(this, 14, 15);
        this.edgefill_back_left_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_back_left_2.addBox(-4.02F, -6.0F, -3.0F, 1, 6, 1, 0.0F);
        this.handle_left = new ModelRenderer(this, 0, 3);
        this.handle_left.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.handle_left.addBox(0.0F, 0.0F, -2.0F, 0, 3, 4, 0.0F);
        this.setRotateAngle(handle_left, 0.0F, 0.0F, -0.136659280431156F);
        this.top_corner_back = new ModelRenderer(this, 79, 0);
        this.top_corner_back.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top_corner_back.addBox(-4.0F, -2.0F, -1.0F, 1, 14, 1, 0.0F);
        this.edgefill_left_left_1 = new ModelRenderer(this, 57, 30);
        this.edgefill_left_left_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_left_left_1.addBox(0.0F, -6.0F, 3.01F, 1, 6, 1, 0.0F);
        this.side_right_2 = new ModelRenderer(this, 42, 15);
        this.side_right_2.setRotationPoint(-3.0F, -6.0F, 0.0F);
        this.side_right_2.addBox(0.0F, -6.0F, -4.02F, 2, 6, 8, 0.0F);
        this.setRotateAngle(side_right_2, 0.0F, 0.0F, 0.27314402793711257F);
        this.edgefill_back_right_2 = new ModelRenderer(this, 19, 15);
        this.edgefill_back_right_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_back_right_2.addBox(3.0F, -6.0F, -3.0F, 1, 6, 1, 0.0F);
        this.side_left_1 = new ModelRenderer(this, 21, 15);
        this.side_left_1.setRotationPoint(-1.0F, -2.0F, 0.0F);
        this.side_left_1.addBox(1.0F, -6.0F, -3.99F, 2, 6, 8, 0.0F);
        this.setRotateAngle(side_left_1, 0.0F, 0.0F, 0.136659280431156F);
        this.edgefill_left_left_2 = new ModelRenderer(this, 47, 30);
        this.edgefill_left_left_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_left_left_2.addBox(-3.0F, -6.0F, 3.02F, 1, 6, 1, 0.0F);
        this.edgefill_front_right_1 = new ModelRenderer(this, 77, 30);
        this.edgefill_front_right_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edgefill_front_right_1.addBox(-4.01F, -6.0F, -1.0F, 1, 6, 1, 0.0F);
        this.side_front_2 = new ModelRenderer(this, 0, 45);
        this.side_front_2.setRotationPoint(0.0F, -6.0F, -3.0F);
        this.side_front_2.addBox(-3.98F, -6.0F, 0.0F, 8, 6, 2, 0.0F);
        this.setRotateAngle(side_front_2, -0.27314402793711257F, 0.0F, 0.0F);
        this.side_right_1.addChild(this.edgefill_right_right_1);
        this.side_back_2.addChild(this.top_back);
        this.side_back_1.addChild(this.edgefill_back_right_1);
        this.bottom_base_main.addChild(this.bottom_base_left);
        this.bottom_base_main.addChild(this.side_front_1);
        this.side_left_2.addChild(this.top_left);
        this.side_front_1.addChild(this.edgefill_front_left_1);
        this.bottom_base_main.addChild(this.side_back_1);
        this.side_front_2.addChild(this.top_front);
        this.top_front.addChild(this.top_corner_front);
        this.side_right_1.addChild(this.edgefill_right_left_1);
        this.side_right_2.addChild(this.edgefill_right_left_2);
        this.top_right.addChild(this.top_corner_right);
        this.side_left_2.addChild(this.edgefill_left_right_2);
        this.side_right_2.addChild(this.handle_right);
        this.side_front_2.addChild(this.edgefill_front_right_2);
        this.side_back_1.addChild(this.edgefill_back_left_1);
        this.side_front_2.addChild(this.edgefill_front_left_2);
        this.side_left_1.addChild(this.side_left_2);
        this.top_left.addChild(this.top_corner_left);
        this.side_right_2.addChild(this.edgefill_right_right_2);
        this.bottom_base_right.addChild(this.side_right_1);
        this.side_back_1.addChild(this.side_back_2);
        this.side_left_1.addChild(this.edgefill_left_right_1);
        this.side_right_2.addChild(this.top_right);
        this.bottom_base_main.addChild(this.bottom_base_right);
        this.side_back_2.addChild(this.edgefill_back_left_2);
        this.side_left_2.addChild(this.handle_left);
        this.top_back.addChild(this.top_corner_back);
        this.side_left_1.addChild(this.edgefill_left_left_1);
        this.side_right_1.addChild(this.side_right_2);
        this.side_back_2.addChild(this.edgefill_back_right_2);
        this.bottom_base_left.addChild(this.side_left_1);
        this.side_left_2.addChild(this.edgefill_left_left_2);
        this.side_front_1.addChild(this.edgefill_front_right_1);
        this.side_front_1.addChild(this.side_front_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.bottom_base_main.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
