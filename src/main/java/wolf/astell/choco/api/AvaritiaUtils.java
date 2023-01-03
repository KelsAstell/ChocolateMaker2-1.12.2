package wolf.astell.choco.api;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.network.PacketHandler;
import morph.avaritia.recipe.AvaritiaRecipeManager;
import morph.avaritia.recipe.compressor.CompressorRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import morph.avaritia.recipe.extreme.IExtremeRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.items.linkage.InfiniteBaubleChocolate;
import wolf.astell.choco.network.PacketNoClip;

import java.util.HashMap;
import java.util.Map;

public class AvaritiaUtils {
    private static final Map<EntityPlayer, Boolean> MAP = new HashMap<>();

    public static void addShapedRecipe(ItemStack result, Object...objects) {
        Object[] list = new Object[objects.length + 1];
        list[0] = (Object) false;
        System.arraycopy(objects, 0, list, 1, objects.length);
        ShapedPrimer primer = CraftingHelper.parseShaped(list);
        primer.mirrored = false;
        ExtremeShapedRecipe recipe = new ExtremeShapedRecipe(result, primer);
        ResourceLocation key = result.getItem().getRegistryName();
        assert key != null;
        AvaritiaRecipeManager.EXTREME_RECIPES.put(key, recipe.setRegistryName(key));
    }
    public static void addCompressorRecipe(ItemStack result, int cost, boolean absolute, ItemStack ingredient) {
        NonNullList<Ingredient> ing = NonNullList.create();
        ing.add(0,Ingredient.fromItem(ingredient.getItem()));
        CompressorRecipe recipe = new CompressorRecipe(result,cost,absolute,ing);
        ResourceLocation key = result.getItem().getRegistryName();
        assert key != null;
        AvaritiaRecipeManager.COMPRESSOR_RECIPES.put(key, recipe.setRegistryName(key));
    }
    public static void addCatalystIngredient(ItemStack stack) {
        IExtremeRecipe recipe = AvaritiaRecipeManager.EXTREME_RECIPES.get(new ResourceLocation("avaritia:items/infinity_catalyst"));
        if(recipe != null) {
            recipe.getIngredients().add(CraftingHelper.getIngredient(stack));
        }
    }
    public static void toggleNoClip(EntityPlayer player) {
        if (isBaubleValid(player)) {
            if (MAP.containsKey(player) && MAP.get(player)) {
                MAP.remove(player);
                player.noClip = false;
                player.sendStatusMessage(new TextComponentTranslation("message.choco.noclip.disabled"), true);
            } else {
                MAP.put(player, true);
                player.noClip = true;
                player.capabilities.isFlying = true;
                player.sendStatusMessage(new TextComponentTranslation("message.choco.noclip.enabled"), true);
            }
        }
    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keys.NOCLIP.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new PacketNoClip());
        }
    }

    public static boolean isBaubleValid(EntityPlayer player) {
        IBaublesItemHandler h = BaublesApi.getBaublesHandler(player);
        for(int i : BaubleType.AMULET.getValidSlots())
        {
            ItemStack stack = h.getStackInSlot(i);
            if(!stack.isEmpty() && stack.getItem() instanceof InfiniteBaubleChocolate)
            {
                return true;
            }
        }
        return false;
    }

}
