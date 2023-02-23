package wolf.astell.choco.init.register;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import wolf.astell.choco.advancements.*;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AdvancementRegister {
    public static final StarShooterTrigger STAR_SHOOTER = CriteriaTriggers.register(new StarShooterTrigger());
    public static final DyeGreenTrigger DYE_GREEN = CriteriaTriggers.register(new DyeGreenTrigger());
    public static final ShiNaLaiTrigger SHI_NA_LAI = CriteriaTriggers.register(new ShiNaLaiTrigger());
    public static final NtrTrigger NTR = CriteriaTriggers.register(new NtrTrigger());

    public static void init() {
    }
}