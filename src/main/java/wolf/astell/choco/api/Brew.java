
package wolf.astell.choco.api;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.List;


public class Brew {

	private final String key;
	private final String name;
	private final int color;
	private final List<PotionEffect> effects;

	public Brew(String key, String name, int color, PotionEffect... effects) {
		this.key = key;
		this.name = name;
		this.color = color;
		this.effects = ImmutableList.copyOf(effects);
	}

	public String getKey() {
		return key;
	}
	public String getUnlocalizedName() {
		return name;
	}

}
