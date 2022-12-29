
package wolf.astell.choco.init.brew.potions;

import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionWaterCandle extends PotionMod {

	private static final int RANGE = 48;

	public PotionWaterCandle() {
		super("waterCandle", false, 0xC32222, 0);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}

	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent.CheckSpawn event) {
		if(event.getResult() != Result.ALLOW && event.getEntityLiving() instanceof IMob) {
			AxisAlignedBB aabb = new AxisAlignedBB(event.getX() - RANGE, event.getY() - RANGE, event.getZ() - RANGE, event.getX() + RANGE, event.getY() + RANGE, event.getZ() + RANGE);
			for(EntityPlayer player : event.getWorld().playerEntities) {
				if(hasEffect(player) && player.getEntityBoundingBox().intersects(aabb)) {
					event.setResult(Result.ALLOW);
					return;
				}
			}
		}
	}

}
