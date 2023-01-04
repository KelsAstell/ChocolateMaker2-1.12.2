package wolf.astell.choco.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InjectDamageSource extends EntityDamageSource {

    public InjectDamageSource(Entity source) {
        super("chocolate", source);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entity) {
        String s = "death.attack.infinity_bauble_chocolate.";
        int r = entity.getEntityWorld().rand.nextInt(2);
        s = s + r;
        return new TextComponentTranslation(s, entity.getDisplayName(), null);
    }

    @Override
    public boolean isDifficultyScaled() {
        return false;
    }

}