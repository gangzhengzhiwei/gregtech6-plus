package gregapi.compat.waila;

import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;

import java.text.DecimalFormat;

import static gregapi.data.CS.T;

public class GTWailaUtils {
    public static DecimalFormat DF1 = new DecimalFormat("#.###"); // 123.4567 -> 123.456, 123.0000 -> 123  I don't know how to give it a name.
    public static String NBT_WAILA_ENABLED = "gt.waila.enabled";

    public static String getFluidTankGTTip(FluidTankGT aTank) {
        return LH.format("gt.waila.fluidtankgt.0", aTank.amount(), FL.name(aTank, T));
    }

    static {
        LH.add("gt.waila.fluidtankgt.0","§f%d§bL §f%s");

        LH.add("gt.waila.barrel.0", "Contain: %s");
        LH.add("gt.waila.barrel.1", "Auto output: §cDisabled");
        LH.add("gt.waila.barrel.2", "Auto output: §bEnabled");
        LH.add("gt.waila.smeltery.0", "Temperature:§f %d/%d §bK");
        LH.add("gt.waila.smeltery.1", "Content:§f ");
        LH.add("gt.waila.smeltery.2", "%s§bUnit§f%s ");
        LH.add("gt.waila.smeltery.3", "Total:§f %s/%d §bUnit");
        LH.add("gt.waila.smeltery.4", "Hidden item:§f %s %s");
        LH.add("gt.waila.smeltery.5", "Heat loss:§f %d§bGU/t");
        LH.add("gt.waila.multiblock.0", "§cStructure not Formed");
        LH.add("gt.waila.pipefluid.0", "Contain: %s");
        LH.add("gt.waila.pipefluid.1", "Pipe Count: §f%d");
    }
}
